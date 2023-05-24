package com.emplk.go4lunch.domain.location;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.gps.GpsLocationRepository;

import javax.inject.Inject;

public class StartLocationRequestUseCase {

    @NonNull
    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public StartLocationRequestUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public void invoke() {
        gpsLocationRepository.startLocationRequest();
    }
}
