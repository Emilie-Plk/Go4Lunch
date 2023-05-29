package com.emplk.go4lunch.domain.gps;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.gps.entity.GpsResponse;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;

public interface GpsLocationRepository {
    void startLocationRequest();

    void stopLocationRequest();

    LiveData<LocationEntity> getLocationLiveData();

    LiveData<Boolean> isGpsEnabledLiveData();

    LiveData<GpsResponse> getGpsResponseLiveData();

}
