package com.emplk.go4lunch.domain.chat.last_message;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.chat.conversation.RecipientEntity;
import com.emplk.go4lunch.domain.chat.conversation.SenderEntity;
import com.emplk.go4lunch.domain.chat.send_message.SendMessageEntity;

import javax.inject.Inject;

public class SaveLastChatMessageSentUseCase {

    @NonNull
    private final ChatRepository chatRepository;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public SaveLastChatMessageSentUseCase(
        @NonNull ChatRepository chatRepository,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.chatRepository = chatRepository;
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
        chatRepository.saveLastMessage(
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
    }
}
