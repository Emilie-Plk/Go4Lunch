package com.emplk.go4lunch.ui.dispatcher;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
                    case DISPLAY_GPS_DIALOG:
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("Enable GPS")
                            .setMessage("Please enable your GPS")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which
                                    ) {
                                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                        startActivity(intent);
                                    }
                                }
                            )
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                        DialogInterface dialog,
                                        int which
                                    ) {
                                        dialog.dismiss();
                                    }
                                }
                            )
                            .create()
                            .show();
                        break;
                }
            }
        );
    }
}
