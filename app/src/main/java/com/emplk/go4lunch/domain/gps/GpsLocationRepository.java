package com.emplk.go4lunch.domain.gps;

import android.location.Location;

import androidx.lifecycle.LiveData;

public interface GpsLocationRepository {
    void startLocationRequest();

    void stopLocationRequest();

    LiveData<Location> getLocationLiveData();

    LiveData<Boolean> isGpsEnabled();
}
