package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

public interface UserRepository {

    void upsertLoggedUserEntity(@Nullable LoggedUserEntity userEntity);

    LiveData<LoggedUserEntity> getLoggedUserEntityLiveData(@NonNull String userId); // TODO: need this method?

    void upsertUserRestaurantChoice(
        @Nullable LoggedUserEntity loggedUserEntity,
        @NonNull RestaurantEntity restaurantEntity
    );

    void deleteUserRestaurantChoice(@Nullable LoggedUserEntity loggedUserEntity);

    LiveData<RestaurantEntity> getUserRestaurantChoiceLiveData(@Nullable LoggedUserEntity loggedUserEntity);
}
