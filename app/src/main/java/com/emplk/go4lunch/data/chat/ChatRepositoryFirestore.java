package com.emplk.go4lunch.data.chat;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.chat.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.SendMessageEntity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatRepositoryFirestore implements ChatRepository {

    private static final String CHAT_COLLECTION = "chat_conversations";

    private static final String USERS_SUBCOLLECTION = "users";

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

        DocumentReference documentReference = firestore  // Upsert subcollection "users"
            .collection(CHAT_COLLECTION)
            .document(conversationUid);

        CollectionReference usersRef = documentReference.collection(USERS_SUBCOLLECTION);

        HashMap<String, Object> currentUserData = new HashMap<>();
        currentUserData.put(USER_ID, currentUserId);
        currentUserData.put(USER_NAME, currentUserName);
        usersRef.document(currentUserId)
            .set(currentUserData);


        HashMap<String, Object> receiverData = new HashMap<>();
        receiverData.put(USER_ID, recipientId);
        receiverData.put(USER_NAME, recipientName);
        usersRef.document(recipientId)
            .set(receiverData);

        CollectionReference messagesRef = documentReference.collection(MESSAGES_SUBCOLLECTION);

        DocumentReference messageDocumentRef = messagesRef.document();

        messagesRef
            .document(conversationUid + "_" + messageDocumentRef.getId())
            .set(
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

        firestore
            .collection(CHAT_COLLECTION)
            .whereArrayContains(FieldPath.documentId(), currentUserId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener((queryDocumentSnapshots, error) -> {
                    if (error != null) {
                        Log.e("ChatRepositoryFirestore", "Error getting chat messages list", error);
                        lastChatMessageReceivedList.setValue(null);
                    }
                    if (queryDocumentSnapshots != null) {
                        List<ChatConversationEntity> chatMessagesEntities = new ArrayList<>();
                        for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                            // I want to get something like the "first" document as is is timestamp sorted already
                            ChatConversationDto chatMessage = queryDocumentSnapshot.toObject(ChatConversationDto.class);
                            ChatConversationEntity conversation = mapToChatConversationEntity(chatMessage);
                            chatMessagesEntities.add(conversation);
                        }
                        lastChatMessageReceivedList.setValue(chatMessagesEntities);
                    }
                }
            );
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
