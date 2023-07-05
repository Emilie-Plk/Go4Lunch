package com.emplk.go4lunch.ui.chat.list;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emplk.go4lunch.databinding.ChatLastMessageItemBinding;

public class ChatLastMessageListAdapter extends ListAdapter<ChatLastMessageViewStateItem, RecyclerView.ViewHolder> {

    @NonNull
    private final OnLastMessageClickListener listener;

    public ChatLastMessageListAdapter(@NonNull OnLastMessageClickListener listener) {
        super(new ListChatItemCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ChatLastMessageItemBinding binding = ChatLastMessageItemBinding.inflate(
            inflater,
            parent,
            false
        );
        return new ChatLastMessageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
        @NonNull RecyclerView.ViewHolder holder,
        int position
    ) {
        ChatLastMessageViewStateItem item = getItem(position);
        ((ChatLastMessageViewHolder) holder).bind(item, listener);
    }

    private static class ChatLastMessageViewHolder extends RecyclerView.ViewHolder {

        private final ChatLastMessageItemBinding binding;
        public ChatLastMessageViewHolder(ChatLastMessageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull ChatLastMessageViewStateItem item, @NonNull OnLastMessageClickListener listener) {

            binding.getRoot().setOnClickListener(v -> listener.onLastMessageClicked(item.getUserId(), item.getPhotoUrl()));
            Glide.with(binding.getRoot())
                .load(item.getPhotoUrl())
                .circleCrop()
                .into(binding.chatWorkmateAvatar);

            binding.chatWorkmateNameTv.setText(item.getName());
            binding.chatWorkmateLastMessageTv.setText(item.getLastMessage());
            binding.chatWorkmateTimestampTv.setText(item.getDate());
        }
    }

    public static class ListChatItemCallback extends DiffUtil.ItemCallback<ChatLastMessageViewStateItem> {
        @Override
        public boolean areItemsTheSame(
            @NonNull ChatLastMessageViewStateItem oldItem,
            @NonNull ChatLastMessageViewStateItem newItem
        ) {
            return oldItem.getDate().equals(newItem.getDate());
        }

        @Override
        public boolean areContentsTheSame(
            @NonNull ChatLastMessageViewStateItem oldItem,
            @NonNull ChatLastMessageViewStateItem newItem
        ) {
            return oldItem.equals(newItem);
        }
    }
}
