package com.emplk.go4lunch.domain.GPS_location;

public interface GPSLocationRepository {
    void startLocationRequest();

    public void stopLocationRequest();
}
