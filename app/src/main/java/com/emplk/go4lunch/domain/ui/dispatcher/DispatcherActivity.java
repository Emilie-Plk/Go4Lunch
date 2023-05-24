package com.emplk.go4lunch.domain.ui.dispatcher;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.domain.ui.onboarding.OnBoardingActivity;
import com.emplk.go4lunch.domain.ui.login.LoginActivity;
import com.emplk.go4lunch.domain.ui.main.MainActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DispatcherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DispatcherViewModel viewModel = new ViewModelProvider(this).get(DispatcherViewModel.class);

        viewModel.getDispatcherViewActionMediatorLiveData().observe(this, dispatcherViewAction -> {
                switch (dispatcherViewAction) {
                    case GO_TO_ONBOARDING_ACTIVITY:
                        startActivity(new Intent(DispatcherActivity.this, OnBoardingActivity.class));
                        finish();
                        break;
                    case GO_TO_LOGIN_ACTIVITY:
                        startActivity(new Intent(DispatcherActivity.this, LoginActivity.class));
                        finish();
                        break;
                    case GO_TO_MAIN_ACTIVITY:
                        startActivity(new Intent(DispatcherActivity.this, MainActivity.class));
                        finish();
                        break;
                }
            }
        );
    }
}