package com.emplk.go4lunch.ui.workmate_list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.emplk.go4lunch.R;

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