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
import com.emplk.go4lunch.ui.dispatcher.DispatcherActivity;

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
            new ActivityResultContracts.RequestPermission(), result -> {
                viewModel.onPermissionResult();
            }
        );

        viewModel.getOnBoardingViewAction().observe(this, viewAction -> {
                switch (viewAction) {
                    case CONTINUE:
                        continueOnboarding();
                        break;
                    case ASK_GPS_PERMISSION:
                        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                        break;
                    case SHOW_RATIONALE:
                        showRequestPermissionRationale();
                        break;
                }
            }
        );

        binding.onboardingAllowButton.setOnClickListener(v -> viewModel.onAllowClicked(
            ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
        ));
    }

    private void showRequestPermissionRationale() {
        new AlertDialog.Builder(this)
            .setTitle("Permission required")
            .setMessage("This app works best with your permission to GPS location. If you want to provide this permission, please click on 'Change settings' to grant it")
            .setPositiveButton("accept", (dialog, which) -> {
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                }
            )
            .setCancelable(false)
            .create()
            .show();


//        if (
//            ActivityCompat.shouldShowRequestPermissionRationale(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            )
//        ) {
//
//        }
    }

    private void continueOnboarding() {
        startActivity(new Intent(OnBoardingActivity.this, DispatcherActivity.class));
        Toast.makeText(this, "Continuing with GPS Permission granted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
