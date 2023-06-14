package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserLiveDataUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.GetFavoriteRestaurantsIdsUseCase;
import com.emplk.go4lunch.domain.user.RestaurantEntity;
import com.emplk.go4lunch.domain.user.UserEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetUserEntityUseCase {

    @NonNull
    private final MediatorLiveData<UserEntity> userEntityMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public GetUserEntityUseCase(
        @NonNull GetCurrentLoggedUserLiveDataUseCase getCurrentLoggedUserLiveDataUseCase,
        @NonNull GetFavoriteRestaurantsIdsUseCase getFavoriteRestaurantsIdsUseCase,
        @NonNull GetUserRestaurantEntityChoiceLiveDataUseCase getUserRestaurantEntityChoiceLiveDataUseCase
    ) {

        LiveData<LoggedUserEntity> loggedUserEntityLiveData = getCurrentLoggedUserLiveDataUseCase.invoke();

        LiveData<RestaurantEntity> attendingRestaurantIdLiveData = getUserRestaurantEntityChoiceLiveDataUseCase.invoke();

        userEntityMediatorLiveData.addSource(getCurrentLoggedUserLiveDataUseCase.invoke(), currentLoggedUser -> {
                combine(
                    currentLoggedUser,
                    getFavoriteRestaurantsIdsUseCase.invoke().getValue(),
                    attendingRestaurantIdLiveData.getValue()
                );
            }
        );

        userEntityMediatorLiveData.addSource(getFavoriteRestaurantsIdsUseCase.invoke(), favoriteRestaurantIds -> {
                combine(
                    loggedUserEntityLiveData.getValue(),
                    favoriteRestaurantIds,
                    attendingRestaurantIdLiveData.getValue()
                );
            }
        );

        userEntityMediatorLiveData.addSource(attendingRestaurantIdLiveData, attendingRestaurantId -> {
                combine(
                    loggedUserEntityLiveData.getValue(),
                    getFavoriteRestaurantsIdsUseCase.invoke().getValue(),
                    attendingRestaurantId
                );
            }
        );
    }

    public LiveData<UserEntity> invoke() {
        return userEntityMediatorLiveData;
    }


    private void combine(
        @Nullable LoggedUserEntity loggedUserEntity,
        @Nullable Set<String> favoriteRestaurantsIds,
        @Nullable RestaurantEntity attendingRestaurant
    ) {
        if (loggedUserEntity == null) {
            return;
        }

        Set<String> updatedFavoriteRestaurantsIds = new HashSet<>(favoriteRestaurantsIds != null ? favoriteRestaurantsIds : Collections.emptySet());

        userEntityMediatorLiveData.setValue(
            new UserEntity(
                loggedUserEntity,
                updatedFavoriteRestaurantsIds,
                attendingRestaurant != null ? attendingRestaurant.getAttendingRestaurantId() : null
            )
        );
    }
}