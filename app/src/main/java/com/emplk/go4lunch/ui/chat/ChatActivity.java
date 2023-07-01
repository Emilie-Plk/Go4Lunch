package com.emplk.go4lunch.ui.chat;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.databinding.ChatActivityBinding;

public class ChatActivity extends AppCompatActivity {

    private ChatActivityBinding binding;

    private ChatViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);
    }
}