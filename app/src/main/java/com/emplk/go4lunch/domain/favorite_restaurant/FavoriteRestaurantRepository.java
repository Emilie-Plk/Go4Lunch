package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.user.RestaurantEntity;
import com.emplk.go4lunch.domain.user.UserEntity;



import java.util.List;
import java.util.Map;

public interface FavoriteRestaurantRepository {

    void addFavoriteRestaurant(@Nullable String userId, @NonNull String restaurantId);

    void removeFavoriteRestaurant(@Nullable String userId, @NonNull String restaurantId);
}
