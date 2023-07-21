package com.emplk.go4lunch.domain.autocomplete.use_case;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.autocomplete.PredictionEntity;
import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import javax.inject.Inject;

public class GetPredictionsUseCase {

    private static final int RADIUS = 1_000;
    private static final String TYPES = "restaurant";

    @NonNull
    private final PredictionsRepository repository;

    @NonNull
    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase;

    @Inject
    public GetPredictionsUseCase(
        @NonNull PredictionsRepository repository,
        @NonNull GetCurrentLocationStateUseCase getCurrentLocationStateUseCase
    ) {
        this.repository = repository;
        this.getCurrentLocationStateUseCase = getCurrentLocationStateUseCase;
    }

    public LiveData<List<PredictionEntity>> invoke(@NonNull String query) {
        LiveData<LocationStateEntity> locationStateEntityLiveData = getCurrentLocationStateUseCase.invoke();
        return Transformations.switchMap(locationStateEntityLiveData, locationStateEntity -> {
                if (locationStateEntity instanceof LocationStateEntity.Success) {
                    Double latitude = ((LocationStateEntity.Success) locationStateEntity).getLocationEntity().getLatitude();
                    Double longitude = ((LocationStateEntity.Success) locationStateEntity).getLocationEntity().getLongitude();
                    return repository.getPredictionsLiveData(
                        query,
                        latitude,
                        longitude,
                        RADIUS,
                        TYPES
                    );
                } else return new MutableLiveData<>(null);
            }
        );
    }
}
