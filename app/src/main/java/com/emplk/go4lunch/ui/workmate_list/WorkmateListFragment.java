package com.emplk.go4lunch.ui.workmate_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emplk.go4lunch.databinding.WorkmateListFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkmateListFragment extends Fragment {

    private WorkmateListFragmentBinding binding;

    private WorkmatesViewModel viewModel;

    public static WorkmateListFragment newInstance() {
        return new WorkmateListFragment();
    }

    @Override
    public View onCreateView(
        @NonNull LayoutInflater inflater,
        ViewGroup container,
        Bundle savedInstanceState
    ) {
        binding = WorkmateListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(
        @NonNull View view,
        @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(WorkmatesViewModel.class);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.getRoot();

        WorkmateListAdapter adapter = new WorkmateListAdapter(new OnStartChatWithWorkmateListener() {
            @Override
            public void onStartChatWithWorker(@NonNull String workmateId) {
// TODO: start chat
            }
        }
        );

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getWorkmates().observe(getViewLifecycleOwner(), workmates -> {
          adapter.submitList(workmates);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}