package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.Set;

public interface FavoriteRestaurantRepository {

    void addFavoriteRestaurant(
        @NonNull String userId,
        @NonNull String restaurantId
    );

    void removeFavoriteRestaurant(
        @NonNull String userId,
        @NonNull String restaurantId
    );

    @NonNull
    LiveData<Set<String>> getUserFavoriteRestaurantIdsLiveData(@NonNull String userId);
}
