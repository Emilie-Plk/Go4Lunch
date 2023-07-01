package com.emplk.go4lunch.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.emplk.go4lunch.databinding.SettingsActivityBinding;

public class SettingsActivity extends AppCompatActivity {

    private SettingsActivityBinding binding;

    public static Intent navigate(@NonNull Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SettingsActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}