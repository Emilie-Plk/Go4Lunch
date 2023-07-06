package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;

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

    public LiveData<List<LastChatMessageEntity>> invoke() {
        return chatRepository.getLastChatMessagesList();
    }
}
