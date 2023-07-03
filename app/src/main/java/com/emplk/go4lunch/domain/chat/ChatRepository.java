package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public interface ChatRepository {

    void sendMessage(@NonNull String receiverId, @NonNull String message, @NonNull String timeStamp);

    @NonNull
    LiveData<List<ChatConversationEntity>> getUserChatMessages();

    @NonNull
    LiveData<List<ChatConversationEntity>> getUserChatConversation();
}
