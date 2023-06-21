package com.emplk.go4lunch.domain.gps;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;

public interface GpsLocationRepository {

    LiveData<LocationStateEntity> getLocationStateLiveData();

    void startLocationRequest();

    void stopLocationRequest();
}
