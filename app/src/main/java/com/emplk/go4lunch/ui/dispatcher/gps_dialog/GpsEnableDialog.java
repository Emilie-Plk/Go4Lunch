package com.emplk.go4lunch.ui.dispatcher.gps_dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.emplk.go4lunch.ui.dispatcher.DispatcherActivity;

public class GpsEnableDialog {
    @NonNull
    private final Context context;

    public GpsEnableDialog(@NonNull Context context) {
        this.context = context;
    }

    public void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Enable GPS")
            .setMessage("Please enable your GPS")
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(
                    DialogInterface dialog,
                    int which
                ) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    context.startActivity(intent);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(
                    DialogInterface dialog,
                    int which
                ) {
                    dialog.dismiss();
                }
            })
            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    Intent intent = new Intent(context, DispatcherActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            })
            .create();
    }
}

