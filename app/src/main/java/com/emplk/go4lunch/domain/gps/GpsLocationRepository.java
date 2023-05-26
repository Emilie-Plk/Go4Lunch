package com.emplk.go4lunch.domain.gps;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.data.gps_location.GpsResponse;

public interface GpsLocationRepository {
    void startLocationRequest();

    void stopLocationRequest();

    LiveData<LocationEntity> getLocationLiveData();

    LiveData<Boolean> isGpsEnabledLiveData();

    LiveData<GpsResponse> getGpsResponseLiveData();

}
