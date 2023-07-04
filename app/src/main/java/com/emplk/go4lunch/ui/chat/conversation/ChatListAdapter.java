package com.emplk.go4lunch.ui.chat.conversation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.ChatRecipientMessageItemBinding;
import com.emplk.go4lunch.databinding.ChatSenderMessageItemBinding;

public class ChatListAdapter extends ListAdapter<ChatMessageViewStateItem, RecyclerView.ViewHolder> {

    private static final int VIEW_TYPE_SENDER = 1;
    private static final int VIEW_TYPE_RECIPIENT = 2;

    public ChatListAdapter() {
        super(new ListChatItemCallback());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent,
        int viewType
    ) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_SENDER) {
            ChatSenderMessageItemBinding sentMessageBinding = ChatSenderMessageItemBinding.inflate(inflater, parent, false);
            return new SenderViewHolder(sentMessageBinding);
        } else if (viewType == VIEW_TYPE_RECIPIENT) {
            ChatRecipientMessageItemBinding receivedMessageBinding = ChatRecipientMessageItemBinding.inflate(inflater, parent, false);
            return new RecipientViewHolder(receivedMessageBinding);
        }
        throw new IllegalArgumentException("Invalid view type: " + viewType);
    }

    @Override
    public void onBindViewHolder(
        @NonNull RecyclerView.ViewHolder holder,
        int position
    ) {
        ChatMessageViewStateItem item = getItem(position);
        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).bind(item);
        } else if (holder instanceof RecipientViewHolder) {
            ((RecipientViewHolder) holder).bind(item);
        }
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessageViewStateItem item = getItem(position);
        return (item.getMessageTypeState() == MessageTypeState.SENDER) ? VIEW_TYPE_SENDER : VIEW_TYPE_RECIPIENT;
    }

    private static class SenderViewHolder extends RecyclerView.ViewHolder {
        private final ChatSenderMessageItemBinding binding;

        public SenderViewHolder(@NonNull ChatSenderMessageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatMessageViewStateItem item) {
            binding.chatSenderNameTv.setText(R.string.chat_sender_name);
            binding.chatContentMessageTv.setText(item.getMessage());
            binding.chatDateTv.setText(item.getDate());
        }
    }

    private static class RecipientViewHolder extends RecyclerView.ViewHolder {
        private final ChatRecipientMessageItemBinding binding;

        public RecipientViewHolder(@NonNull ChatRecipientMessageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatMessageViewStateItem item) {
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
