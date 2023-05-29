package com.emplk.go4lunch.domain.location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.gps.entity.GpsResponse;
import com.emplk.go4lunch.domain.gps.GpsLocationRepository;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetCurrentLocationUseCase {


    @NonNull
    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public GetCurrentLocationUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<LocationEntity> invoke() {
        return Transformations.map(gpsLocationRepository.getGpsResponseLiveData(), gpsResponse -> {
            if (gpsResponse instanceof GpsResponse.Success) {
                return ((GpsResponse.Success) gpsResponse).locationEntity;
            }
            return null; // TODO: I need to manage the GpsProviderDisabled case
        });
    }
}
