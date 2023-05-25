package com.emplk.go4lunch.ui.restaurant_list;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchEntity;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchWrapper;
import com.emplk.go4lunch.domain.gps.GpsLocationEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
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
    private final StartLocationRequestUseCase startLocationRequestUseCase;

    @NonNull
    private final Resources resources;


    private final MediatorLiveData<List<RestaurantListViewState>> restaurantListMediatorLiveData = new MediatorLiveData<>();

    private final LiveData<Boolean> hasGpsPermissionLiveData;


    @Inject
    public RestaurantListViewModel(
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase,
        @NonNull GetCurrentLocationUseCase getCurrentLocationUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase,
        @NonNull GetGpsPermissionUseCase getGpsPermissionUseCase,
        @NonNull Resources resources
    ) {
        this.startLocationRequestUseCase = startLocationRequestUseCase;
        this.resources = resources;

        LiveData<GpsLocationEntity> locationLiveData = getCurrentLocationUseCase.invoke();

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
        @Nullable GpsLocationEntity location,
        @Nullable NearbySearchWrapper nearbySearchWrapper
    ) {
        List<RestaurantListViewState> result = new ArrayList<>();

        if (location == null) {
            if (hasGpsPermission != null && !hasGpsPermission) {
                result.add(
                    new RestaurantListViewState.RestaurantListError(
                        "Please provide Gps permission\nin the Settings or look for\na location in the search bar!",
                        ResourcesCompat.getDrawable(resources, R.drawable.baseline_not_listed_location_24, null)
                    )
                );
                restaurantListMediatorLiveData.setValue(result);
            }
            return;
        }

        if (nearbySearchWrapper == null) {
            result.add(
                new RestaurantListViewState.RestaurantListError(
                    "Something went wrong!\nPlease try again later.",
                    ResourcesCompat.getDrawable(resources, R.drawable.baseline_error_outline_24, null)
                )
            );
            return;
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.Loading) {
            result.add(
                new RestaurantListViewState.Loading()
            );
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.NoResults) {
            result.add(
                new RestaurantListViewState.RestaurantListError(
                    "Oops, no results found in your area!",
                    ResourcesCompat.getDrawable(resources, R.drawable.baseline_mood_bad_24, null)
                )
            );
        } else if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
            for (NearbySearchEntity nearbySearchEntity : ((NearbySearchWrapper.Success) nearbySearchWrapper).getResults()) {
                result.add(
                    new RestaurantListViewState.RestaurantList(
                        nearbySearchEntity.getPlaceId(),
                        nearbySearchEntity.getRestaurantName(),
                        nearbySearchEntity.getVicinity(),
                        getDistanceString(location.getLatitude(), location.getLongitude(), nearbySearchEntity.getLatitude(), nearbySearchEntity.getLongitude()),
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
                    ResourcesCompat.getDrawable(resources, R.drawable.baseline_network_off_24, null)
                )
            );
        }

        restaurantListMediatorLiveData.setValue(result);
    }

    private boolean isRatingBarVisible(@Nullable Float rating) {
        return rating != null && rating > 0F;
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

    public LiveData<List<RestaurantListViewState>> getRestaurantItemViewStateListLiveData() {
        return restaurantListMediatorLiveData;
    }

    @SuppressLint("MissingPermission")
    public void refreshLocationRequest() {
        startLocationRequestUseCase.invoke();
    }

    private String getDistanceString(
        @Nullable Double userLat,
        @Nullable Double userLong,
        @NonNull Float lat,
        @NonNull Float longit
    ) {
        if (userLat != null && userLong != null) {
            Location userLocation = new Location("userLocation");
            userLocation.setLatitude(userLat);
            userLocation.setLongitude(userLong);

            Location restaurantLocation = new Location("restaurantLocation");

            restaurantLocation.setLatitude(lat);
            restaurantLocation.setLongitude(longit);

            float distance = userLocation.distanceTo(restaurantLocation);
            DecimalFormat decimalFormat = new DecimalFormat("#.#");
            decimalFormat.setRoundingMode(RoundingMode.DOWN);
            return decimalFormat.format(distance).split("\\.")[0] + "m";
        } else return "Error";
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