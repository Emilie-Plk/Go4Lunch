package com.emplk.go4lunch.domain.location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.emplk.go4lunch.domain.gps.GpsLocationRepository;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
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
