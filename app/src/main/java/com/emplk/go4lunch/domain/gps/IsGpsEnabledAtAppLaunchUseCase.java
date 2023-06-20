package com.emplk.go4lunch.domain.gps;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;

public class IsGpsEnabledAtAppLaunchUseCase {

    @NonNull
    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public IsGpsEnabledAtAppLaunchUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<Boolean> invoke() {
        return gpsLocationRepository.isGpsProviderEnabled();
    }
}
