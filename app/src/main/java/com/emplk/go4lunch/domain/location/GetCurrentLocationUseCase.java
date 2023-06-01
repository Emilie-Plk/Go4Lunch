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
public class GetCurrentLocationUseCase {


    @NonNull
    private final GpsLocationRepository gpsLocationRepository;

    @Inject
    public GetCurrentLocationUseCase(@NonNull GpsLocationRepository gpsLocationRepository) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    public LiveData<LocationEntity> invoke() {
        MediatorLiveData<LocationEntity> mediatorLiveData = new MediatorLiveData<>();

        mediatorLiveData.addSource(gpsLocationRepository.getLocationStateLiveData(), new Observer<LocationStateEntity>() {
                @Override
                public void onChanged(LocationStateEntity locationStateEntity) {
                    if (locationStateEntity instanceof LocationStateEntity.Success) {
                        mediatorLiveData.setValue(((LocationStateEntity.Success) locationStateEntity).locationEntity);
                    }
                }
            }
        );

        return mediatorLiveData;
    }
}
