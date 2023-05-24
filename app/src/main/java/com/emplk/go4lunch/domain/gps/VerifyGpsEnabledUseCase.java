package com.emplk.go4lunch.domain.gps;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;

public class VerifyGpsEnabledUseCase {

    @NonNull
    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public VerifyGpsEnabledUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<Boolean> invoke() {
        return gpsLocationRepository.isGpsEnabledLiveData();
    }
}
