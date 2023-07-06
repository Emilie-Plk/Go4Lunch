package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;

import java.util.List;

public interface ChatRepository {

    void sendMessage(@NonNull SendMessageEntity sendMessageEntity);

    void saveLastMessage(@NonNull SendMessageEntity sendMessageEntity);

    @NonNull
    LiveData<List<LastChatMessageEntity>> getLastChatMessagesList();

    @NonNull
    LiveData<List<ChatConversationEntity>> getChatConversation(@NonNull String recipientId);
}
