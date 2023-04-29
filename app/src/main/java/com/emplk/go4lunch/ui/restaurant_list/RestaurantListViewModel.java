package com.emplk.go4lunch.ui.restaurant_list;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchEntity;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchWrapper;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchRepository;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantListViewModel extends ViewModel {

    @NonNull
    private final NearbySearchRepository repository;

    private int maxId = 0;

    @Inject
    public RestaurantListViewModel(@NonNull NearbySearchRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<RestaurantItemViewState>> getRestaurantListItemViewState() {
        return Transformations.map(
            repository.getNearbyRestaurants(
                "48.911241, 2.291856",
                "restaurant",
                "restaurant",
                "distance",
                API_KEY), nearbySearchWrapper -> {
                List<RestaurantItemViewState> restaurantItemViewStates = new ArrayList<>();
                if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
                    for (NearbySearchEntity nearbySearchEntity : ((NearbySearchWrapper.Success) nearbySearchWrapper).getResults()) {
                        restaurantItemViewStates.add(
                            new RestaurantItemViewState(
                                nearbySearchEntity.getPlaceId(),
                                nearbySearchEntity.getRestaurantName(),
                                formatCuisine(nearbySearchEntity.getCuisine()),
                                formatVicinity(nearbySearchEntity.getVicinity()),
                                getDistanceString(nearbySearchEntity.getLatitude(), nearbySearchEntity.getLongitude()),
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
                    restaurantItemViewStates.add(
                        new RestaurantItemViewState(
                            "error",
                            "error",
                            "error",
                            "error",
                            "error",
                            "error", "error", isOpen, openingStateColor, "error", null
                        )
                    );
                }
                return restaurantItemViewStates;
            });
    }

    private String formatVicinity(String vicinity) {
        return vicinity.split("\\,")[0];
    }

    private String formatCuisine(String cuisine) {
        return cuisine.substring(0, 1).toUpperCase() + cuisine.substring(1);
    }

    private String getDistanceString(Float lat, Float longit) {
        Location userLocation = new Location("userLocation");
        userLocation.setLatitude(48.911241);
        userLocation.setLongitude(2.291856);

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