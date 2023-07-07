package com.emplk.go4lunch.domain.chat.last_message;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.chat.ChatRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

public class GetLastChatMessageSortedChronologicallyUseCase {

    @NonNull
    private final ChatRepository chatRepository;

    @Inject
    public GetLastChatMessageSortedChronologicallyUseCase(
        @NonNull ChatRepository chatRepository
    ) {
        this.chatRepository = chatRepository;
    }

    public LiveData<List<LastChatMessageEntity>> invoke() {
        return Transformations.map(chatRepository.getLastChatMessagesList(), lastChatMessageEntities -> {
                Collections.sort(lastChatMessageEntities, Comparator.comparing(
                        lastChatMessageEntity -> lastChatMessageEntity.getTimestamp()
                    )
                );
                return lastChatMessageEntities;
            }
        );
    }
}
