package com.emplk.go4lunch.ui.chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.emplk.go4lunch.databinding.ChatSentMessageBinding;

public class ChatListAdapter extends ListAdapter<ChatMessageViewStateItem, RecyclerView.ViewHolder> {

    public ChatListAdapter() {
        super(new ListChatItemCallback());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        ChatSentMessageBinding binding = ChatSentMessageBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new RecyclerView.ViewHolder(binding.getRoot()) {
        };
    }

    @Override
    public void onBindViewHolder(
        @NonNull RecyclerView.ViewHolder holder,
        int position
    ) {
        ((ChatListViewHolder) holder).bind(getItem(position));
    }

    public static class ChatListViewHolder extends RecyclerView.ViewHolder {

        private final ChatSentMessageBinding binding;

        public ChatListViewHolder(@NonNull ChatSentMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull ChatMessageViewStateItem item) {
            binding.chatSenderNameTv.setText(item.getName());
            binding.chatContentMessageTv.setText(item.getMessage());
            binding.chatDateTv.setText(item.getDate());
        }
    }

    private static class ListChatItemCallback extends DiffUtil.ItemCallback<ChatMessageViewStateItem> {
        @Override
        public boolean areItemsTheSame(
            @NonNull ChatMessageViewStateItem oldItem,
            @NonNull ChatMessageViewStateItem newItem
        ) {
            return false;
        }

        @Override
        public boolean areContentsTheSame(
            @NonNull ChatMessageViewStateItem oldItem,
            @NonNull ChatMessageViewStateItem newItem
        ) {
            return oldItem.equals(newItem);
        }
    }
}
