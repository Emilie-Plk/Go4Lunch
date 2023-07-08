package com.emplk.go4lunch.ui.chat.conversation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.emplk.go4lunch.databinding.ChatActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatConversationActivity extends AppCompatActivity {

    private ChatActivityBinding binding;

    private ChatConversationViewModel viewModel;

    private static final String WORKMATE_ID = "workmateId";
    private static final String WORKMATE_NAME = "workmateName";

    private static final String WORKMATE_PHOTO_URL = "workmatePhotoUrl";

    public static Intent navigate(
        @NonNull Context context,
        @NonNull String workmateId,
        @NonNull String workmateName,
        @NonNull String workmatePhotoUrl
    ) {
        Intent intent = new Intent(context, ChatConversationActivity.class);
        intent.putExtra(WORKMATE_ID, workmateId);
        intent.putExtra(WORKMATE_NAME, workmateName);
        intent.putExtra(WORKMATE_PHOTO_URL, workmatePhotoUrl);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ChatActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.chatToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this).get(ChatConversationViewModel.class);

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
                            getIntent().getStringExtra(WORKMATE_PHOTO_URL),
                            message
                        );
                        binding.chatMessageInputEt.setText("");
                    }
                }
            }
        );
    }

    private void initRecyclerView() {
        ChatConversationListAdapter adapter = new ChatConversationListAdapter();
        binding.chatRv.setAdapter(adapter);
        binding.chatRv.setLayoutManager(new LinearLayoutManager(this));
        viewModel.getChatMessages(getIntent().getStringExtra(WORKMATE_ID)).observe(this,
            chatMessageViewStateItems -> {
                if (chatMessageViewStateItems != null && !chatMessageViewStateItems.isEmpty()) {
                    adapter.submitList(chatMessageViewStateItems);
                    binding.chatRv.smoothScrollToPosition(chatMessageViewStateItems.size() - 1);
                }
            }
        );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}