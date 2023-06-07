package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;

public interface FavoriteRestaurantRepository {

    void addFavoriteRestaurant(
        @NonNull String userId,
        @NonNull String restaurantId
    );

    void removeFavoriteRestaurant(
        @NonNull String userId,
        @NonNull String restaurantId
    );
}
