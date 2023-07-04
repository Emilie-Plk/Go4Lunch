package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

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

    public LiveData<List<ChatConversationEntity>> invoke(@NonNull String receiverId) {
        return chatRepository.getChatConversation(receiverId);
    }
}
