package com.example.go4lunch.ui.restaurant_list;

import static com.example.go4lunch.BuildConfig.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.data.API.nearby_search_restaurants.NearbySearchRepository;
import com.example.go4lunch.data.API.nearby_search_restaurants.NearbySearchResult;
import com.example.go4lunch.data.API.nearby_search_restaurants.NearbySearchWrapper;

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
                "pizza",
                "distance",
                API_KEY), nearbySearchWrapper -> {
                List<RestaurantItemViewState> restaurantItemViewStates = new ArrayList<>();
                if (nearbySearchWrapper instanceof NearbySearchWrapper.Success) {
                    for (NearbySearchResult nearbySearchResult : ((NearbySearchWrapper.Success) nearbySearchWrapper).getResults()) {
                        restaurantItemViewStates.add(
                            new RestaurantItemViewState(
                                maxId++,
                                nearbySearchResult.getName(),
                                nearbySearchResult.getAddress()
                            )
                        );
                    }
                }
                if (nearbySearchWrapper instanceof NearbySearchWrapper.Error) {
                    restaurantItemViewStates.add(
                        new RestaurantItemViewState(
                            maxId++, "error", "error"
                        )
                    );
                }
                return restaurantItemViewStates;
            });
    }
}