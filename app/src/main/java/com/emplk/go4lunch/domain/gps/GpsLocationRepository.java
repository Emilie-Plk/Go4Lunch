package com.emplk.go4lunch.domain.gps;

import androidx.lifecycle.LiveData;

public interface GpsLocationRepository {
    void startLocationRequest();

    void stopLocationRequest();

    LiveData<GpsLocationEntity> getLocationLiveData();

    LiveData<Boolean> isGpsEnabledLiveData();

    LiveData<GpsResponse> getGpsResponseLiveData();

}
