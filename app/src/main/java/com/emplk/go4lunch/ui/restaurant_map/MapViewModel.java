package com.emplk.go4lunch.ui.restaurant_map;

import android.content.Context;
import android.util.Log;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.domain.workmate.GetAttendantsGoingToSameRestaurantAsUserUseCase;
import com.emplk.go4lunch.ui.restaurant_map.map__marker.RestaurantMarkerViewStateItem;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class MapViewModel extends ViewModel {

    @NonNull
    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase;

    private final MediatorLiveData<List<RestaurantMarkerViewStateItem>> mapViewStateMediatorLiveData = new MediatorLiveData<>();
    @NonNull
    private final Context context;

    @Inject
    public MapViewModel(
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull GetCurrentLocationStateUseCase getCurrentLocationStateUseCase,
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase,
        @NonNull GetAttendantsGoingToSameRestaurantAsUserUseCase getAttendantsGoingToSameRestaurantAsUserUseCase,
        @NonNull @ApplicationContext Context context
    ) {
        this.getCurrentLocationStateUseCase = getCurrentLocationStateUseCase;
        this.context = context;

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();
        LiveData<LocationStateEntity> locationStateEntityLiveData = getCurrentLocationStateUseCase.invoke();
        LiveData<Boolean> isGpsEnabledLiveData = isGpsEnabledUseCase.invoke();
        LiveData<Map<String, Integer>> restaurantIdToAttendantsCountLiveData = getAttendantsGoingToSameRestaurantAsUserUseCase.invoke();

        mapViewStateMediatorLiveData.addSource(isGpsEnabledLiveData, isGpsEnabled -> {
                combine(isGpsEnabled, locationStateEntityLiveData.getValue(), nearbySearchWrapperLiveData.getValue(), restaurantIdToAttendantsCountLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(locationStateEntityLiveData, locationEntity -> {
                combine(isGpsEnabledLiveData.getValue(), locationEntity, nearbySearchWrapperLiveData.getValue(), restaurantIdToAttendantsCountLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper -> {
                combine(isGpsEnabledLiveData.getValue(), locationStateEntityLiveData.getValue(), nearbySearchWrapper, restaurantIdToAttendantsCountLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(restaurantIdToAttendantsCountLiveData, restaurantIdToAttendantsCount -> {
                combine(isGpsEnabledLiveData.getValue(), locationStateEntityLiveData.getValue(), nearbySearchWrapperLiveData.getValue(), restaurantIdToAttendantsCount
                );
            }
        );
    }

    private void combine(
        @Nullable Boolean isGpsEnabled,
        @Nullable LocationStateEntity locationStateEntity,
        @Nullable NearbySearchWrapper nearbySearchWrapper,
        @Nullable Map<String, Integer> restaurantIdToAttendantsCount
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
                            isRestaurantAttended(restaurantIdToAttendantsCount, nearbySearchEntity.getPlaceId())
                        )
                    );
                    mapViewStateMediatorLiveData.setValue(restaurantMarkerViewStateItems);
                }
            } else if (nearbySearchWrapper instanceof NearbySearchWrapper.Loading) {
                Log.d("MapViewModel", "Loading nearby search");
            }
        }
    }

    @ColorRes
    private int isRestaurantAttended(
        @Nullable Map<String, Integer> restaurantIdToAttendantsCount,
        @NonNull String placeId
    ) {
        if (restaurantIdToAttendantsCount != null && restaurantIdToAttendantsCount.containsKey(placeId)) {
            return R.color.ok_green;
        } else {
            return R.color.ripe_peach;
        }
    }

    public LiveData<List<RestaurantMarkerViewStateItem>> getMapViewState() {
        return mapViewStateMediatorLiveData;
    }

    public LiveData<LocationStateEntity> getLocationState() {
        return getCurrentLocationStateUseCase.invoke();
    }
}
