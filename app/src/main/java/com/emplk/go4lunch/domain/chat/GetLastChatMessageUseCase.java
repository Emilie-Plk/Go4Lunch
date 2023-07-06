package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;

import java.util.List;

import javax.inject.Inject;

public class GetLastChatMessageUseCase {

    @NonNull
    private final ChatRepository chatRepository;


    @Inject
    public GetLastChatMessageUseCase(
        @NonNull ChatRepository chatRepository
    ) {
        this.chatRepository = chatRepository;
    }

    public LiveData<List<ChatConversationEntity>> invoke() {
     return chatRepository.getLastChatMessagesList();
    }
}
