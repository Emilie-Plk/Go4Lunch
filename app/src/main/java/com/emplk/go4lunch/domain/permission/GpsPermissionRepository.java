package com.emplk.go4lunch.domain.permission;

import androidx.lifecycle.LiveData;

public interface GpsPermissionRepository {

    LiveData<Boolean> hasGpsPermissionLiveData();

    void refreshGpsPermission();
}
