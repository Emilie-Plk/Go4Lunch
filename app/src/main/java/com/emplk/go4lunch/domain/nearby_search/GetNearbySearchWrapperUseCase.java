package com.emplk.go4lunch.domain.nearby_search;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchWrapper;
import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;

import javax.inject.Inject;

public class GetNearbySearchWrapperUseCase {

    @NonNull
    private final NearbySearchRepository nearbySearchRepository;

    @NonNull
    private final GetCurrentLocationUseCase getCurrentLocationUseCase;

    @Inject
    public GetNearbySearchWrapperUseCase(
        @NonNull NearbySearchRepository nearbySearchRepository,
        @NonNull GetCurrentLocationUseCase getCurrentLocationUseCase
    ) {
        this.nearbySearchRepository = nearbySearchRepository;
        this.getCurrentLocationUseCase = getCurrentLocationUseCase;
    }

    public LiveData<NearbySearchWrapper> invoke() {
        return Transformations.switchMap(getCurrentLocationUseCase.invoke(), gpsLocationEntity -> {
                return nearbySearchRepository.getNearbyRestaurants(
                    gpsLocationEntity.getLatitude() + "," + gpsLocationEntity.getLongitude(),
                    "restaurant",
                    "distance",
                    API_KEY);
            }
        );
    }
}
