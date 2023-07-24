package com.emplk.go4lunch.domain.chat.last_message;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.chat.ChatRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class GetLastChatMessageSortedChronologicallyUseCase {

    @NonNull
    private final ChatRepository chatRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public GetLastChatMessageSortedChronologicallyUseCase(
        @NonNull ChatRepository chatRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.chatRepository = chatRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public LiveData<List<LastChatMessageEntity>> invoke() {
        String currentLoggedUserId = getCurrentLoggedUserIdUseCase.invoke();
        return Transformations.map(chatRepository.getLastChatMessagesList(currentLoggedUserId), lastChatMessageEntities -> {
                Collections.sort(lastChatMessageEntities, Comparator.comparing(
                        lastChatMessageEntity -> lastChatMessageEntity.getTimestamp()
                    )
                );
                return lastChatMessageEntities;
            }
        );
    }
}
