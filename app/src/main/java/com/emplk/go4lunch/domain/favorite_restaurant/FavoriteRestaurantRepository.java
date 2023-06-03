package com.emplk.go4lunch.domain.favorite_restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.user.RestaurantEntity;
import com.emplk.go4lunch.domain.user.UserEntity;



import java.util.List;

public interface FavoriteRestaurantRepository {

    LiveData<List<RestaurantEntity>> getUserFavoriteRestaurantList(@Nullable UserEntity userEntity);

    void addFavoriteRestaurant(@Nullable UserEntity userEntity, @NonNull RestaurantEntity restaurantEntity);

    void removeFavoriteRestaurant(@Nullable UserEntity userEntity, @NonNull RestaurantEntity restaurantEntity);
}
