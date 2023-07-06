package com.emplk.go4lunch.ui.chat.list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.chat.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.GetLastChatMessageUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatLastMessageListViewModel extends ViewModel {

    @NonNull
    private final GetLastChatMessageUseCase getLastChatMessageUseCase;

    @Inject
    public ChatLastMessageListViewModel(@NonNull GetLastChatMessageUseCase getLastChatMessageUseCase) {
        this.getLastChatMessageUseCase = getLastChatMessageUseCase;
    }

    public LiveData<List<ChatLastMessageViewStateItem>> getChatLastMessageViewStateItems() {
        return Transformations.switchMap(
            getLastChatMessageUseCase.invoke(),
            chatConversationEntities -> {
                List<ChatLastMessageViewStateItem> chatLastMessageViewStateItemList = new ArrayList<>();
                for (ChatConversationEntity chatConversationEntity : chatConversationEntities) {
                    chatLastMessageViewStateItemList.add(new ChatLastMessageViewStateItem(
                        chatConversationEntity.getUserId(),
                        chatConversationEntity.getUserName(),
                        chatConversationEntity.getMessage(),
                        "https://picsum.photos/200/300",
                        chatConversationEntity.getTimestamp().toDate().toString()
                    ));
                }
                return new MutableLiveData<>(chatLastMessageViewStateItemList);
            }
        );
    }
}
