package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.autocomplete.use_case.GetPredictionPlaceIdUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.domain.workmate.GetAttendantsGoingToSameRestaurantAsUserUseCase;
import com.emplk.go4lunch.ui.restaurant_map.map__marker.RestaurantMarkerViewStateItem;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    @NonNull
    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase;

    @NonNull
    private final SingleLiveEvent<Void> noRestaurantMatchSingleLiveEvent = new SingleLiveEvent<>();

    @NonNull
    private final SingleLiveEvent<Void> noRestaurantFoundSingleLiveEvent = new SingleLiveEvent<>();

    @NonNull
    private final MediatorLiveData<List<RestaurantMarkerViewStateItem>> mapViewStateMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public MapViewModel(
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull GetCurrentLocationStateUseCase getCurrentLocationStateUseCase,
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase,
        @NonNull GetAttendantsGoingToSameRestaurantAsUserUseCase getAttendantsGoingToSameRestaurantAsUserUseCase,
        @NonNull GetPredictionPlaceIdUseCase getPredictionPlaceIdUseCase
    ) {
        this.getCurrentLocationStateUseCase = getCurrentLocationStateUseCase;

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();
        LiveData<LocationStateEntity> locationStateEntityLiveData = getCurrentLocationStateUseCase.invoke();
        LiveData<Boolean> isGpsEnabledLiveData = isGpsEnabledUseCase.invoke();
        LiveData<Map<String, Integer>> restaurantIdWithAttendantsMapLiveData = getAttendantsGoingToSameRestaurantAsUserUseCase.invoke();
        LiveData<String> placeIdLiveData = getPredictionPlaceIdUseCase.invoke();

        mapViewStateMediatorLiveData.addSource(isGpsEnabledLiveData, isGpsEnabled -> {
                combine(
                    isGpsEnabled,
                    locationStateEntityLiveData.getValue(),
                    nearbySearchWrapperLiveData.getValue(),
                    restaurantIdWithAttendantsMapLiveData.getValue(),
                    placeIdLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(locationStateEntityLiveData, locationEntity -> {
                combine(
                    isGpsEnabledLiveData.getValue(),
                    locationEntity,
                    nearbySearchWrapperLiveData.getValue(),
                    restaurantIdWithAttendantsMapLiveData.getValue(),
                    placeIdLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper -> {
                combine(
                    isGpsEnabledLiveData.getValue(),
                    locationStateEntityLiveData.getValue(),
                    nearbySearchWrapper,
                    restaurantIdWithAttendantsMapLiveData.getValue(),
                    placeIdLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(restaurantIdWithAttendantsMapLiveData, restaurantIdToAttendantsCount -> {
                combine(
                    isGpsEnabledLiveData.getValue(),
                    locationStateEntityLiveData.getValue(),
                    nearbySearchWrapperLiveData.getValue(),
                    restaurantIdToAttendantsCount,
                    placeIdLiveData.getValue()
                );
            }
        );

        mapViewStateMediatorLiveData.addSource(placeIdLiveData, placeId -> {
                combine(
                    isGpsEnabledLiveData.getValue(),
                    locationStateEntityLiveData.getValue(),
                    nearbySearchWrapperLiveData.getValue(),
                    restaurantIdWithAttendantsMapLiveData.getValue(),
                    placeId
                );
            }
        );
    }

    private void combine(
        @Nullable Boolean isGpsEnabled,
        @Nullable LocationStateEntity locationStateEntity,
        @Nullable NearbySearchWrapper nearbySearchWrapper,
        @Nullable Map<String, Integer> restaurantIdToAttendantsCount,
        @Nullable String placeId
    ) {
        if (isGpsEnabled == null || nearbySearchWrapper == null || nearbySearchWrapper instanceof NearbySearchWrapper.Loading || locationStateEntity == null) {
            return;
        }

        List<RestaurantMarkerViewStateItem> restaurantMarkerViewStateItems = new ArrayList<>();
        if (locationStateEntity instanceof LocationStateEntity.Success) {
            if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
                for (NearbySearchEntity nearbySearchEntity : ((NearbySearchWrapper.Success) nearbySearchWrapper).getNearbySearchEntityList()) {
                    if (placeId == null) {
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
                    } else if (nearbySearchEntity.getPlaceId().equals(placeId)) {
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
                        break;
                    }
                }
            } else if (nearbySearchWrapper instanceof NearbySearchWrapper.NoResults) {
                noRestaurantFoundSingleLiveEvent.call();
            }
            if (restaurantMarkerViewStateItems.isEmpty() && nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
                noRestaurantMatchSingleLiveEvent.call();
            }
            mapViewStateMediatorLiveData.setValue(restaurantMarkerViewStateItems);
        }
    }

    @NonNull
    public SingleLiveEvent<Void> getNoRestaurantMatchSingleLiveEvent() {
        return noRestaurantMatchSingleLiveEvent;
    }

    @NonNull
    public SingleLiveEvent<Void> getNoRestaurantFoundSingleLiveEvent() {
        return noRestaurantFoundSingleLiveEvent;
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
