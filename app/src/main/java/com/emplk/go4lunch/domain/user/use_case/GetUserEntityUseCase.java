package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserLiveDataUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.GetFavoriteRestaurantsIdsUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.go4lunch.domain.user.use_case.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;

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
        @NonNull GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase
    ) {

        LiveData<LoggedUserEntity> loggedUserEntityLiveData = getCurrentLoggedUserLiveDataUseCase.invoke();
LiveData<Set<String>> attendingRestaurantIdLiveData = getFavoriteRestaurantsIdsUseCase.invoke();
        LiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityLiveData = getUserWithRestaurantChoiceEntityLiveDataUseCase.invoke();

        userEntityMediatorLiveData.addSource(loggedUserEntityLiveData, currentLoggedUser -> {
                combine(
                    currentLoggedUser,
                    getFavoriteRestaurantsIdsUseCase.invoke().getValue(),
                    userWithRestaurantChoiceEntityLiveData.getValue()
                );
            }
        );

        userEntityMediatorLiveData.addSource(attendingRestaurantIdLiveData, favoriteRestaurantIds -> {
                combine(
                    loggedUserEntityLiveData.getValue(),
                    favoriteRestaurantIds,
                    userWithRestaurantChoiceEntityLiveData.getValue()
                );
            }
        );

        userEntityMediatorLiveData.addSource(userWithRestaurantChoiceEntityLiveData, userWithRestaurantChoice -> {
                combine(
                    loggedUserEntityLiveData.getValue(),
                    getFavoriteRestaurantsIdsUseCase.invoke().getValue(),
                    userWithRestaurantChoice
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
        @Nullable UserWithRestaurantChoiceEntity userWithRestaurantChoice
    ) {
        if (loggedUserEntity == null && userWithRestaurantChoice == null) {
            return;
        }
        if (loggedUserEntity != null && userWithRestaurantChoice != null) {
            Set<String> updatedFavoriteRestaurantsIds = new HashSet<>(favoriteRestaurantsIds != null ? favoriteRestaurantsIds : Collections.emptySet());

            userEntityMediatorLiveData.setValue(
                new UserEntity(
                    loggedUserEntity,
                    updatedFavoriteRestaurantsIds,
                    userWithRestaurantChoice.getAttendingRestaurantId()
                )
            );
        }
    }
}