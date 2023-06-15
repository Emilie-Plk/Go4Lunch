package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import java.util.List;

public interface UserRepository {

    void upsertLoggedUserEntity(@Nullable LoggedUserEntity userEntity);

    LiveData<List<LoggedUserEntity>> getLoggedUserEntitiesLiveData();

    LiveData<LoggedUserEntity> getLoggedUserEntityLiveData(@NonNull String userId); // TODO: need this method?

    void upsertUserRestaurantChoice(
        @Nullable LoggedUserEntity loggedUserEntity,
        @NonNull ChosenRestaurantEntity chosenRestaurantEntity
    );

    LiveData<List<UserWithRestaurantChoiceEntity>> getUserWithRestaurantChoiceEntities();

    LiveData<UserWithRestaurantChoiceEntity> getUserWithRestaurantChoiceEntity(@NonNull String userId);

    void deleteUserRestaurantChoice(@Nullable LoggedUserEntity loggedUserEntity);

}
