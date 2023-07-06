package com.emplk.go4lunch.data.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.chat.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.SendMessageEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatRepositoryFirestore implements ChatRepository {

    private static final String CHAT_COLLECTION = "chat_conversations";

    private static final String MESSAGES_SUBCOLLECTION = "messages";
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";

    @NonNull
    private final FirebaseFirestore firestore;

    @NonNull
    private final String currentUserId;

    @NonNull
    private final String currentUserName;

    @Inject
    public ChatRepositoryFirestore(
        @NonNull FirebaseFirestore firestore,
        @NonNull FirebaseAuth firebaseAuth
    ) {
        this.firestore = firestore;

        if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().getDisplayName() != null) {
            currentUserId = firebaseAuth.getCurrentUser().getUid();
            currentUserName = firebaseAuth.getCurrentUser().getDisplayName();
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

    @NonNull
    @Override
    public LiveData<List<ChatConversationEntity>> getLastChatMessagesList() {
        MutableLiveData<List<ChatConversationEntity>> lastChatMessageReceivedList = new MutableLiveData<>();

        String currentUserIdWithSuffix = currentUserId + "\uFFFD";

        firestore
            .collection(CHAT_COLLECTION)
            .whereGreaterThanOrEqualTo(FieldPath.documentId(), currentUserId)
            .whereLessThan(FieldPath.documentId(), currentUserIdWithSuffix)
            .addSnapshotListener((queryDocumentSnapshots, error) -> {
                if (error != null) {
                    Log.e("ChatRepositoryFirestore", "Error getting chat messages list", error);
                    lastChatMessageReceivedList.setValue(null);
                    return;
                }

                if (queryDocumentSnapshots != null) {
                    List<ChatConversationEntity> chatMessagesEntities = new ArrayList<>();

                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                        String conversationId = queryDocumentSnapshot.getId();

                        firestore
                            .collection(CHAT_COLLECTION)
                            .document(conversationId)
                            .collection(MESSAGES_SUBCOLLECTION)
                            .orderBy("timestamp", Query.Direction.DESCENDING)
                            .limit(1)
                            .get()
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    QuerySnapshot messageQuerySnapshot = task.getResult();
                                    if (messageQuerySnapshot != null && !messageQuerySnapshot.isEmpty()) {
                                        DocumentSnapshot lastMessageSnapshot = messageQuerySnapshot.getDocuments().get(0);
                                        ChatConversationDto chatMessage = lastMessageSnapshot.toObject(ChatConversationDto.class);
                                        ChatConversationEntity conversation = mapToChatConversationEntity(chatMessage);
                                        chatMessagesEntities.add(conversation);
                                    }
                                    lastChatMessageReceivedList.setValue(chatMessagesEntities);
                                } else {
                                    Log.e("ChatRepositoryFirestore", "Error getting chat messages", task.getException());
                                }
                            });
                    }
                } else {
                    lastChatMessageReceivedList.setValue(new ArrayList<>());
                }
            });

        return lastChatMessageReceivedList;
    }



    @NonNull
    @Override
    public LiveData<List<ChatConversationEntity>> getChatConversation(@NonNull String receiverId) {
        MutableLiveData<List<ChatConversationEntity>> chatMessagesList = new MutableLiveData<>();
        String conversationUid = generateConversationId(currentUserId, receiverId);

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
}
