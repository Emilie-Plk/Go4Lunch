package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;

import javax.inject.Inject;

public class RemoveFavoriteRestaurantUseCase {

    @NonNull
    private final FavoriteRestaurantRepository favoriteRestaurantRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public RemoveFavoriteRestaurantUseCase(
        @NonNull FavoriteRestaurantRepository favoriteRestaurantRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.favoriteRestaurantRepository = favoriteRestaurantRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public void invoke(@NonNull String restaurantId) {
        favoriteRestaurantRepository.removeFavoriteRestaurant(
            getCurrentLoggedUserIdUseCase.invoke(),
            restaurantId
        );
    }
}
