package com.emplk.go4lunch.data.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.chat.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.SendMessageEntity;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ChatRepositoryFirestore implements ChatRepository {

    private static final String CHAT_COLLECTION = "chat_conversations";

    private static final String USERS_SUBCOLLECTION = "users";
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";

    @NonNull
    private final FirebaseFirestore firestore;

    @NonNull
    private final FirebaseAuth firebaseAuth;

    @NonNull
    private String currentUserId;

    @NonNull
    private String currentUserName;

    @NonNull
    private final Clock clock;

    @Inject
    public ChatRepositoryFirestore(
        @NonNull FirebaseFirestore firestore,
        @NonNull FirebaseAuth firebaseAuth,
        @NonNull Clock clock
    ) {
        this.firestore = firestore;
        this.firebaseAuth = firebaseAuth;
        this.clock = clock;

        if (firebaseAuth.getCurrentUser() != null && firebaseAuth.getCurrentUser().getDisplayName() != null) {
            currentUserId = firebaseAuth.getCurrentUser().getUid();
            currentUserName = firebaseAuth.getCurrentUser().getDisplayName();
        } else {
            throw new IllegalStateException("User is not logged in!");
        }
    }


    @Override
    public void sendMessage(@NonNull SendMessageEntity sendMessageEntity) {
        String receiverId = sendMessageEntity.getReceiverId();
        String receiverName = sendMessageEntity.getReceiverName();
        Timestamp timeStamp = sendMessageEntity.getTimeStamp();
        String conversationUid = generateConversationId(currentUserId, sendMessageEntity.getReceiverId());

        DocumentReference documentReference = firestore  // Upsert subcollection "users"
            .collection(CHAT_COLLECTION)
            .document(conversationUid);

        CollectionReference conversationRef = documentReference.collection(USERS_SUBCOLLECTION); // I want to add two fields "userId" and "userName" to this subcollection


        HashMap<String, Object> currentUserData = new HashMap<>();
        currentUserData.put(USER_ID, currentUserId);
        currentUserData.put(USER_NAME, currentUserName);
        conversationRef.document(currentUserId)
            .set(currentUserData);

        HashMap<String, Object> receiverData = new HashMap<>();
        receiverData.put(USER_ID, receiverId);
        receiverData.put(USER_NAME, receiverName);
        conversationRef.document(receiverId)
            .set(receiverData);

        documentReference.set(
            new SendMessageDto(
                receiverId,
                receiverName,
                sendMessageEntity.getMessage(),
                timeStamp
            )
        );
    }

    @NonNull
    @Override
    public LiveData<List<ChatConversationEntity>> getUserChatMessages() {
        return null;
    }

    @NonNull
    @Override
    public LiveData<List<ChatConversationEntity>> getUserChatConversation() {
        return null;
    }

    private String generateConversationId(
        String userId1,
        String userId2
    ) {
        String smallerId = userId1.compareTo(userId2) < 0 ? userId1 : userId2;
        String largerId = userId1.compareTo(userId2) >= 0 ? userId1 : userId2;

        return smallerId + "_" + largerId;
    }
}
