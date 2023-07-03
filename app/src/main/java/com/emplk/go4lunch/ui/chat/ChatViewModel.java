package com.emplk.go4lunch.ui.chat;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.chat.SendMessageUseCase;
import com.google.firebase.Timestamp;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatViewModel extends ViewModel {

    private final SendMessageUseCase sendMessageUseCase;

    @Inject
    public ChatViewModel(
        SendMessageUseCase sendMessageUseCase
    ) {
        this.sendMessageUseCase = sendMessageUseCase;
    }

    public void sendMessage(
        @NonNull String receiverId,
        @NonNull String receiverName,
        @NonNull String message
    ) {
        sendMessageUseCase.invoke(
            receiverId,
            receiverName,
            message,
            Timestamp.now()
        );
    }

    public LiveData<List<ChatMessageViewStateItem>> getChatMessages() {
        return null;
    }
}
