package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

public class IsRestaurantUserFavoriteUseCase {

    @NonNull
    private final FavoriteRestaurantRepository favoriteRestaurantRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public IsRestaurantUserFavoriteUseCase(
        UserRepository userRepository,
        @NonNull FirebaseFirestore firestore,
        @NonNull FavoriteRestaurantRepository favoriteRestaurantRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.favoriteRestaurantRepository = favoriteRestaurantRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public LiveData<Boolean> invoke(String restaurantId) {
        MutableLiveData<Boolean> isFavorite = new MutableLiveData<>();
        return Transformations.switchMap(
            favoriteRestaurantRepository.getUserFavoriteRestaurantIdsLiveData(getCurrentLoggedUserIdUseCase.invoke()), favoriteRestaurantIds -> {
                isFavorite.setValue(favoriteRestaurantIds.contains(restaurantId));
                return isFavorite;
            }
        );
    }
}
