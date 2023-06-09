package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

public interface UserRepository {

    void upsertLoggedUserEntity(@Nullable LoggedUserEntity userEntity);

    LiveData<LoggedUserEntity> getLoggedUserEntityLiveData(@NonNull String userId);

    void addUserRestaurantChoice(
        @Nullable LoggedUserEntity loggedUserEntity,
        @NonNull RestaurantEntity restaurantEntity
    );

    void removeUserRestaurantChoice(@Nullable LoggedUserEntity loggedUserEntity);

    LiveData<RestaurantEntity> getUserRestaurantChoiceLiveData(@NonNull String userId);
}
