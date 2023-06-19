package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.GetFavoriteRestaurantsIdsUseCase;
import com.emplk.go4lunch.domain.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

public class GetUserEntityUseCase {

    @NonNull
    private final MediatorLiveData<UserEntity> userEntityMediatorLiveData;

    @Nullable
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;


    @Inject
    public GetUserEntityUseCase(
        @Nullable GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase,
        @NonNull GetFavoriteRestaurantsIdsUseCase getFavoriteRestaurantsIdsUseCase,
        @NonNull GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase
    ) {
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
        userEntityMediatorLiveData = new MediatorLiveData<>();

        LiveData<Set<String>> favoriteRestaurantsIdsLiveData = getFavoriteRestaurantsIdsUseCase.invoke();
        LiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityLiveData = getUserWithRestaurantChoiceEntityLiveDataUseCase.invoke();

        userEntityMediatorLiveData.addSource(favoriteRestaurantsIdsLiveData, favoriteRestaurantIds -> {
                combine(
                    favoriteRestaurantIds,
                    userWithRestaurantChoiceEntityLiveData.getValue()
                );
            }
        );

        userEntityMediatorLiveData.addSource(userWithRestaurantChoiceEntityLiveData, userWithRestaurantChoice -> {
                combine(
                    favoriteRestaurantsIdsLiveData.getValue(),
                    userWithRestaurantChoice
                );
            }
        );
    }

    public LiveData<UserEntity> invoke() {
        return userEntityMediatorLiveData;
    }


    private void combine(
        @Nullable Set<String> favoriteRestaurantsIds,
        @Nullable UserWithRestaurantChoiceEntity userWithRestaurantChoice
    ) {
        LoggedUserEntity loggedUserEntity = getCurrentLoggedUserUseCase.invoke();

        if (loggedUserEntity == null) {
            return;
        }

        Set<String> updatedFavoriteRestaurantsIds = new HashSet<>(favoriteRestaurantsIds != null ? favoriteRestaurantsIds : Collections.emptySet());

        userEntityMediatorLiveData.setValue(
            new UserEntity(
                loggedUserEntity,
                updatedFavoriteRestaurantsIds,
                userWithRestaurantChoice != null ? userWithRestaurantChoice.getAttendingRestaurantId() : null
            )
        );
    }
}