package com.emplk.go4lunch.ui.onboarding;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.OnboardingActivityBinding;
import com.emplk.go4lunch.ui.dispatcher.DispatcherActivity;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OnBoardingActivity extends AppCompatActivity {

    private OnBoardingViewModel viewModel;

    private ActivityResultLauncher<String[]> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnboardingActivityBinding binding = OnboardingActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(OnBoardingViewModel.class);

        permissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                viewModel.onPermissionResult();
            }
        );

        viewModel.getOnBoardingViewAction().observe(this, viewAction -> {
                switch (viewAction) {
                    case CONTINUE_TO_AUTHENTICATION:
                        continueWithPermissions();
                        break;
                    case ASK_Gps_PERMISSION:
                        permissionLauncher.launch(new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            }
                        );
                        break;
                    case SHOW_RATIONALE:
                        showRequestPermissionRationale();
                        break;
                    case GO_APP_SETTINGS:
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        break;
                }
            }
        );

        binding.onboardingAllowButton.setOnClickListener(v -> {
                viewModel.onAllowClicked(
                    ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)
                );
            }
        );
    }

    private void showRequestPermissionRationale() {
        new AlertDialog.Builder(this)
            .setTitle("Permission required")
            .setMessage(R.string.showRationaleGpsPermissionMessage)
            .setPositiveButton(R.string.change_settings_dialog_button, (dialog, which) -> {
                    viewModel.onChangeAppSettingsClicked();
                }
            )
            .setCancelable(false)
            .create()
            .show();
    }

    private void continueWithPermissions() {
        startActivity(new Intent(OnBoardingActivity.this, DispatcherActivity.class));
        Toast.makeText(this, "Continuing with Gps Permission granted", Toast.LENGTH_SHORT).show();
        finish();
    }
}
