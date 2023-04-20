package com.example.go4lunch.ui.restaurant_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.go4lunch.R;

public class RestaurantListFragment extends Fragment {

    @NonNull
    public static RestaurantListFragment newInstance() {
        return new RestaurantListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.restaurant_list_fragment, container, false);
    }
}