package com.emplk.go4lunch.ui.dispatcher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.ui.login.LoginActivity;
import com.emplk.go4lunch.ui.main.MainActivity;
import com.emplk.go4lunch.ui.onboarding.OnBoardingActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DispatcherActivity extends AppCompatActivity {

    public static Intent navigate(@NonNull Context context) {
        return new Intent(context, DispatcherActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DispatcherViewModel viewModel = new ViewModelProvider(this).get(DispatcherViewModel.class);

        viewModel.getDispatcherViewAction().observe(this, dispatcherViewAction -> {
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
