package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public interface ChatRepository {

    void sendMessage(@NonNull SendMessageEntity sendMessageEntity);

    @NonNull
    LiveData<List<ChatConversationEntity>> getChatMessagesList();

    @NonNull
    LiveData<List<ChatConversationEntity>> getChatConversation(@NonNull String receiverId);
}
