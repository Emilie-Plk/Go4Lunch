package com.emplk.go4lunch.ui.restaurant_list;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.GPSlocation.GPSLocationRepository;
import com.emplk.go4lunch.data.GPSlocation.LocationPermissionState;
import com.emplk.go4lunch.data.GPSlocation.PermissionChecker;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchEntity;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchRepository;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchWrapper;

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
    private final PermissionChecker permissionChecker;


    private final MediatorLiveData<List<RestaurantListViewState>> restaurantListMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<Boolean> hasGpsPermissionLiveData = new MutableLiveData<>();

    @Inject
    public RestaurantListViewModel(
        @NonNull NearbySearchRepository nearbySearchRepository,
        @NonNull GPSLocationRepository gpsLocationRepository,
        @NonNull PermissionChecker permissionChecker) {
        this.nearbySearchRepository = nearbySearchRepository;
        this.permissionChecker = permissionChecker;
        this.gpsLocationRepository = gpsLocationRepository;

        LiveData<Location> locationLiveData = gpsLocationRepository.getLocationLiveData();
        //  LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = nearbySearchRepository.getNearbyRestaurants(parameters?)

        restaurantListMediatorLiveData.addSource(locationLiveData, location ->
            combine(location, hasGpsPermissionLiveData.getValue()
            )
        );

        restaurantListMediatorLiveData.addSource(hasGpsPermissionLiveData, hasGpsPermission ->
            combine(locationLiveData.getValue(), hasGpsPermission
            )
        );

    }

    private void combine(@Nullable Location location, @Nullable Boolean hasGpsPermission) {
        MediatorLiveData<List<RestaurantListViewState>> mediatorLiveData = new MediatorLiveData<>();
         if (location == null) {
            if (hasGpsPermission == null || !hasGpsPermission) {
                Log.e(TAG, "GPS permission not granted!");
                //TODO: I don't want to display a LIST, just a <RestaurantListViewState> :'(
                mediatorLiveData.setValue(
                    new RestaurantListViewState.DatabaseError(
                        "Something went wrong with your request! \n Try later :)"
                    )
                );
            }
            return;
        }
        switch (getLocationPermissionState(location, hasGpsPermission)) {
            case NO_PERMISSION:
                // TODO: ViewState/Wrapper for permissions
                break;
            case NO_LOCATION:
                break;
            case LOCATION_PERMISSION:
                mediatorLiveData.addSource(
                    nearbySearchRepository.getNearbyRestaurants(
                        location.getLatitude() + "," + location.getLongitude(),
                        "restaurant",
                        "restaurant",
                        "distance",
                        API_KEY),
                    nearbySearchWrapper -> {
                        List<RestaurantListViewState> restaurantListViewStates = new ArrayList<>();

                        if (nearbySearchWrapper instanceof NearbySearchWrapper.Loading) {
                            Log.i(TAG, "Loading state");
                            restaurantListViewStates.add(
                                new RestaurantListViewState.Loading(
                                    "Loading...  \n Please wait"
                                )
                            );
                        }


                        if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
                            for (NearbySearchEntity nearbySearchEntity : ((NearbySearchWrapper.Success) nearbySearchWrapper).getResults()) {
                                restaurantListViewStates.add(
                                    new RestaurantListViewState.RestaurantList(
                                        nearbySearchEntity.getPlaceId(),
                                        nearbySearchEntity.getRestaurantName(),
                                        formatCuisine(nearbySearchEntity.getCuisine()),
                                        nearbySearchEntity.getVicinity(),
                                        getDistanceString(location.getLatitude(), location.getLongitude(), nearbySearchEntity.getLatitude(), nearbySearchEntity.getLongitude()),
                                        "3",
                                        nearbySearchEntity.getOpeningHours().toString(),
                                        nearbySearchEntity.getOpeningHours(),
                                        "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photo_reference=" + nearbySearchEntity.getPhotoReferenceUrl() + "&key=" + API_KEY,
                                        convertFiveToThreeRating(nearbySearchEntity.getRating())
                                    )
                                );
                            }
                        }

                        if (nearbySearchWrapper instanceof NearbySearchWrapper.Error) {
                            Log.e(TAG, "Exception encountered: " + ((NearbySearchWrapper.Error) nearbySearchWrapper).getThrowable().getMessage());
                            restaurantListViewStates.add(
                                new RestaurantListViewState.DatabaseError(
                                    "Something went wrong with your request! \n Try later :)"
                                )
                            );
                        }

                        restaurantListMediatorLiveData.setValue(restaurantListViewStates);
                    }
                );
                restaurantListMediatorLiveData.addSource(mediatorLiveData, restaurantItemViewStates ->
                    restaurantListMediatorLiveData.setValue(restaurantItemViewStates)
                );
                break;
        }
    }

    private LocationPermissionState getLocationPermissionState(@Nullable Location location, @Nullable Boolean hasGpsPermission) {
        if (hasGpsPermission == null || !hasGpsPermission) {
            return LocationPermissionState.NO_PERMISSION;
        } else if (location == null) {
            return LocationPermissionState.NO_LOCATION;
        } else {
            return LocationPermissionState.LOCATION_PERMISSION;
        }
    }


    public LiveData<List<RestaurantListViewState>> getRestaurantItemViewStateListLiveData() {
        return restaurantListMediatorLiveData;
    }

    @SuppressLint("MissingPermission")
    public void refresh() {
        boolean hasGpsPermission = permissionChecker.hasLocationPermission();
        hasGpsPermissionLiveData.setValue(hasGpsPermission);

        if (hasGpsPermission) {
            gpsLocationRepository.startLocationRequest();
        } else {
            gpsLocationRepository.stopLocationRequest();
        }
    }

  /*      private String formatVicinity (String vicinity){
            return vicinity.split("\\,")[0];
        }*/

    private String formatCuisine(String cuisine) {
        return cuisine.substring(0, 1).toUpperCase() + cuisine.substring(1);
    }

    private String getDistanceString(double userLat, double userLong, Float lat, Float longit) {
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
    }

    public float convertFiveToThreeRating(float fiveRating) {
        float convertedRating = Math.round(fiveRating * 2) / 2f; // round to nearest 0.5
        return Math.min(3f, convertedRating / 5f * 3f); // convert 3 -> 5 with steps of 0.5
    }
}