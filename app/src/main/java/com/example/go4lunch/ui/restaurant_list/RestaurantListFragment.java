package com.example.go4lunch.ui.restaurant_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.go4lunch.databinding.RestaurantListFragmentBinding;

public class RestaurantListFragment extends Fragment {

    private RestaurantListFragmentBinding binding;

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
//        setUpViewModel();
//        initRecyclerView();
    }
}