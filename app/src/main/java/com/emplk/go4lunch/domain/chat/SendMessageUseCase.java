package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import javax.inject.Inject;

public class SendMessageUseCase {

    private final ChatRepository chatRepository;

    @Inject
    public SendMessageUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void invoke(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String message
    ) {
        chatRepository.sendMessage(
            new SendMessageEntity(
                recipientId,
                recipientName,
                message
            )
        );
    }
}
