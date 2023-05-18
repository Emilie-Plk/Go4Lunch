package com.emplk.go4lunch.ui.onboarding;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

    private ActivityResultLauncher<String> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = OnboardingActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);

        permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), result ->
            {
                if (Boolean.TRUE.equals(result)) {
                    startMainActivityWithPermissions();
                } else {
                    startMainActivityWithoutPermissions();
                }
            }
        );


        viewModel.isShowRationale().observe(this, isShowRationale -> {
                if (Boolean.TRUE.equals(!isShowRationale)) {
                    showRequestPermissionRationale();
                }
            }
        );

        binding.onboardingAllowButton.setOnClickListener(v -> {
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        );

        binding.onboardingDeclineButton.setOnClickListener(v -> {
                startMainActivityWithoutPermissions();
            }
        );
    }


    private void showRequestPermissionRationale() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        ) {
            new AlertDialog.Builder(this)
                .setTitle("Permission required")
                .setMessage("This app works best with your permission to GPS location. If you want to provide this permission, please click on 'Change settings' to grant it")
                .setPositiveButton("accept", (dialog, which) -> {
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                )
                .setNegativeButton("cancel", (dialog, which) -> {
                        startMainActivityWithoutPermissions();
                    }
                )
                .create()
                .show();
        }
    }

    private void startMainActivityWithPermissions() {
        startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class)
        );
        Toast.makeText(this, "MainActivity with GPS Permission granted", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void startMainActivityWithoutPermissions() {
        startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class)
        );
        Toast.makeText(this, "MainActivity without GPS Permission granted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
