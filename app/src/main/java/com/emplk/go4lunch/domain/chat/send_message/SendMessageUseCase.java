package com.emplk.go4lunch.domain.chat.send_message;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.last_message.SaveLastChatMessageSentUseCase;

import javax.inject.Inject;

public class SendMessageUseCase {

    @NonNull
    private final ChatRepository chatRepository;

    @NonNull
    private final SaveLastChatMessageSentUseCase saveLastChatMessageSentUseCase;

    @Inject
    public SendMessageUseCase(
        @NonNull ChatRepository chatRepository,
        @NonNull SaveLastChatMessageSentUseCase saveLastChatMessageSentUseCase
    ) {
        this.chatRepository = chatRepository;
        this.saveLastChatMessageSentUseCase = saveLastChatMessageSentUseCase;
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

        saveLastChatMessageSentUseCase.invoke(
            recipientId,
            recipientName,
            recipientPhotoUrl,
            message);
    }
}
