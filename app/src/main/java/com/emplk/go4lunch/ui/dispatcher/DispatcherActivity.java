package com.emplk.go4lunch.ui.dispatcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.ui.login.LoginActivity;
import com.emplk.go4lunch.ui.onboarding.OnBoardingActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DispatcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DispatcherViewModel viewModel = new ViewModelProvider(this).get(DispatcherViewModel.class);

        viewModel.isPermissionGranted().observe(this, isPermissionGranted -> { // I'd like to also check if user is logged in or not
                if (Boolean.TRUE.equals(isPermissionGranted)) {
                    startActivity(new Intent(DispatcherActivity.this, LoginActivity.class)
                    );
                } else {
                    startActivity(new Intent(DispatcherActivity.this, OnBoardingActivity.class)
                    );
                }
            }
        );
    }
}