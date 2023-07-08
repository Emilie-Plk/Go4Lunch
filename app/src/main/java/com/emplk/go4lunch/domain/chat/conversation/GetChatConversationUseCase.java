package com.emplk.go4lunch.domain.chat.conversation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.chat.ChatRepository;

import java.util.List;

import javax.inject.Inject;

public class GetChatConversationUseCase {

    @NonNull
    private final ChatRepository chatRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;


    @Inject
    public GetChatConversationUseCase(
        @NonNull ChatRepository chatRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.chatRepository = chatRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public LiveData<List<ChatConversationEntity>> invoke(@NonNull String recipientId) {
        return chatRepository.getChatConversation(getCurrentLoggedUserIdUseCase.invoke(), recipientId);
    }
}
