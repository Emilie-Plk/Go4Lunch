package com.emplk.go4lunch.data.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.conversation.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.conversation.RecipientEntity;
import com.emplk.go4lunch.domain.chat.conversation.SenderEntity;
import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;
import com.emplk.go4lunch.domain.chat.send_message.SendMessageEntity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatRepositoryFirestore implements ChatRepository {

    private static final String CHAT_COLLECTION = "chat_conversations";
    private static final String CHAT_LAST_MESSAGES_COLLECTION = "chat_last_messages";
    private static final String MESSAGES_SUB_COLLECTION = "messages";
    private static final String LAST_MESSAGES_SUB_COLLECTION = "last_messages";
    private static final String MESSAGE_TIMESTAMP = "timestamp";

    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public ChatRepositoryFirestore(@NonNull FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void sendMessage(@NonNull SendMessageEntity sendMessageEntity) {
        String senderId = sendMessageEntity.getSenderEntity().getSenderId();
        String recipientId = sendMessageEntity.getRecipientEntity().getRecipientId();
        String conversationUid = generateConversationId(senderId, recipientId);
        firestore
            .collection(CHAT_COLLECTION)
            .document(conversationUid)
            .collection(MESSAGES_SUB_COLLECTION)
            .add(
                new ChatConversationDto(
                    sendMessageEntity.getSenderEntity().getSenderId(),
                    sendMessageEntity.getSenderEntity().getSenderName(),
                    sendMessageEntity.getSenderEntity().getSenderPictureUrl(),
                    sendMessageEntity.getRecipientEntity().getRecipientId(),
                    sendMessageEntity.getRecipientEntity().getRecipientName(),
                    sendMessageEntity.getRecipientEntity().getRecipientPhotoUrl(),
                    sendMessageEntity.getMessage(),
                    null
                )
            )
            .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("ChatRepositoryFirestore", "Message sent successfully");
                    } else {
                        Log.e("ChatRepositoryFirestore", "Message sent failed", task.getException());
                    }
                }
            );
    }

    @Override
    public void saveLastMessage(@NonNull SendMessageEntity sendMessageEntity) {
        String senderId = sendMessageEntity.getSenderEntity().getSenderId();
        String recipientId = sendMessageEntity.getRecipientEntity().getRecipientId();

        ChatConversationDto lastMessageChatConversationDto = new ChatConversationDto(
            sendMessageEntity.getSenderEntity().getSenderId(),
            sendMessageEntity.getSenderEntity().getSenderName(),
            sendMessageEntity.getSenderEntity().getSenderPictureUrl(),
            sendMessageEntity.getRecipientEntity().getRecipientId(),
            sendMessageEntity.getRecipientEntity().getRecipientName(),
            sendMessageEntity.getRecipientEntity().getRecipientPhotoUrl(),
            sendMessageEntity.getMessage(),
            null
        );

        // Save recipient's last message
        firestore
            .collection(CHAT_LAST_MESSAGES_COLLECTION)
            .document(recipientId)
            .collection(LAST_MESSAGES_SUB_COLLECTION)
            .document(senderId)
            .set(lastMessageChatConversationDto)
            .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("ChatRepositoryFirestore", "Last message saved successfully for sender");
                    } else {
                        Log.e("ChatRepositoryFirestore", "Error saving last message for sender", task.getException());
                    }
                }
            );

        // Save sender's last message
        firestore
            .collection(CHAT_LAST_MESSAGES_COLLECTION)
            .document(senderId)
            .collection(LAST_MESSAGES_SUB_COLLECTION)
            .document(recipientId)
            .set(lastMessageChatConversationDto)
            .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("ChatRepositoryFirestore", "Last message saved successfully for recipient");
                    } else {
                        Log.e("ChatRepositoryFirestore", "Error saving last message for recipient", task.getException());
                    }
                }
            );
    }

    @NonNull
    @Override
    public LiveData<List<LastChatMessageEntity>> getLastChatMessagesList(@NonNull String currentUserId) {
        MutableLiveData<List<LastChatMessageEntity>> lastChatMessageReceivedList = new MutableLiveData<>();

        firestore.collection(CHAT_LAST_MESSAGES_COLLECTION)
            .document(currentUserId)
            .collection(LAST_MESSAGES_SUB_COLLECTION)
            .addSnapshotListener((querySnapshot, error) -> {
                    if (error != null) {
                        Log.e("ChatRepositoryFirestore", "Error getting last chat messages list", error);
                        return;
                    }
                    List<LastChatMessageEntity> lastChatMessageEntities = new ArrayList<>();
                    if (querySnapshot != null) {
                        for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                            if (documentSnapshot != null) {
                                Log.d("ChatRepositoryFirestore", "Last chat message received: " + documentSnapshot.getData());
                                LastChatMessageDto lastChatMessageDto = documentSnapshot.toObject(LastChatMessageDto.class);
                                LastChatMessageEntity lastChatMessageEntity = mapToLastChatMessageEntity(documentSnapshot.getId(), lastChatMessageDto);
                                if (lastChatMessageEntity != null) {
                                    lastChatMessageEntities.add(lastChatMessageEntity);
                                }
                            }
                        }
                    }
                    lastChatMessageReceivedList.setValue(lastChatMessageEntities);
                }
            );
        return lastChatMessageReceivedList;
    }


    @NonNull
    @Override
    public LiveData<List<ChatConversationEntity>> getChatConversation(
        @NonNull String currentUserId,
        @NonNull String recipientId
    ) {
        MutableLiveData<List<ChatConversationEntity>> chatMessagesList = new MutableLiveData<>();
        String conversationUid = generateConversationId(currentUserId, recipientId);

        firestore
            .collection(CHAT_COLLECTION)
            .document(conversationUid)
            .collection(MESSAGES_SUB_COLLECTION)
            .orderBy(MESSAGE_TIMESTAMP, Query.Direction.DESCENDING)
            .limit(30)
            .addSnapshotListener((queryDocumentSnapshots, error) -> {
                    if (error != null) {
                        Log.e("ChatRepositoryFirestore", "Error getting chat messages list", error);
                        chatMessagesList.setValue(null);
                    }
                    if (queryDocumentSnapshots != null) {
                        List<ChatConversationEntity> chatMessagesEntities = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            ChatConversationDto chatMessage = queryDocumentSnapshot.toObject(ChatConversationDto.class);
                            ChatConversationEntity conversation = mapToChatConversationEntity(queryDocumentSnapshot.getId(), chatMessage);
                            if (conversation != null) {
                                chatMessagesEntities.add(conversation);
                            }
                        }
                        chatMessagesList.setValue(chatMessagesEntities);
                    }
                }
            );
        return chatMessagesList;
    }

    private String generateConversationId(
        String userId1,
        String userId2
    ) {
        List<String> recipients = new ArrayList<>(Arrays.asList(userId1, userId2));
        Collections.sort(recipients);
        return recipients.get(0) + "_" + recipients.get(1);
    }

    @Nullable
    private ChatConversationEntity mapToChatConversationEntity(
        String documentId,
        ChatConversationDto chatConversationDto
    ) {
        if (chatConversationDto != null &&
            chatConversationDto.getSenderId() != null &&
            chatConversationDto.getSenderName() != null &&
            chatConversationDto.getSenderPictureUrl() != null &&
            chatConversationDto.getRecipientId() != null &&
            chatConversationDto.getRecipientName() != null &&
            chatConversationDto.getRecipientPictureUrl() != null &&
            chatConversationDto.getMessage() != null &&
            chatConversationDto.getTimestamp() != null
        ) {
            return new ChatConversationEntity(
                documentId,
                new SenderEntity(
                    chatConversationDto.getSenderId(),
                    chatConversationDto.getSenderName(),
                    chatConversationDto.getSenderPictureUrl()
                ),
                new RecipientEntity(
                    chatConversationDto.getRecipientId(),
                    chatConversationDto.getRecipientName(),
                    chatConversationDto.getRecipientPictureUrl()
                ),
                chatConversationDto.getMessage(),
                chatConversationDto.getTimestamp()
            );
        } else {
            return null;
        }
    }

    @Nullable
    private LastChatMessageEntity mapToLastChatMessageEntity(
        @Nullable String documentId,
        @Nullable LastChatMessageDto lastChatMessageDto
    ) {
        if (documentId != null &&
            lastChatMessageDto != null &&
            lastChatMessageDto.getMessage() != null &&
            lastChatMessageDto.getSenderId() != null &&
            lastChatMessageDto.getSenderName() != null &&
            lastChatMessageDto.getSenderPictureUrl() != null &&
            lastChatMessageDto.getRecipientId() != null &&
            lastChatMessageDto.getRecipientName() != null &&
            lastChatMessageDto.getRecipientPictureUrl() != null &&
            lastChatMessageDto.getTimestamp() != null
        ) {
            return new LastChatMessageEntity(
                documentId,
                lastChatMessageDto.getMessage(),
                new SenderEntity(
                    lastChatMessageDto.getSenderId(),
                    lastChatMessageDto.getSenderName(),
                    lastChatMessageDto.getSenderPictureUrl()
                ),
                new RecipientEntity(
                    lastChatMessageDto.getRecipientId(),
                    lastChatMessageDto.getRecipientName(),
                    lastChatMessageDto.getRecipientPictureUrl()
                ),
                lastChatMessageDto.getTimestamp()
            );
        } else {
            return null;
        }
    }
}
