package com.emplk.go4lunch.data.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.chat.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.SendMessageEntity;
import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatRepositoryFirestore implements ChatRepository {

    private static final String CHAT_COLLECTION = "chat_conversations";
    private static final String CHAT_LAST_MESSAGES_COLLECTION = "chat_last_messages";
    private static final String MESSAGES_SUBCOLLECTION = "messages";

    private static final String LAST_MESSAGE_SUBCOLLECTION = "last_message";


    @NonNull
    private final FirebaseFirestore firestore;

    @NonNull
    private final String currentUserId;
    @NonNull
    private final String currentUserName;
    @NonNull
    private final String currentUserPictureUrl;

    @Inject
    public ChatRepositoryFirestore(
        @NonNull FirebaseFirestore firestore,
        @NonNull FirebaseAuth firebaseAuth
    ) {
        this.firestore = firestore;

        if (firebaseAuth.getCurrentUser() != null &&
            firebaseAuth.getCurrentUser().getDisplayName() != null &&
            firebaseAuth.getCurrentUser().getPhotoUrl() != null
        ) {
            currentUserId = firebaseAuth.getCurrentUser().getUid();
            currentUserName = firebaseAuth.getCurrentUser().getDisplayName();
            currentUserPictureUrl = firebaseAuth.getCurrentUser().getPhotoUrl().toString();
        } else {
            throw new IllegalStateException("User is not logged in!");
        }
    }


    @Override
    public void sendMessage(@NonNull SendMessageEntity sendMessageEntity) {
        String recipientId = sendMessageEntity.getRecipientId();
        String recipientName = sendMessageEntity.getRecipientName();
        String conversationUid = generateConversationId(currentUserId, sendMessageEntity.getRecipientId());

        firestore
            .collection(CHAT_COLLECTION)
            .document(conversationUid)
            .collection(MESSAGES_SUBCOLLECTION)
            .add(
                new SendMessageDto(
                    recipientId,
                    recipientName,
                    sendMessageEntity.getMessage(),
                    null
                )
            )
            .addOnSuccessListener(aVoid -> {
                    Log.d("ChatRepositoryFirestore", "Message sent successfully");
                }
            )
            .addOnFailureListener(e -> {
                    Log.e("ChatRepositoryFirestore", "Error sending message", e);
                }
            );
    }

    @Override
    public void saveLastMessage(@NonNull SendMessageEntity sendMessageEntity) {
        String recipientId = sendMessageEntity.getRecipientId();

        Map<String, Object> lastMessage = new HashMap<>();
        lastMessage.put("message", sendMessageEntity.getMessage());
        lastMessage.put("message_date", FieldValue.serverTimestamp());
        lastMessage.put("sender_id", currentUserId);
        lastMessage.put("sender_name", currentUserName);
        lastMessage.put("sender_photo_url", currentUserPictureUrl);
        lastMessage.put("recipient_id", sendMessageEntity.getRecipientId());
        lastMessage.put("recipient_name", sendMessageEntity.getRecipientName());
        lastMessage.put("recipient_photo_url", sendMessageEntity.getRecipientPhotoUrl());

        firestore
            .collection(CHAT_LAST_MESSAGES_COLLECTION)
            .document(currentUserId)
            .collection(LAST_MESSAGE_SUBCOLLECTION)
            .document(recipientId)
            .set(lastMessage)
            .addOnSuccessListener(aVoid -> {
                    Log.d("ChatRepositoryFirestore", "Last message saved successfully for currentUser");
                }
            ).addOnFailureListener(e -> {
                    Log.e("ChatRepositoryFirestore", "Error saving last message for currentUser", e);
                }
            );

        firestore
            .collection(CHAT_LAST_MESSAGES_COLLECTION)
            .document(recipientId)
            .collection(LAST_MESSAGE_SUBCOLLECTION)
            .document(currentUserId)
            .set(lastMessage)
            .addOnSuccessListener(aVoid -> {
                    Log.d("ChatRepositoryFirestore", "Last message saved successfully for recipient");
                }
            ).addOnFailureListener(e -> {
                    Log.e("ChatRepositoryFirestore", "Error saving last message for recipient", e);
                }
            );
    }

    @NonNull
    @Override
    public LiveData<List<LastChatMessageEntity>> getLastChatMessagesList() {
        MutableLiveData<List<LastChatMessageEntity>> lastChatMessageReceivedList = new MutableLiveData<>();

        firestore.collection(CHAT_LAST_MESSAGES_COLLECTION)
            .document(currentUserId)
            .collection(LAST_MESSAGE_SUBCOLLECTION)
            .addSnapshotListener((querySnapshot, error) -> {
                if (error != null) {
                    Log.e("ChatRepositoryFirestore", "Error getting last chat messages list", error);
                    return;
                }

                List<LastChatMessageEntity> lastChatMessageEntities = new ArrayList<>();
                if (querySnapshot != null) {
                    for (DocumentSnapshot documentSnapshot : querySnapshot.getDocuments()) {
                        if (documentSnapshot != null) {
                            LastChatMessageEntity lastChatMessageEntity = mapToLastChatMessageEntity(documentSnapshot);
                            if (lastChatMessageEntity != null) {
                                lastChatMessageEntities.add(lastChatMessageEntity);
                            }
                        }
                    }
                }

                lastChatMessageReceivedList.setValue(lastChatMessageEntities);
            });

        return lastChatMessageReceivedList;
    }


    @NonNull
    @Override
    public LiveData<List<ChatConversationEntity>> getChatConversation(@NonNull String recipientId) {
        MutableLiveData<List<ChatConversationEntity>> chatMessagesList = new MutableLiveData<>();
        String conversationUid = generateConversationId(currentUserId, recipientId);

        firestore
            .collection(CHAT_COLLECTION)
            .document(conversationUid)
            .collection(MESSAGES_SUBCOLLECTION)
            .orderBy("timestamp", Query.Direction.ASCENDING)
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
                            ChatConversationEntity conversation = mapToChatConversationEntity(chatMessage);
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
        String smallerId = userId1.compareTo(userId2) < 0 ? userId1 : userId2;
        String largerId = userId1.compareTo(userId2) >= 0 ? userId1 : userId2;

        return smallerId + "_" + largerId;
    }

    private ChatConversationEntity mapToChatConversationEntity(ChatConversationDto chatConversationDto) {
        if (chatConversationDto.getRecipientId() != null &&
            chatConversationDto.getRecipientName() != null &&
            chatConversationDto.getMessage() != null &&
            chatConversationDto.getTimestamp() != null) {
            return new ChatConversationEntity(
                chatConversationDto.getRecipientId(),
                chatConversationDto.getRecipientName(),
                chatConversationDto.getMessage(),
                chatConversationDto.getTimestamp()
            );
        } else {
            return null;
        }
    }

    private LastChatMessageEntity mapToLastChatMessageEntity(DocumentSnapshot documentSnapshot) {
        Map<String, Object> data = documentSnapshot.getData();

        if (data != null) {
            String lastMessage = (String) data.get("message");
            Timestamp timestamp = (Timestamp) data.get("message_date");
            String senderId = (String) data.get("sender_id");
            String senderName = (String) data.get("sender_name");
            String senderPhotoUrl = (String) data.get("sender_photo_url");
            String recipientId = (String) data.get("recipient_id");
            String recipientName = (String) data.get("recipient_name");
            String recipientPhotoUrl = (String) data.get("recipient_photo_url");

            if (lastMessage != null &&
                recipientId != null &&
                recipientName != null &&
                recipientPhotoUrl != null &&
                senderId != null &&
                senderName != null &&
                senderPhotoUrl != null &&
                timestamp != null
            ) {
                return new LastChatMessageEntity(lastMessage, senderId, senderName, senderPhotoUrl, recipientId, recipientName, recipientPhotoUrl, timestamp);
            }
        }
        return null;
    }
}
