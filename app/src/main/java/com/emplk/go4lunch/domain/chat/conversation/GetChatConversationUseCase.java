package com.emplk.go4lunch.domain.chat.conversation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.chat.ChatRepository;

import java.util.Collections;
import java.util.Comparator;
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
        String currentLoggedUserId = getCurrentLoggedUserIdUseCase.invoke();
        return Transformations.map(chatRepository.getChatConversation(currentLoggedUserId, recipientId), chatConversationEntities -> {
                Collections.sort(chatConversationEntities, Comparator.comparing(
                        chatConversationEntity -> chatConversationEntity.getTimestamp()
                    )
                );
                return chatConversationEntities;
            }
        );
    }
}
