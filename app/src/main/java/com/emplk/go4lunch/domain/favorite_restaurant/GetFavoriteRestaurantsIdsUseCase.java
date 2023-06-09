package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;

import java.util.Set;

import javax.inject.Inject;

public class GetFavoriteRestaurantsIdsUseCase {

    @NonNull
    private final FavoriteRestaurantRepository favoriteRestaurantRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public GetFavoriteRestaurantsIdsUseCase(
        @NonNull FavoriteRestaurantRepository favoriteRestaurantRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.favoriteRestaurantRepository = favoriteRestaurantRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public LiveData<Set<String>> invoke() {
        return favoriteRestaurantRepository.getUserFavoriteRestaurantIdsLiveData(getCurrentLoggedUserIdUseCase.invoke());
    }
}

