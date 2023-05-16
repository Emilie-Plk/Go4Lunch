package com.emplk.go4lunch.ui.onboarding;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.databinding.OnboardingActivityBinding;
import com.emplk.go4lunch.ui.login.LoginActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OnBoardingActivity extends AppCompatActivity {

    private OnboardingActivityBinding binding;

    private OnBoardingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OnboardingActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);

        viewModel.getOnBoardingViewAction().observe(this, onBoardingViewAction -> {

                switch (onBoardingViewAction) {
                    case MAIN_WITH_GPS_PERMISSION:
                        startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class)
                        );
                        Toast.makeText(this, "MainActivity with GPS Permission granted", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case MAIN_WITHOUT_GPS_PERMISSION:
                        startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class)
                        );
                        Toast.makeText(this, "MainActivity without GPS Permission granted", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
            }
        );

        binding.onboardingAllowButton.setOnClickListener(v ->
            requestGPSPermission()
        );

        binding.onboardingDeclineButton.setOnClickListener(v ->
            {
                showRequestPermissionRationale();
            }
        );

    }

    private void requestGPSPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        viewModel.setUserPermissionChoice(true);
    }

    private void showRequestPermissionRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            new AlertDialog.Builder(this)
                .setTitle("Permission required")
                .setMessage("This app works best with your permission to GPS location. If you want to provide this permission, please click on 'Change settings' to grant it")
                .setPositiveButton("accept", (dialog, which) -> {
                        requestGPSPermission();
                    }
                )
                .setNegativeButton("cancel", (dialog, which) -> {
                        viewModel.setUserPermissionChoice(false);
                    }
                )
                .create()
                .show();
        }
    }
}