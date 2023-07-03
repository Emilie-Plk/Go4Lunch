package com.emplk.go4lunch.domain.nearby_search;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;

import javax.inject.Inject;

public class GetNearbySearchWrapperUseCase {

    private static final int RADIUS = 1_000;
    @NonNull
    private final NearbySearchRepository nearbySearchRepository;

    @NonNull
    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase;

    @Inject
    public GetNearbySearchWrapperUseCase(
        @NonNull NearbySearchRepository nearbySearchRepository,
        @NonNull GetCurrentLocationStateUseCase getCurrentLocationStateUseCase
    ) {
        this.nearbySearchRepository = nearbySearchRepository;
        this.getCurrentLocationStateUseCase = getCurrentLocationStateUseCase;
    }


    public LiveData<NearbySearchWrapper> invoke() {
        LiveData<LocationStateEntity> locationStateLiveData = getCurrentLocationStateUseCase.invoke();
        return Transformations.switchMap(locationStateLiveData, locationState -> {
                if (locationState instanceof LocationStateEntity.Success) {
                    LocationEntity location = ((LocationStateEntity.Success) locationState).locationEntity;
                    return nearbySearchRepository.getNearbyRestaurants(
                        location.getLatitude() + "," + location.getLongitude(),
                        "restaurant",
                        RADIUS,
                        API_KEY
                    );
                } else if (locationState instanceof LocationStateEntity.GpsProviderDisabled) {
                    return new MutableLiveData<>(
                        new NearbySearchWrapper.RequestError(
                            new Exception("GpsProviderDisabled")
                        )
                    );
                } else return null;
            }
        );
    }
}