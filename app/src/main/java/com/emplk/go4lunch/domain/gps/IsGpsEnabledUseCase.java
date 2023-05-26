package com.emplk.go4lunch.domain.gps;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.data.gps_location.GpsResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IsGpsEnabledUseCase {

    @NonNull
    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public IsGpsEnabledUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<Boolean> invoke() {
        return Transformations.map(gpsLocationRepository.getGpsResponseLiveData(), gpsResponse -> {
                if (gpsResponse instanceof GpsResponse.GpsProviderDisabled) {
                    return false;
                } else {
                    return true;
                }
            }
        );
    }
}
