package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.ui.chat.conversation.MessageTypeState;

import javax.inject.Inject;

public class SetMessageTypeStateUseCase {

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public SetMessageTypeStateUseCase(
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public MessageTypeState invoke(@NonNull String recipientId) {
        String currentUserId = getCurrentLoggedUserIdUseCase.invoke();
        if (!currentUserId.equals(recipientId)) {
            return MessageTypeState.SENDER;
        } else {
            return MessageTypeState.RECIPIENT;
        }
    }
}
