package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;

import javax.inject.Inject;

public class AddFavoriteRestaurantUseCase {
    @NonNull
    private final FavoriteRestaurantRepository favoriteRestaurantRepository;

    @Inject
    public AddFavoriteRestaurantUseCase(
        @NonNull FavoriteRestaurantRepository favoriteRestaurantRepository
    ) {
        this.favoriteRestaurantRepository = favoriteRestaurantRepository;
    }

    public void invoke(
        @NonNull String restaurantId,
        @NonNull String userId
    ) {
        favoriteRestaurantRepository.addFavoriteRestaurant(
            userId,
            restaurantId
        );
    }
}
