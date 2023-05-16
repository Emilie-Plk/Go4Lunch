package com.emplk.go4lunch.ui.restaurant_list;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.data.gps_location.GPSLocationRepository;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchEntity;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchRepository;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchWrapper;
import com.emplk.go4lunch.data.permission.GPSPermissionRepository;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantListViewModel extends ViewModel {

    private static final String TAG = "RestaurantListViewModel";

    @NonNull
    private final NearbySearchRepository nearbySearchRepository;

    @NonNull
    private final GPSLocationRepository gpsLocationRepository;

    @NonNull
    private final GPSPermissionRepository gpsPermissionRepository;

    @NonNull
    private final Application application;


    private final MediatorLiveData<List<RestaurantListViewState>> restaurantListMediatorLiveData = new MediatorLiveData<>();

    private final LiveData<Boolean> hasGpsPermissionLiveData;


    @Inject
    public RestaurantListViewModel(
        @NonNull NearbySearchRepository nearbySearchRepository,
        @NonNull GPSLocationRepository gpsLocationRepository,
        @NonNull GPSPermissionRepository gpsPermissionRepository,
        @NonNull Application application
    ) {
        this.nearbySearchRepository = nearbySearchRepository;
        this.gpsLocationRepository = gpsLocationRepository;
        this.gpsPermissionRepository = gpsPermissionRepository;
        this.application = application;

        LiveData<Location> locationLiveData = gpsLocationRepository.getLocationLiveData();

        hasGpsPermissionLiveData = gpsPermissionRepository.hasGPSPermission();

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = Transformations.switchMap(
            locationLiveData, new Function<Location, LiveData<NearbySearchWrapper>>() {
                @Override
                public LiveData<NearbySearchWrapper> apply(@Nullable Location location) {
                    if (location != null) {
                        return nearbySearchRepository.getNearbyRestaurants(
                            location.getLatitude() + "," + location.getLongitude(),
                            "restaurant",
                            "distance",
                            API_KEY
                        );
                    }
                    return null;
                }
            }
        );

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
        @Nullable Location location,
        @Nullable NearbySearchWrapper nearbySearchWrapper
    ) {
        List<RestaurantListViewState> result = new ArrayList<>();

        if (location == null) {
            if (hasGpsPermission != null && !hasGpsPermission) {
                setErrorState("Please provide GPS permission to continue!", AppCompatResources.getDrawable(application.getApplicationContext(), R.drawable.baseline_sad_face_24));
            }
        }

        if (nearbySearchWrapper != null) {
            if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
                if (((NearbySearchWrapper.Success) nearbySearchWrapper).getResults().isEmpty() ||
                    ((NearbySearchWrapper.Success) nearbySearchWrapper).getResults() == null) {
                    setErrorState(
                        "Oops, no found restaurant in this area!",
                        null
                    );
                } else {
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
                }
            }

            if (nearbySearchWrapper instanceof NearbySearchWrapper.Loading) {
                result.add(
                    new RestaurantListViewState.Loading()
                );
            }

            if (nearbySearchWrapper instanceof NearbySearchWrapper.Error) {
                ((NearbySearchWrapper.Error) nearbySearchWrapper).getThrowable().printStackTrace();
                setErrorState(
                    application.getResources().getString(R.string.list_error_message_generic),
                    AppCompatResources.getDrawable(application.getApplicationContext(), R.drawable.baseline_sad_face_24)
                );
            }
        }
        restaurantListMediatorLiveData.setValue(result);
    }

    private boolean isRatingBarVisible(@Nullable Float rating) {
        return rating != null && rating > 0F;
    }


    private String parseRestaurantPictureUrl(String photoReferenceUrl) {
        if (photoReferenceUrl != null && !photoReferenceUrl.isEmpty()) {
            return String.format(application
                .getApplicationContext()
                .getString(R.string.google_image_url), photoReferenceUrl, API_KEY);
        } else {
            Uri uri = Uri.parse("android.resource://com.emplk.go4lunch/" + R.drawable.restaurant_table);
            return uri.toString();
        }
    }

    public LiveData<List<RestaurantListViewState>> getRestaurantItemViewStateListLiveData() {
        return restaurantListMediatorLiveData;
    }

    @SuppressLint("MissingPermission")
    public void setLocationRequest() {
        gpsLocationRepository.startLocationRequest();
    }

    private String getDistanceString(
        @Nullable Double userLat,
        @Nullable Double userLong,
        @Nullable Float lat,
        @Nullable Float longit) {
        if (userLat != null && userLong != null && lat != null && longit != null) {
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

    private float convertFiveToThreeRating(Float fiveRating) {
        float convertedRating = Math.round(fiveRating * 2) / 2f; // round to nearest 0.5
        return Math.min(3f, convertedRating / 5f * 3f); // convert 3 -> 5 with steps of 0.5
    }

    private void setErrorState(@NonNull String message, @Nullable Drawable drawable) {
        List<RestaurantListViewState> result = new ArrayList<>();
        result.add(new RestaurantListViewState.RestaurantListError(message, drawable));
        restaurantListMediatorLiveData.setValue(result);
    }
}