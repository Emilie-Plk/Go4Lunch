package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.chat.last_message.SaveLastMessageSentUseCase;

import javax.inject.Inject;

public class SendMessageUseCase {

    @NonNull
    private final ChatRepository chatRepository;

    @NonNull
    private final SaveLastMessageSentUseCase saveLastMessageSentUseCase;

    @Inject
    public SendMessageUseCase(
        @NonNull ChatRepository chatRepository,
        @NonNull SaveLastMessageSentUseCase saveLastMessageSentUseCase
    ) {
        this.chatRepository = chatRepository;
        this.saveLastMessageSentUseCase = saveLastMessageSentUseCase;
    }

    public void invoke(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String recipientPhotoUrl,
        @NonNull String message
    ) {
        chatRepository.sendMessage(
            new SendMessageEntity(
                recipientId,
                recipientName,
                recipientPhotoUrl,
                message
            )
        );

        saveLastMessageSentUseCase.invoke(
            recipientId,
            recipientName,
            recipientPhotoUrl,
            message);
    }
}
