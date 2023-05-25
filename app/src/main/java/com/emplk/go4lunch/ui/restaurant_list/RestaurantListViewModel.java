package com.emplk.go4lunch.ui.restaurant_list;


import static com.emplk.go4lunch.BuildConfig.API_KEY;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.NO_GPS_FOUND;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.NO_RESULT_FOUND;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.REQUEST_FAILURE;
import static com.emplk.go4lunch.ui.restaurant_list.ErrorDrawable.UNKNOWN_ERROR;

import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchEntity;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchWrapper;
import com.emplk.go4lunch.domain.gps.LocationEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.permission.GetGpsPermissionUseCase;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantListViewModel extends ViewModel {

    @NonNull
    private final Resources resources;

    private final MediatorLiveData<List<RestaurantListViewState>> restaurantListMediatorLiveData = new MediatorLiveData<>();

    private final LiveData<Boolean> hasGpsPermissionLiveData;


    @Inject
    public RestaurantListViewModel(
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase,
        @NonNull GetCurrentLocationUseCase getCurrentLocationUseCase,
        @NonNull GetGpsPermissionUseCase getGpsPermissionUseCase,
        @NonNull Resources resources
    ) {
        this.resources = resources;

        LiveData<LocationEntity> locationLiveData = getCurrentLocationUseCase.invoke();

        hasGpsPermissionLiveData = getGpsPermissionUseCase.invoke();

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();

        restaurantListMediatorLiveData.addSource(hasGpsPermissionLiveData, hasGpsPermission ->
            combine(hasGpsPermission, locationLiveData.getValue(), nearbySearchWrapperLiveData.getValue()
            )
        );

        restaurantListMediatorLiveData.addSource(locationLiveData, location ->
            combine(hasGpsPermissionLiveData.getValue(), location, nearbySearchWrapperLiveData.getValue()
            )
        );

        restaurantListMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper ->
            combine(hasGpsPermissionLiveData.getValue(), locationLiveData.getValue(), nearbySearchWrapper
            )
        );
    }

    private void combine(
        @Nullable Boolean hasGpsPermission,
        @Nullable LocationEntity location,
        @Nullable NearbySearchWrapper nearbySearchWrapper
    ) {
        List<RestaurantListViewState> result = new ArrayList<>();

        if (location == null) {
            if (hasGpsPermission != null && !hasGpsPermission) {
                result.add(
                    new RestaurantListViewState.RestaurantListError(
                        resources.getString(R.string.list_error_message_no_gps),
                        NO_GPS_FOUND
                    )
                );
                restaurantListMediatorLiveData.setValue(result);
            }
            return;
        }

        if (nearbySearchWrapper == null) {
            result.add(
                new RestaurantListViewState.RestaurantListError(
                    resources.getString(R.string.list_error_message_generic),
                    UNKNOWN_ERROR
                )
            );
            return;
        }

        if (nearbySearchWrapper instanceof NearbySearchWrapper.Loading) {
            result.add(
                new RestaurantListViewState.Loading()
            );
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.NoResults) {
            result.add(
                new RestaurantListViewState.RestaurantListError(
                    resources.getString(R.string.list_error_message_no_results),
                    NO_RESULT_FOUND
                )
            );
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
            for (NearbySearchEntity nearbySearchEntity : ((NearbySearchWrapper.Success) nearbySearchWrapper).getResults()) {
                result.add(
                    new RestaurantListViewState.RestaurantList(
                        nearbySearchEntity.getPlaceId(),
                        nearbySearchEntity.getRestaurantName(),
                        nearbySearchEntity.getVicinity(),
                        getDistanceString(location.getLatitude(), location.getLongitude(), nearbySearchEntity.getLocationEntity()),
                        "3",
                        "14h-16h",
                        true,
                        parseRestaurantPictureUrl(nearbySearchEntity.getPhotoReferenceUrl()),
                        isRatingBarVisible(nearbySearchEntity.getRating()),
                        convertFiveToThreeRating(nearbySearchEntity.getRating()
                        )
                    )
                );
            }
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.RequestError) {
            ((NearbySearchWrapper.RequestError) nearbySearchWrapper).getThrowable().printStackTrace();
            result.add(
                new RestaurantListViewState.RestaurantListError(
                    resources.getString(R.string.list_error_message_generic),
                    REQUEST_FAILURE
                )
            );
        }

        restaurantListMediatorLiveData.setValue(result);
    }

    private boolean isRatingBarVisible(@Nullable Float rating) {
        return rating != null && rating > 0F;
    }

    public LiveData<List<RestaurantListViewState>> getRestaurantItemViewStateListLiveData() {
        return restaurantListMediatorLiveData;
    }

    private String parseRestaurantPictureUrl(String photoReferenceUrl) {
        if (photoReferenceUrl != null && !photoReferenceUrl.isEmpty()) {
            return String.format(
                resources.getString(R.string.google_image_url), photoReferenceUrl, API_KEY);
        } else {
            Uri uri = Uri.parse("android.resource://com.emplk.go4lunch/" + R.drawable.restaurant_table);
            return uri.toString();
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
        return decimalFormat.format(distance).split("\\.")[0] + "m";
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