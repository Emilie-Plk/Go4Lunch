package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.ui.restaurant_map.map__marker.MarkerStatusState;
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
    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase;

    @NonNull
    private final GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase;

    private final MediatorLiveData<List<RestaurantMarkerViewStateItem>> mapViewStateMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public MapViewModel(
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull GetCurrentLocationStateUseCase getCurrentLocationStateUseCase,
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase
    ) {
        this.isGpsEnabledUseCase = isGpsEnabledUseCase;
        this.getCurrentLocationStateUseCase = getCurrentLocationStateUseCase;
        this.getNearbySearchWrapperUseCase = getNearbySearchWrapperUseCase;

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();
        LiveData<LocationStateEntity> locationStateEntityLiveData = getCurrentLocationStateUseCase.invoke();
        LiveData<Boolean> isGpsEnabledLiveData = isGpsEnabledUseCase.invoke();

        mapViewStateMediatorLiveData.addSource(isGpsEnabledLiveData, isGpsEnabled -> {
                combine(isGpsEnabled, locationStateEntityLiveData.getValue(), nearbySearchWrapperLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(locationStateEntityLiveData, locationEntity -> {
                combine(isGpsEnabledLiveData.getValue(), locationEntity, nearbySearchWrapperLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper -> {
                combine(isGpsEnabledLiveData.getValue(), locationStateEntityLiveData.getValue(), nearbySearchWrapper
                );
            }
        );
    }

    private void combine(
        @Nullable Boolean isGpsEnabled,
        @Nullable LocationStateEntity locationStateEntity,
        @Nullable NearbySearchWrapper nearbySearchWrapper
    ) {
        if (isGpsEnabled == null || nearbySearchWrapper == null) {
            return;
        }

        List<RestaurantMarkerViewStateItem> restaurantMarkerViewStateItems = new ArrayList<>();
        if (locationStateEntity instanceof LocationStateEntity.Success) {
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
                            MarkerStatusState.IS_FAVORITE // TODO: I need to create a new obj for this (fav/not fav by user)
                        )
                    );
                    mapViewStateMediatorLiveData.setValue(restaurantMarkerViewStateItems);
                }
            } else if (nearbySearchWrapper instanceof NearbySearchWrapper.Loading) {
                return;
            }
        } else if (locationStateEntity instanceof LocationStateEntity.GpsProviderDisabled) {
            //maybe a SingleLiveEvent for toast, see what's happening in repo
            return;
        }

    }

    public LiveData<List<RestaurantMarkerViewStateItem>> getMapViewState() {
        return mapViewStateMediatorLiveData;
    }

    public LiveData<LocationStateEntity> getLocationState() {
        return getCurrentLocationStateUseCase.invoke();
    }

}
