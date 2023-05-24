package com.emplk.go4lunch.domain.gps;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;

public class GetGpsResponseUseCase {

    @NonNull
    private final GpsLocationRepository gpsLocationRepository;


    @Inject
    public GetGpsResponseUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<GpsResponse> invoke() {
        return gpsLocationRepository.getGpsResponseLiveData();
    }
}
