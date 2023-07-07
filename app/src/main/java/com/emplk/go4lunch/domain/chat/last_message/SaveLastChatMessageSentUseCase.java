package com.emplk.go4lunch.domain.chat.last_message;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.send_message.SendMessageEntity;

import javax.inject.Inject;

public class SaveLastChatMessageSentUseCase {

    @NonNull
    private final ChatRepository chatRepository;

    @Inject
    public SaveLastChatMessageSentUseCase(@NonNull ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void invoke(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String recipientPhotoUrl,
        @NonNull String message
    ) {
        chatRepository.saveLastMessage(
            new SendMessageEntity(
                recipientId,
                recipientName,
                recipientPhotoUrl,
                message
            )
        );
    }
}
