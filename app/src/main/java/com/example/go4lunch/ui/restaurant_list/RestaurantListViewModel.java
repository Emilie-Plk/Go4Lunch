package com.example.go4lunch.ui.restaurant_list;

import static com.example.go4lunch.BuildConfig.API_KEY;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.data.nearbySearchRestaurants.NearbySearchEntity;
import com.example.go4lunch.data.nearbySearchRestaurants.NearbySearchRepository;
import com.example.go4lunch.data.nearbySearchRestaurants.NearbySearchWrapper;

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
                                nearbySearchEntity.getCuisine(),
                                nearbySearchEntity.getVicinity(),
                                getDistanceString(nearbySearchEntity.getLatitude(), nearbySearchEntity.getLongitude()),
                                "3",
                                nearbySearchEntity.getOpeningHours().toString(),
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
                            "error", "error", "error", null
                        )
                    );
                }
                return restaurantItemViewStates;
            });
    }

    private String getDistanceString(Float lat, Float longit) {
        Location userLocation = new Location("userLocation");
        userLocation.setLatitude(48.911241);
        userLocation.setLongitude(2.291856);

        Location restaurantLocation = new Location("restaurantLocation");
        restaurantLocation.setLatitude(lat); // here is the error
        restaurantLocation.setLongitude(longit); // here is the error

        float distance = userLocation.distanceTo(restaurantLocation);
        return Float.toString(distance);
    }

    public float convertFiveToThreeRating(float fiveRating) {
        float convertedRating = (float) Math.floor(fiveRating * 2) / 2; // arrondit le rating à 0.5 près
        return (convertedRating < 1.5f) ? 1.0f : (convertedRating < 2.0f) ? 1.5f : (convertedRating < 2.5f) ? 2.0f : (convertedRating < 3.0f) ? 2.5f : 3.0f; // convertit la note sur 5 en note sur 3 avec des steps de 0.5
    }

}