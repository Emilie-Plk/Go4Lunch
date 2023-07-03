package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import javax.inject.Inject;

public class SendMessageUseCase {

    private final ChatRepository chatRepository;

    @Inject
    public SendMessageUseCase(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void invoke(
        @NonNull String receiverId,
        @NonNull String receiverName,
        @NonNull String message,
        @NonNull Timestamp timeStamp
    ) {
        chatRepository.sendMessage(new SendMessageEntity(receiverId, receiverName, message, timeStamp));
    }
}
