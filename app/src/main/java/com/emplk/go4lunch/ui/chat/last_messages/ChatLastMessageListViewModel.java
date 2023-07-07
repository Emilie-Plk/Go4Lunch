package com.emplk.go4lunch.ui.chat.last_messages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.chat.last_message.GetLastChatMessageUseCase;
import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;
import com.google.firebase.Timestamp;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatLastMessageListViewModel extends ViewModel {

    @NonNull
    private final GetLastChatMessageUseCase getLastChatMessageUseCase;

    @Nullable
    private final LoggedUserEntity currentUser;

    @Inject
    public ChatLastMessageListViewModel(
        @NonNull GetLastChatMessageUseCase getLastChatMessageUseCase,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.getLastChatMessageUseCase = getLastChatMessageUseCase;

        if (getCurrentLoggedUserUseCase.invoke() != null) {
            this.currentUser = getCurrentLoggedUserUseCase.invoke();
        } else {
            this.currentUser = null;
        }
    }

    public LiveData<List<ChatLastMessageViewStateItem>> getChatLastMessageViewStateItems() {
        return Transformations.switchMap(
            getLastChatMessageUseCase.invoke(),
            lastChatMessageEntities -> {
                List<ChatLastMessageViewStateItem> chatLastMessageViewStateItemList = new ArrayList<>();
                for (LastChatMessageEntity lastChatMessageEntity : lastChatMessageEntities) {
                    String workmateId = getWorkmateId(lastChatMessageEntity.getRecipientId(), lastChatMessageEntity.getSenderId());
                    String workmateName = getWorkmateUserName(lastChatMessageEntity.getRecipientName(), lastChatMessageEntity.getSenderName());
                    String workmatePhotoUrl = getWorkmatePhotoUrl(lastChatMessageEntity.getRecipientPhotoUrl(), lastChatMessageEntity.getSenderPhotoUrl());

                    chatLastMessageViewStateItemList.add(
                        new ChatLastMessageViewStateItem(
                            workmateId,
                            workmateName,
                            lastChatMessageEntity.getLastMessage(),
                            workmatePhotoUrl,
                            formatTimestamp(lastChatMessageEntity.getTimestamp())
                        )
                    );
                }
                return new MutableLiveData<>(chatLastMessageViewStateItemList);
            }
        );
    }

    @NonNull
    private String getWorkmatePhotoUrl(
        String recipientPhotoUrl,
        String senderPhotoUrl
    ) {
        if (currentUser != null &&
            recipientPhotoUrl.equals(currentUser.getPictureUrl())
        ) {
            return senderPhotoUrl;
        } else {
            return recipientPhotoUrl;
        }
    }

    @NonNull
    private String getWorkmateUserName(
        String recipientName,
        String senderName
    ) {
        if (currentUser != null &&
            recipientName.equals(currentUser.getName())
        ) {
            return senderName;
        } else {
            return recipientName;
        }
    }

    @NonNull
    private String getWorkmateId(
        String recipientId,
        String senderId
    ) {
        if (currentUser != null &&
            recipientId.equals(currentUser.getId())
        ) {
            return senderId;
        } else {
            return recipientId;
        }
    }

    @NonNull
    private String formatTimestamp(@NonNull Timestamp timestamp) {
        Locale locale = Locale.getDefault();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale);
        return dateFormat.format(timestamp.toDate());
    }
}
