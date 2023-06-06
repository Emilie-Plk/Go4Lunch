package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

public interface UserRepository {
    void upsertUser(@NonNull LoggedUserEntity loggedUserEntity);
    void addFavoriteRestaurant(@NonNull String placeId);
    void removeFavoriteRestaurant(@NonNull String placeId);

    LiveData<LoggedUserEntity> getLoggedUserEntityLiveData(String userId);
}
