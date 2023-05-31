package com.emplk.go4lunch.ui.restaurant_map;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.ui.restaurant_map.map__marker.MarkerState;
import com.emplk.go4lunch.ui.restaurant_map.map__marker.RestaurantMarkerViewStateItem;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    @NonNull
    private final IsGpsEnabledUseCase isGpsEnabledUseCase;

    @NonNull
    private final GetCurrentLocationUseCase getCurrentLocationUseCase;

    @NonNull
    private final GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase;

    private final MediatorLiveData<List<RestaurantMarkerViewStateItem>> mapViewStateMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public MapViewModel(
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull GetCurrentLocationUseCase getCurrentLocationUseCase,
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase,
        @NonNull Resources resources
    ) {
        this.isGpsEnabledUseCase = isGpsEnabledUseCase;
        this.getCurrentLocationUseCase = getCurrentLocationUseCase;
        this.getNearbySearchWrapperUseCase = getNearbySearchWrapperUseCase;

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();
        LiveData<LocationEntity> locationEntityLiveData = getCurrentLocationUseCase.invoke();
        LiveData<Boolean> isGpsEnabledLiveData = isGpsEnabledUseCase.invoke();

        mapViewStateMediatorLiveData.addSource(isGpsEnabledLiveData, isGpsEnabled -> {
                combine(isGpsEnabled, locationEntityLiveData.getValue(), nearbySearchWrapperLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(locationEntityLiveData, locationEntity -> {
                combine(isGpsEnabledLiveData.getValue(), locationEntity, nearbySearchWrapperLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper -> {
                combine(isGpsEnabledLiveData.getValue(), locationEntityLiveData.getValue(), nearbySearchWrapper
                );
            }
        );
    }

    private void combine(
        @Nullable Boolean isGpsEnabled,
        @Nullable LocationEntity location,
        @Nullable NearbySearchWrapper nearbySearchWrapper
    ) {
        if (isGpsEnabled == null || location == null || nearbySearchWrapper == null) {
            return;
        }

        List<RestaurantMarkerViewStateItem> restaurantMarkerViewStateItems = new ArrayList<>();

        if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
            for (NearbySearchEntity nearbySearchEntity : ((NearbySearchWrapper.Success) nearbySearchWrapper).getNearbySearchEntityList()) {
                restaurantMarkerViewStateItems.add(
                    new RestaurantMarkerViewStateItem(
                        nearbySearchEntity.getPlaceId(),
                        nearbySearchEntity.getRestaurantName(),
                        new LatLng(
                            nearbySearchEntity.getLocationEntity().getLatitude(),
                            nearbySearchEntity.getLocationEntity().getLongitude()
                        ),
                        MarkerState.IS_FAVORITE // TODO: I need to create a new obj for this (fav/not fav by user)
                    )
                );
            }
        }
        mapViewStateMediatorLiveData.setValue(restaurantMarkerViewStateItems);
    }

    public LiveData<List<RestaurantMarkerViewStateItem>> getMapViewState() {
        return mapViewStateMediatorLiveData;
    }

    public LiveData<LocationEntity> getUserCurrentLocation() {
        return getCurrentLocationUseCase.invoke();
    }

}
