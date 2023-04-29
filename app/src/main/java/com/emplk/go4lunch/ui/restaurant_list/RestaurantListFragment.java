package com.emplk.go4lunch.ui.restaurant_list;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emplk.go4lunch.databinding.RestaurantListFragmentBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RestaurantListFragment extends Fragment {

    private RestaurantListFragmentBinding binding;

    private RestaurantListViewModel viewModel;

    @NonNull
    public static RestaurantListFragment newInstance() {
        return new RestaurantListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = RestaurantListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpViewModel();
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.getRoot();

        RestaurantListAdapter adapter = new RestaurantListAdapter(new OnRestaurantClickedListener() {
            @Override
            public void onRestaurantClicked(String id) {
                Log.i(TAG, "Restaurant clicked! Id is:" + id);
            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        viewModel.getRestaurantListItemViewState()
            .observe(getViewLifecycleOwner(),
                adapter::submitList);
    }

    private void setUpViewModel() {
        viewModel = new ViewModelProvider(this).get(RestaurantListViewModel.class);
    }
}