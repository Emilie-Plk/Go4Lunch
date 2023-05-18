package com.emplk.go4lunch.data.permission;

import android.Manifest;
import android.app.Application;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GPSPermissionRepository {

    @NonNull
    private final Application application;

    final MutableLiveData<Boolean> permissionLiveData;

    @Inject
    public GPSPermissionRepository(
        @NonNull Application application
    ) {
        this.application = application;
        permissionLiveData = new MutableLiveData<>();
    }

    public LiveData<Boolean> hasGPSPermission() {
        return permissionLiveData;
    }

    public void refreshGPSPermission() {
        permissionLiveData.setValue(
            ContextCompat.checkSelfPermission(
                application.getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        );
    }
}
