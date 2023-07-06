package com.emplk.go4lunch.ui.chat.conversation;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.chat.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.GetChatConversationUseCase;
import com.emplk.go4lunch.domain.chat.SendMessageUseCase;
import com.emplk.go4lunch.domain.chat.SetMessageTypeStateUseCase;
import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatConversationViewModel extends ViewModel {

    private final SendMessageUseCase sendMessageUseCase;

    private final GetChatConversationUseCase getChatConversationUseCase;

    private final SetMessageTypeStateUseCase setMessageTypeStateUseCase;

    @Inject
    public ChatConversationViewModel(
        SendMessageUseCase sendMessageUseCase,
        GetChatConversationUseCase getChatConversationUseCase,
        SetMessageTypeStateUseCase setMessageTypeStateUseCase
    ) {
        this.sendMessageUseCase = sendMessageUseCase;
        this.getChatConversationUseCase = getChatConversationUseCase;
        this.setMessageTypeStateUseCase = setMessageTypeStateUseCase;
    }

    public void sendMessage(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String recipientPhotoUrl,
        @NonNull String message
    ) {
        sendMessageUseCase.invoke(
            recipientId,
            recipientName,
            recipientPhotoUrl,
            message
        );
    }

    public LiveData<List<ChatMessageViewStateItem>> getChatMessages(@NonNull String workmateId) {
        return Transformations.switchMap(
            getChatConversationUseCase.invoke(workmateId),
            chatConversationEntities -> {
                List<ChatMessageViewStateItem> chatMessageViewStateItems = new ArrayList<>();
                if (chatConversationEntities != null && !chatConversationEntities.isEmpty()) {
                    for (ChatConversationEntity chatConversationEntity : chatConversationEntities) {
                        chatMessageViewStateItems.add(new ChatMessageViewStateItem(
                                chatConversationEntity.getUserId(),
                                chatConversationEntity.getUserName(),
                                chatConversationEntity.getMessage(),
                                formatTimestamp(chatConversationEntity.getTimestamp()),
                                setMessageTypeStateUseCase.invoke(chatConversationEntity.getUserId())
                            )
                        );
                    }
                }
                return new MutableLiveData<>(chatMessageViewStateItems);
            }
        );
    }

    private String formatTimestamp(@NonNull Timestamp timestamp) {
        Locale locale = Locale.getDefault();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
        return dateFormat.format(timestamp.toDate());
    }
}
