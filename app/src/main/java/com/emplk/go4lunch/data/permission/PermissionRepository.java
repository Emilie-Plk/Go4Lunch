package com.emplk.go4lunch.data.permission;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;

// TODO Emilie implement this!
public class PermissionRepository {

    @Inject
    public PermissionRepository() {

    }

    public LiveData<Boolean> getLocationPermission() {
        return new MutableLiveData<>();
    }

    public void refreshPermissions() {

    }
}
