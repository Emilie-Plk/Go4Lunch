package com.emplk.go4lunch.domain.chat.send_message;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.conversation.RecipientEntity;
import com.emplk.go4lunch.domain.chat.conversation.SenderEntity;
import com.emplk.go4lunch.domain.chat.last_message.SaveLastChatMessageSentUseCase;

import javax.inject.Inject;

public class SendMessageUseCase {

    @NonNull
    private final ChatRepository chatRepository;

    @NonNull
    private final SaveLastChatMessageSentUseCase saveLastChatMessageSentUseCase;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public SendMessageUseCase(
        @NonNull ChatRepository chatRepository,
        @NonNull SaveLastChatMessageSentUseCase saveLastChatMessageSentUseCase,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.chatRepository = chatRepository;
        this.saveLastChatMessageSentUseCase = saveLastChatMessageSentUseCase;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public void invoke(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String recipientPhotoUrl,
        @NonNull String message
    ) {
        LoggedUserEntity currentUser = getCurrentLoggedUserUseCase.invoke();
        if (currentUser == null || currentUser.getPictureUrl() == null) {
            return;
        }
        chatRepository.sendMessage(
            new SendMessageEntity(
                new SenderEntity(
                    currentUser.getId(),
                    currentUser.getName(),
                    currentUser.getPictureUrl()
                ),
                new RecipientEntity(
                    recipientId,
                    recipientName,
                    recipientPhotoUrl
                ),
                message
            )
        );

        // also save last message in Firestore
        saveLastChatMessageSentUseCase.invoke(
            recipientId,
            recipientName,
            recipientPhotoUrl,
            message);
    }
}
