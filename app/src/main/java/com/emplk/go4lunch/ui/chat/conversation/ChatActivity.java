package com.emplk.go4lunch.ui.chat.conversation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emplk.go4lunch.databinding.ChatActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatActivity extends AppCompatActivity {

    private ChatActivityBinding binding;

    private ChatViewModel viewModel;

    private static final String WORKMATE_ID = "workmateId";
    private static final String WORKMATE_NAME = "workmateName";

    public static Intent navigate(
        @NonNull Context context,
        @NonNull String workmateId,
        @NonNull String workmateName
    ) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(WORKMATE_ID, workmateId);
        intent.putExtra(WORKMATE_NAME, workmateName);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.chatToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this).get(ChatViewModel.class);

        initRecyclerView();

        binding.chatMessageInputEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                CharSequence s,
                int start,
                int count,
                int after
            ) {
            }

            @Override
            public void onTextChanged(
                CharSequence s,
                int start,
                int before,
                int count
            ) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        binding.chatSendFabButton.setOnClickListener(v -> {
                if (binding.chatMessageInputEt.getText() != null) {
                    String message = binding.chatMessageInputEt.getText().toString();
                    if (!message.isEmpty()) {
                        viewModel.sendMessage(
                            getIntent().getStringExtra(WORKMATE_ID),
                            getIntent().getStringExtra(WORKMATE_NAME),
                            message
                        );
                        binding.chatMessageInputEt.setText("");
                    }
                }
            }
        );
    }

    private void initRecyclerView() {
        ChatListAdapter adapter = new ChatListAdapter();
        binding.chatRv.setAdapter(adapter);
        binding.chatRv.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getChatMessages(getIntent().getStringExtra(WORKMATE_ID)).observe(this, chatMessageViewStateItems -> {
            if (chatMessageViewStateItems != null && !chatMessageViewStateItems.isEmpty()) {
                adapter.submitList(chatMessageViewStateItems);
            }
            binding.chatRv.smoothScrollToPosition(adapter.getItemCount());
        });

    }


}