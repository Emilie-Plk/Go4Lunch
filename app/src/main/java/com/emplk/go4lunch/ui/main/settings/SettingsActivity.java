package com.emplk.go4lunch.ui.main.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.databinding.SettingsActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SettingsActivity extends AppCompatActivity {

    private SettingsActivityBinding binding;

    private SettingsViewModel viewModel;

    public static Intent navigate(@NonNull Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SettingsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.settingsToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        viewModel = new ViewModelProvider(this).get(SettingsViewModel.class);

        binding.settingsGpsButton.setOnClickListener(v -> viewModel.startGpsSettingsIntent());

        viewModel.isNotificationEnabled().observe(this, isEnabled -> {
                binding.settingsNotificationSwitch.setChecked(isEnabled);

                binding.settingsNotificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                        viewModel.toggleNotification();
                    }
                );
            }
        );

        viewModel.getStartGpsSettingsIntentSingleLiveEvent().observe(this, aVoid -> {
                startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        );
    }


}