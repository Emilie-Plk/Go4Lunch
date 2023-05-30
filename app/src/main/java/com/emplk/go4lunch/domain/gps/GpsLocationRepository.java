package com.emplk.go4lunch.domain.gps;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;

public interface GpsLocationRepository {
    void startLocationRequest();

    void stopLocationRequest();

    LiveData<Boolean> isGpsEnabledLiveData();

    LiveData<LocationStateEntity> getLocationStateLiveData();

}
