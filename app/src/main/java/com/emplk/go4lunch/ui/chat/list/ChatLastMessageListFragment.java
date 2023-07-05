package com.emplk.go4lunch.ui.chat.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.emplk.go4lunch.databinding.ChatListFragmentBinding;
import com.emplk.go4lunch.ui.chat.conversation.ChatConversationActivity;
import com.emplk.go4lunch.ui.chat.conversation.ChatConversationListAdapter;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatLastMessageListFragment extends Fragment {

    private ChatListFragmentBinding binding;

    private ChatLastMessageListViewModel viewModel;


    public static ChatLastMessageListFragment newInstance() {
        return new ChatLastMessageListFragment();
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = ChatListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(
        @NonNull View view,
        @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(ChatLastMessageListViewModel.class);
        initRecyclerView();
    }

    private void initRecyclerView() {
        ChatLastMessageListAdapter adapter = new ChatLastMessageListAdapter(new OnLastMessageClickListener() {
            @Override
            public void onLastMessageClicked(
                @NonNull String workmateId,
                @NonNull String workmatePictureUrl
            ) {
                startActivity(ChatConversationActivity.navigate(requireContext(), workmateId, workmatePictureUrl));
            }
        });
        RecyclerView recyclerView = binding.getRoot();
        recyclerView.setAdapter(adapter);
        viewModel.getChatLastMessageViewStateItems().observe(getViewLifecycleOwner(), adapter::submitList);
    }
}