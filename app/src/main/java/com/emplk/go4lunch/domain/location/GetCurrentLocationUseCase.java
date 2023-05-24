package com.emplk.go4lunch.domain.location;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.gps.GpsLocationEntity;
import com.emplk.go4lunch.domain.gps.GpsLocationRepository;

import javax.inject.Inject;

public class GetCurrentLocationUseCase {

    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public GetCurrentLocationUseCase(GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<GpsLocationEntity> invoke() {
       return gpsLocationRepository.getLocationLiveData();
    }
}
