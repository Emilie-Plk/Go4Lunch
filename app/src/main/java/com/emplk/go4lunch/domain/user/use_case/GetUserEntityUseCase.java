package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserLiveDataUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.GetFavoriteRestaurantsIdsUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

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
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserLiveDataUseCase getCurrentLoggedUserLiveDataUseCase,
        @NonNull GetFavoriteRestaurantsIdsUseCase getFavoriteRestaurantsIdsUseCase
    ) {

        LiveData<String> attendingRestaurantIdLiveData = Transformations.switchMap(getCurrentLoggedUserLiveDataUseCase.invoke(), loggedUserEntity -> {
                return Transformations.map(userRepository.getUserRestaurantChoiceLiveData(loggedUserEntity), restaurantEntity -> {
                        if (restaurantEntity != null) {
                            return restaurantEntity.getAttendingRestaurantId();
                        } else {
                            return null;
                        }
                    }
                );
            }
        );

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
                    getCurrentLoggedUserLiveDataUseCase.invoke().getValue(),
                    favoriteRestaurantIds,
                    attendingRestaurantIdLiveData.getValue()
                );
            }
        );

        userEntityMediatorLiveData.addSource(attendingRestaurantIdLiveData, attendingRestaurantId -> {
                combine(
                    getCurrentLoggedUserLiveDataUseCase.invoke().getValue(),
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
        @Nullable String attendingRestaurantId
    ) {
        if (loggedUserEntity == null) {
            return;
        }

        if (favoriteRestaurantsIds != null && attendingRestaurantId != null) {
            userEntityMediatorLiveData.setValue(
                new UserEntity(
                    loggedUserEntity,
                    new HashSet<>(favoriteRestaurantsIds),
                    attendingRestaurantId
                )
            );
        } else if (favoriteRestaurantsIds == null && attendingRestaurantId != null) {
            userEntityMediatorLiveData.setValue(
                new UserEntity(
                    loggedUserEntity,
                    new HashSet<>(),
                    attendingRestaurantId
                )
            );
        } else {
            userEntityMediatorLiveData.setValue(
                new UserEntity(
                    loggedUserEntity,
                    new HashSet<>(),
                    null
                )
            );
        }
    }

}