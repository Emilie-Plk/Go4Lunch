package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class RemoveFavoriteRestaurantUseCase {

    @NonNull
    private final FavoriteRestaurantRepository favoriteRestaurantRepository;


    @Inject
    public RemoveFavoriteRestaurantUseCase(
        @NonNull FavoriteRestaurantRepository favoriteRestaurantRepository
    ) {
        this.favoriteRestaurantRepository = favoriteRestaurantRepository;
    }

    public void invoke(
        @NonNull String restaurantId,
        @NonNull String userId
    ) {
        favoriteRestaurantRepository.removeFavoriteRestaurant(
            userId,
            restaurantId
        );
    }
}
