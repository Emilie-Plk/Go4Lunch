package com.emplk.go4lunch.domain.restaurant_list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.NearbySearchRepository;
import com.emplk.go4lunch.ui.restaurant_list.RestaurantListViewState;

import java.util.List;

import javax.inject.Inject;

public class GetNearbyRestaurantListUseCase {
    @NonNull
    private final NearbySearchRepository nearbySearchRepository;

    @NonNull
    private final GetCurrentLocationUseCase getCurrentLocationUseCase; // not sure I need this one

    @NonNull
    private final GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase;

    //TODO: gPs Permission use case

    @Inject
    public GetNearbyRestaurantListUseCase(
        @NonNull NearbySearchRepository nearbySearchRepository,
        @NonNull GetCurrentLocationUseCase getCurrentLocationUseCase,
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase
    ) {
        this.nearbySearchRepository = nearbySearchRepository;
        this.getCurrentLocationUseCase = getCurrentLocationUseCase;
        this.getNearbySearchWrapperUseCase = getNearbySearchWrapperUseCase;
    }

    public LiveData<List<RestaurantListViewState>> invoke() {
        MediatorLiveData<List<RestaurantListViewState>> restaurantListMediatorLiveData = new MediatorLiveData<>();

        return restaurantListMediatorLiveData;
    }
}
