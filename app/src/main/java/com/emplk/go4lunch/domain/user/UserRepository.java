package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
<<<<<<< HEAD
import androidx.annotation.Nullable;
=======
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

public interface UserRepository {
<<<<<<< HEAD

    void upsertLoggedUserEntity(@Nullable LoggedUserEntity userEntity);

    LiveData<LoggedUserEntity> getLoggedUserEntityLiveData(@NonNull String userId); // TODO: need this method?

    void upsertUserRestaurantChoice(
        @Nullable LoggedUserEntity loggedUserEntity,
        @NonNull RestaurantEntity restaurantEntity
    );

    void deleteUserRestaurantChoice(@Nullable LoggedUserEntity loggedUserEntity);

    LiveData<RestaurantEntity> getUserRestaurantChoiceLiveData(@Nullable LoggedUserEntity loggedUserEntity);
=======
    void upsertUser(@NonNull LoggedUserEntity loggedUserEntity);
    void addFavoriteRestaurant(@NonNull String placeId);
    void removeFavoriteRestaurant(@NonNull String placeId);

    LiveData<LoggedUserEntity> getLoggedUserEntityLiveData(String userId);
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
}
