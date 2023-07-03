package com.emplk.go4lunch.data.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.chat.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.time.Clock;
import java.util.List;

public class ChatRepositoryFirestore implements ChatRepository {

    private static final String CHAT_COLLECTION = "chat_conversations";

    private static final String USERS_SUBCOLLECTION = "users";

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
    public void sendMessage(
        @NonNull String receiverId,
        @NonNull String message,
        @NonNull String timeStamp
    ) {

        String conversationUid = generateConversationId(currentUserId, receiverId);

        CollectionReference conversationRef = firestore
            .collection(CHAT_COLLECTION)
            .document(conversationUid)
            .collection(USERS_SUBCOLLECTION);

        DocumentReference messageRef = conversationRef.document(currentUserId + "_" + timeStamp);

        messageRef.set(new ChatConversationDto(
            currentUserId,
            receiverId,
            message,
            timeStamp
        ));
    }

    @NonNull
    @Override
    public LiveData<List<ChatConversationEntity>> getUserChatMessages() {
        //
    }

    @NonNull
    @Override
    public LiveData<List<ChatConversationEntity>> getUserChatConversation() {
        //
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
