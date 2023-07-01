package com.emplk.go4lunch.ui.restaurant_list;


import static com.emplk.go4lunch.BuildConfig.API_KEY;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.NO_GPS_FOUND;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.NO_RESULT_FOUND;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.REQUEST_FAILURE;
import static com.emplk.go4lunch.ui.restaurant_list.RestaurantOpeningState.IS_CLOSED;
import static com.emplk.go4lunch.ui.restaurant_list.RestaurantOpeningState.IS_NOT_DEFINED;
import static com.emplk.go4lunch.ui.restaurant_list.RestaurantOpeningState.IS_OPEN;

import android.content.res.Resources;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.SortNearbyRestaurantsUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;
import com.emplk.go4lunch.domain.workmate.GetAttendantsByRestaurantIdsUseCase;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantListViewModel extends ViewModel {

    @NonNull
    private final Resources resources;

    private final MediatorLiveData<List<RestaurantListViewStateItem>> restaurantListMediatorLiveData = new MediatorLiveData<>();
    @NonNull
    private final SortNearbyRestaurantsUseCase sortNearbyRestaurantsUseCase;

    @NonNull
    private final GetAttendantsByRestaurantIdsUseCase getAttendantsByRestaurantIdsUseCase;

    private final LiveData<Boolean> hasGpsPermissionLiveData;


    @Inject
    public RestaurantListViewModel(
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase,
        @NonNull GetCurrentLocationStateUseCase getCurrentLocationStateUseCase,
        @NonNull HasGpsPermissionUseCase hasGpsPermissionUseCase,
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull Resources resources,
        @NonNull SortNearbyRestaurantsUseCase sortNearbyRestaurantsUseCase,
        @NonNull GetAttendantsByRestaurantIdsUseCase getAttendantsByRestaurantIdsUseCase
    ) {
        this.resources = resources;
        this.sortNearbyRestaurantsUseCase = sortNearbyRestaurantsUseCase;
        this.getAttendantsByRestaurantIdsUseCase = getAttendantsByRestaurantIdsUseCase;

        LiveData<LocationStateEntity> locationLiveData = getCurrentLocationStateUseCase.invoke();

        LiveData<Boolean> isGpsEnabledMutableLiveData = isGpsEnabledUseCase.invoke();

        LiveData<Map<String, Integer>> attendantsByRestaurantIdsLiveData = getAttendantsByRestaurantIdsUseCase.invoke();

        hasGpsPermissionLiveData = hasGpsPermissionUseCase.invoke();

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();


        restaurantListMediatorLiveData.addSource(hasGpsPermissionLiveData, hasGpsPermission ->
            combine(hasGpsPermission, isGpsEnabledMutableLiveData.getValue(), locationLiveData.getValue(), nearbySearchWrapperLiveData.getValue(), attendantsByRestaurantIdsLiveData.getValue())
        );

        //TODO: virer le isGpsEnabled?
        restaurantListMediatorLiveData.addSource(locationLiveData, location ->
            combine(hasGpsPermissionLiveData.getValue(), isGpsEnabledMutableLiveData.getValue(), location, nearbySearchWrapperLiveData.getValue(), attendantsByRestaurantIdsLiveData.getValue())
        );

        restaurantListMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper ->
            combine(hasGpsPermissionLiveData.getValue(), isGpsEnabledMutableLiveData.getValue(), locationLiveData.getValue(), nearbySearchWrapper, attendantsByRestaurantIdsLiveData.getValue())
        );

        restaurantListMediatorLiveData.addSource(isGpsEnabledMutableLiveData, isGpsEnabled ->
            combine(hasGpsPermissionLiveData.getValue(), isGpsEnabled, locationLiveData.getValue(), nearbySearchWrapperLiveData.getValue(), attendantsByRestaurantIdsLiveData.getValue())
        );

        restaurantListMediatorLiveData.addSource(attendantsByRestaurantIdsLiveData, attendantsByRestaurantIds ->
            combine(hasGpsPermissionLiveData.getValue(), isGpsEnabledMutableLiveData.getValue(), locationLiveData.getValue(), nearbySearchWrapperLiveData.getValue(), attendantsByRestaurantIds)
        );

    }

    private void combine(
        @Nullable Boolean hasGpsPermission,
        @Nullable Boolean isGpsEnabled,
        @Nullable LocationStateEntity locationStateEntity,
        @Nullable NearbySearchWrapper nearbySearchWrapper,
        @Nullable Map<String, Integer> attendantsByRestaurantIds
    ) {
        if (nearbySearchWrapper == null || locationStateEntity == null) {
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

        if (isGpsEnabled != null && !isGpsEnabled) {
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

                List<NearbySearchEntity> sortedRestaurantList = sortNearbyRestaurantsUseCase.invoke(((NearbySearchWrapper.Success) nearbySearchWrapper).getNearbySearchEntityList(), location);
                for (NearbySearchEntity nearbySearchEntity : sortedRestaurantList) {
                    result.add(
                        new RestaurantListViewStateItem.RestaurantItemItem(
                            nearbySearchEntity.getPlaceId(),
                            nearbySearchEntity.getRestaurantName(),
                            nearbySearchEntity.getVicinity(),
                            getDistanceString(location.getLatitude(), location.getLongitude(), nearbySearchEntity.getLocationEntity()),
                            getAttendants(nearbySearchEntity.getPlaceId(), attendantsByRestaurantIds),
                            formatOpeningStatus(nearbySearchEntity.isOpen()),
                            parseRestaurantPictureUrl(nearbySearchEntity.getPhotoReferenceUrl()),
                            isRatingBarVisible(nearbySearchEntity.getRating()),
                            convertFiveToThreeRating(nearbySearchEntity.getRating())
                        )
                    );
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
        restaurantListMediatorLiveData.setValue(result);
    }

    private String getAttendants(
        String placeId,
        Map<String, Integer> attendantsByRestaurantIds
    ) {
        if (attendantsByRestaurantIds != null && attendantsByRestaurantIds.containsKey(placeId)) {
            return attendantsByRestaurantIds.get(placeId).toString();
        } else {
            return "0";
        }
    }

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

    private String getDistanceString(
        @NonNull Double userLat,
        @NonNull Double userLong,
        @NonNull LocationEntity locationEntity
    ) {
        Location userLocation = new Location("userLocation");
        userLocation.setLatitude(userLat);
        userLocation.setLongitude(userLong);

        Location restaurantLocation = new Location("nearbySearchResultRestaurantLocation");
        restaurantLocation.setLatitude(locationEntity.getLatitude());
        restaurantLocation.setLongitude(locationEntity.getLongitude());

        float distance = userLocation.distanceTo(restaurantLocation);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        decimalFormat.setRoundingMode(RoundingMode.DOWN);
        return decimalFormat.format(distance).split("[.,]")[0] + "m";
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