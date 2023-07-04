package com.emplk.go4lunch.ui.chat;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.ChatSentMessageBinding;

public class ChatListAdapter extends ListAdapter<ChatMessageViewStateItem, ChatListAdapter.ChatListViewHolder> {

    public ChatListAdapter() {
        super(new ListChatItemCallback());
    }

    @NonNull
    @Override
    public ChatListViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        ChatSentMessageBinding binding = ChatSentMessageBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false
        );
        return new ChatListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(
        @NonNull ChatListViewHolder holder,
        int position
    ) {
       holder.bind(getItem(position));
    }

    public static class ChatListViewHolder extends RecyclerView.ViewHolder {

        private final ChatSentMessageBinding binding;

        public ChatListViewHolder(@NonNull ChatSentMessageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(@NonNull ChatMessageViewStateItem item) {

            boolean isUserMessage = (item.getMessageTypeState() == MessageTypeState.SENDER);

            // Set message alignment based on the message type
            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(binding.getRoot());
            if (isUserMessage) {
                constraintSet.clear(R.id.chat_content_message_tv, ConstraintSet.START);
                constraintSet.connect(R.id.chat_content_message_tv, ConstraintSet.END, R.id.chat_sender_name_tv, ConstraintSet.END);
            } else {
                constraintSet.clear(R.id.chat_content_message_tv, ConstraintSet.END);
                constraintSet.connect(R.id.chat_content_message_tv, ConstraintSet.START, R.id.chat_sender_name_tv, ConstraintSet.START);
            }
            constraintSet.applyTo(binding.getRoot());

            // Bind other data as before
            binding.chatContentMessageTv.setText(item.getMessage());
            binding.chatDateTv.setText(item.getDate());
            if (isUserMessage) {
                binding.chatSenderNameTv.setText(R.string.chat_sender_name);
                binding.chatSenderNameTv.setTextColor(binding.getRoot().getResources().getColor(R.color.light_gray));
            } else {
                binding.chatSenderNameTv.setText(item.getName());
                binding.chatSenderNameTv.setTextColor(binding.getRoot().getResources().getColor(R.color.light_peach));
            }
        }
    }

    private static class ListChatItemCallback extends DiffUtil.ItemCallback<ChatMessageViewStateItem> {
        @Override
        public boolean areItemsTheSame(
            @NonNull ChatMessageViewStateItem oldItem,
            @NonNull ChatMessageViewStateItem newItem
        ) {
            return oldItem.getDate().equals(newItem.getDate());
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
