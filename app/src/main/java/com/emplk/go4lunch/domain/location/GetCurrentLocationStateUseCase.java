package com.emplk.go4lunch.domain.location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.gps.GpsLocationRepository;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;

import javax.inject.Inject;

public class GetCurrentLocationStateUseCase {

    @NonNull
    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public GetCurrentLocationStateUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<LocationStateEntity> invoke() {
        return gpsLocationRepository.getLocationStateLiveData();
    }
}
