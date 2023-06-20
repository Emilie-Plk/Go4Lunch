package com.emplk.go4lunch.domain.gps;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

public class IsGpsEnabledUseCase {

    @NonNull
    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public IsGpsEnabledUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<Boolean> invoke() {
        return Transformations.switchMap(gpsLocationRepository.getLocationStateLiveData(), gpsResponse -> {
                MutableLiveData<Boolean> isGpsEnabledLiveData = new MutableLiveData<>();
                if (gpsResponse instanceof LocationStateEntity.GpsProviderDisabled) {
                    isGpsEnabledLiveData.setValue(false);
                } else if (gpsResponse instanceof LocationStateEntity.Success) {
                    isGpsEnabledLiveData.setValue(true);
                }
                return isGpsEnabledLiveData;
            }
        );
    }
}