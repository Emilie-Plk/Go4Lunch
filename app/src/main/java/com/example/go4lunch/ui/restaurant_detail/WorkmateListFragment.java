package com.example.go4lunch.ui.restaurant_detail;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.R;

public class WorkmateListFragment extends Fragment {

    public static WorkmateListFragment newInstance() {
        return new WorkmateListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.workmate_list_fragment, container, false);
    }
}