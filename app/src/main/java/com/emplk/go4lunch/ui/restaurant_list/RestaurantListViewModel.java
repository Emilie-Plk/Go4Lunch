package com.emplk.go4lunch.ui.restaurant_list;


import static com.emplk.go4lunch.BuildConfig.API_KEY;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.NO_GPS_FOUND;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.NO_RESULT_FOUND;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.REQUEST_FAILURE;
import static com.emplk.go4lunch.ui.restaurant_list.RestaurantOpeningState.IS_CLOSED;
import static com.emplk.go4lunch.ui.restaurant_list.RestaurantOpeningState.IS_NOT_DEFINED;
import static com.emplk.go4lunch.ui.restaurant_list.RestaurantOpeningState.IS_OPEN;

import android.content.res.Resources;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.autocomplete.use_case.GetPredictionPlaceIdUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;
import com.emplk.go4lunch.domain.workmate.GetAttendantsGoingToSameRestaurantAsUserUseCase;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantListViewModel extends ViewModel {

    @NonNull
    private final Resources resources;

    @NonNull
    private final MediatorLiveData<List<RestaurantListViewStateItem>> restaurantListMediatorLiveData = new MediatorLiveData<>();


    @Inject
    public RestaurantListViewModel(
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase,
        @NonNull GetCurrentLocationStateUseCase getCurrentLocationStateUseCase,
        @NonNull HasGpsPermissionUseCase hasGpsPermissionUseCase,
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull Resources resources,
        @NonNull GetAttendantsGoingToSameRestaurantAsUserUseCase getAttendantsGoingToSameRestaurantAsUserUseCase,
        @NonNull GetPredictionPlaceIdUseCase getPredictionPlaceIdUseCase

    ) {
        this.resources = resources;

        LiveData<LocationStateEntity> locationLiveData = getCurrentLocationStateUseCase.invoke();
        LiveData<Boolean> isGpsEnabledMutableLiveData = isGpsEnabledUseCase.invoke();
        LiveData<Map<String, Integer>> attendantsByRestaurantIdsLiveData = getAttendantsGoingToSameRestaurantAsUserUseCase.invoke();
        LiveData<Boolean> hasGpsPermissionLiveData = hasGpsPermissionUseCase.invoke();
        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();
        LiveData<String> placeIdLiveData = getPredictionPlaceIdUseCase.invoke();


        restaurantListMediatorLiveData.addSource(hasGpsPermissionLiveData, hasGpsPermission ->
            combine(
                hasGpsPermission,
                isGpsEnabledMutableLiveData.getValue(),
                locationLiveData.getValue(),
                nearbySearchWrapperLiveData.getValue(),
                attendantsByRestaurantIdsLiveData.getValue(),
                placeIdLiveData.getValue()
            )
        );

        restaurantListMediatorLiveData.addSource(locationLiveData, location ->
            combine(
                hasGpsPermissionLiveData.getValue(),
                isGpsEnabledMutableLiveData.getValue(),
                location,
                nearbySearchWrapperLiveData.getValue(),
                attendantsByRestaurantIdsLiveData.getValue(),
                placeIdLiveData.getValue()
            )
        );

        restaurantListMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper ->
            combine(
                hasGpsPermissionLiveData.getValue(),
                isGpsEnabledMutableLiveData.getValue(),
                locationLiveData.getValue(),
                nearbySearchWrapper,
                attendantsByRestaurantIdsLiveData.getValue(),
                placeIdLiveData.getValue()
            )
        );

        restaurantListMediatorLiveData.addSource(isGpsEnabledMutableLiveData, isGpsEnabled ->
            combine(
                hasGpsPermissionLiveData.getValue(),
                isGpsEnabled,
                locationLiveData.getValue(),
                nearbySearchWrapperLiveData.getValue(),
                attendantsByRestaurantIdsLiveData.getValue(),
                placeIdLiveData.getValue()
            )
        );

        restaurantListMediatorLiveData.addSource(attendantsByRestaurantIdsLiveData, attendantsByRestaurantIds ->
            combine(
                hasGpsPermissionLiveData.getValue(),
                isGpsEnabledMutableLiveData.getValue(),
                locationLiveData.getValue(),
                nearbySearchWrapperLiveData.getValue(),
                attendantsByRestaurantIds,
                placeIdLiveData.getValue()
            )
        );

        restaurantListMediatorLiveData.addSource(placeIdLiveData, placeId ->
            combine(
                hasGpsPermissionLiveData.getValue(),
                isGpsEnabledMutableLiveData.getValue(),
                locationLiveData.getValue(),
                nearbySearchWrapperLiveData.getValue(),
                attendantsByRestaurantIdsLiveData.getValue(),
                placeId)
        );

    }

    private void combine(
        @Nullable Boolean hasGpsPermission,
        @Nullable Boolean isGpsEnabled,
        @Nullable LocationStateEntity locationStateEntity,
        @Nullable NearbySearchWrapper nearbySearchWrapper,
        @Nullable Map<String, Integer> attendantsByRestaurantIds,
        @Nullable String placeId
    ) {
        if (nearbySearchWrapper == null || locationStateEntity == null || isGpsEnabled == null) {
            return;
        }
        List<RestaurantListViewStateItem> result = new ArrayList<>();

        if (hasGpsPermission != null && !hasGpsPermission) {
            result.add(
                new RestaurantListViewStateItem.RestaurantListErrorItem(
                    resources.getString(R.string.list_error_message_no_gps),
                    NO_GPS_FOUND
                )
            );
            restaurantListMediatorLiveData.setValue(result);
        }

        if (locationStateEntity instanceof LocationStateEntity.GpsProviderDisabled) {
            result.add(
                new RestaurantListViewStateItem.RestaurantListErrorItem(
                    resources.getString(R.string.list_error_message_no_gps),
                    NO_GPS_FOUND
                )
            );
            restaurantListMediatorLiveData.setValue(result);
            return;
        }

        if (!isGpsEnabled) {
            result.add(
                new RestaurantListViewStateItem.RestaurantListErrorItem(
                    resources.getString(R.string.list_error_message_no_gps),
                    NO_GPS_FOUND
                )
            );
            restaurantListMediatorLiveData.setValue(result);
            return;
        }


        if (nearbySearchWrapper instanceof NearbySearchWrapper.Loading) {
            result.add(
                new RestaurantListViewStateItem.Loading()
            );
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.NoResults) {
            result.add(
                new RestaurantListViewStateItem.RestaurantListErrorItem(
                    resources.getString(R.string.list_error_message_no_results),
                    NO_RESULT_FOUND
                )
            );
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
            if (locationStateEntity instanceof LocationStateEntity.Success) {
                LocationEntity location = ((LocationStateEntity.Success) locationStateEntity).locationEntity;

                List<NearbySearchEntity> sortedRestaurantList = sortNearbyRestaurants(((NearbySearchWrapper.Success) nearbySearchWrapper).getNearbySearchEntityList(), location);
                for (NearbySearchEntity nearbySearchEntity : sortedRestaurantList) {
                    if (placeId == null) {
                        result.add(
                            new RestaurantListViewStateItem.RestaurantItemItem(
                                nearbySearchEntity.getPlaceId(),
                                nearbySearchEntity.getRestaurantName(),
                                nearbySearchEntity.getVicinity(),
                                (new DecimalFormat("#").format(nearbySearchEntity.getDistance())) + "m",
                                getAttendants(nearbySearchEntity.getPlaceId(), attendantsByRestaurantIds),
                                formatOpeningStatus(nearbySearchEntity.isOpen()),
                                parseRestaurantPictureUrl(nearbySearchEntity.getPhotoReferenceUrl()),
                                isRatingBarVisible(nearbySearchEntity.getRating()),
                                convertFiveToThreeRating(nearbySearchEntity.getRating())
                            )
                        );
                    } else if (nearbySearchEntity.getPlaceId().equals(placeId)) {
                        result.add(
                            new RestaurantListViewStateItem.RestaurantItemItem(
                                nearbySearchEntity.getPlaceId(),
                                nearbySearchEntity.getRestaurantName(),
                                nearbySearchEntity.getVicinity(),
                                (new DecimalFormat("#").format(nearbySearchEntity.getDistance())) + "m",
                                getAttendants(nearbySearchEntity.getPlaceId(), attendantsByRestaurantIds),
                                formatOpeningStatus(nearbySearchEntity.isOpen()),
                                parseRestaurantPictureUrl(nearbySearchEntity.getPhotoReferenceUrl()),
                                isRatingBarVisible(nearbySearchEntity.getRating()),
                                convertFiveToThreeRating(nearbySearchEntity.getRating())
                            )
                        );
                        break;
                    }
                }
            }
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.RequestError) {
            ((NearbySearchWrapper.RequestError) nearbySearchWrapper).getThrowable().printStackTrace();
            result.add(
                new RestaurantListViewStateItem.RestaurantListErrorItem(
                    resources.getString(R.string.list_error_message_generic),
                    REQUEST_FAILURE
                )
            );
        }
        if (result.isEmpty()) {
            result.add(
                new RestaurantListViewStateItem.RestaurantListErrorItem(
                    resources.getString(R.string.list_no_restaurant_match),
                    NO_RESULT_FOUND
                )
            );
        }
        restaurantListMediatorLiveData.setValue(result);
    }

    private List<NearbySearchEntity> sortNearbyRestaurants(
        @NonNull List<NearbySearchEntity> nearbySearchEntityList,
        @NonNull LocationEntity userLocationEntity
    ) {
        LatLng userLatLng = new LatLng(userLocationEntity.getLatitude(), userLocationEntity.getLongitude());
        return nearbySearchEntityList.stream()
            .sorted(Comparator.comparingDouble(place ->
                    SphericalUtil.computeDistanceBetween(
                        userLatLng,
                        new LatLng(place.getLocationEntity().getLatitude(), place.getLocationEntity().getLongitude()
                        )
                    )
                )
            )
            .collect(Collectors.toList());
    }

    private String getAttendants(
        String placeId,
        Map<String, Integer> attendantsByRestaurantIds
    ) {
        if (attendantsByRestaurantIds != null &&
            attendantsByRestaurantIds.containsKey(placeId) &&
            attendantsByRestaurantIds.get(placeId) != null
        ) {
            return String.valueOf(attendantsByRestaurantIds.get(placeId));
        } else {
            return "0";
        }
    }

    @NonNull
    public LiveData<List<RestaurantListViewStateItem>> getRestaurants() {
        return restaurantListMediatorLiveData;
    }

    private RestaurantOpeningState formatOpeningStatus(@Nullable Boolean isOpen) {
        if (isOpen == null) {
            return IS_NOT_DEFINED;
        } else if (isOpen) {
            return IS_OPEN;
        } else {
            return IS_CLOSED;
        }
    }

    private boolean isRatingBarVisible(@Nullable Float rating) {
        return rating != null && rating > 0F;
    }

    @Nullable
    private String parseRestaurantPictureUrl(String photoReferenceUrl) {
        if (photoReferenceUrl != null && !photoReferenceUrl.isEmpty()) {
            return resources.getString(R.string.google_image_url, photoReferenceUrl, API_KEY);
        } else {
            return null;
        }
    }

    private float convertFiveToThreeRating(@Nullable Float fiveRating) {
        if (fiveRating == null) {
            return 0f;
        } else {
            float convertedRating = Math.round(fiveRating * 2) / 2f;
            return Math.min(3f, convertedRating / 5f * 3f);
        }
    }
}