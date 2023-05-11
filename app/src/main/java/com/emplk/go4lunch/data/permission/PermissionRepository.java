package com.emplk.go4lunch.data.permission;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class PermissionRepository {

    private final Context context;

    final MutableLiveData<Boolean> permissionLiveData;

    @Inject
    public PermissionRepository(
        @NonNull @ApplicationContext Context context) {
        this.context = context;
        permissionLiveData = new MutableLiveData<>();
    }

    public LiveData<Boolean> getLocationPermission() {
        return permissionLiveData;
    }

    public void refreshPermission() {
        if (ContextCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionLiveData.setValue(false);
        } else {
            permissionLiveData.setValue(true);
        }

    }
}
