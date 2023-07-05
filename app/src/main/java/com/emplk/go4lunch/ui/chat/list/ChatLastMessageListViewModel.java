package com.emplk.go4lunch.ui.chat.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ChatLastMessageListViewModel extends ViewModel {

    @Inject
    public ChatLastMessageListViewModel() {
    }

    public LiveData<List<ChatLastMessageViewStateItem>> getChatLastMessageViewStateItems() {
        return null;
    }
}
