package com.emplk.go4lunch.data.permission;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class GpsPermissionRepository {

    @NonNull
    private final Context context;

    final MutableLiveData<Boolean> permissionLiveData;

    @Inject
    public GpsPermissionRepository(
        @NonNull @ApplicationContext Context context
    ) {
        this.context = context;
        permissionLiveData = new MutableLiveData<>();
    }

    public LiveData<Boolean> hasGpsPermissionLiveData() {
        return Transformations.distinctUntilChanged(permissionLiveData);
    }

    public void refreshGpsPermission() {
        permissionLiveData.setValue(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        );
    }
}
