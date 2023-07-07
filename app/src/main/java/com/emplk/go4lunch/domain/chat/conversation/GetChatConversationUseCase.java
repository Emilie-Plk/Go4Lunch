package com.emplk.go4lunch.domain.chat.conversation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.chat.ChatRepository;

import java.util.List;

import javax.inject.Inject;

public class GetChatConversationUseCase {

    @NonNull
    private final ChatRepository chatRepository;


    @Inject
    public GetChatConversationUseCase(
        @NonNull ChatRepository chatRepository
    ) {
        this.chatRepository = chatRepository;
    }

    public LiveData<List<ChatConversationEntity>> invoke(@NonNull String recipientId) {
        return chatRepository.getChatConversation(recipientId);
    }
}
