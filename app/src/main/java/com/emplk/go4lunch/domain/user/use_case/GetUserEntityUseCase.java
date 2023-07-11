package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInLiveDataUseCase;
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

    @NonNull
    private final IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @NonNull
    private final AuthRepository authRepository;


    @Inject
    public GetUserEntityUseCase(
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase,
        @NonNull GetFavoriteRestaurantsIdsUseCase getFavoriteRestaurantsIdsUseCase,
        @NonNull GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase,
        @NonNull IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase,
        @NonNull AuthRepository authRepository
    ) {
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
        this.isUserLoggedInLiveDataUseCase = isUserLoggedInLiveDataUseCase;
        this.authRepository = authRepository;
        userEntityMediatorLiveData = new MediatorLiveData<>();

        LiveData<Set<String>> favoriteRestaurantsIdsLiveData = getFavoriteRestaurantsIdsUseCase.invoke();
        LiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityLiveData = getUserWithRestaurantChoiceEntityLiveDataUseCase.invoke();
        LiveData<Boolean> isUserLoggedInLiveData = isUserLoggedInLiveDataUseCase.invoke();

        userEntityMediatorLiveData.addSource(isUserLoggedInLiveData, isUserLoggedIn -> {
                combine(
                    isUserLoggedIn,
                    favoriteRestaurantsIdsLiveData.getValue(),
                    userWithRestaurantChoiceEntityLiveData.getValue(),
                    authRepository.getLoggedUserLiveData().getValue()
                );
            }
        );

        userEntityMediatorLiveData.addSource(favoriteRestaurantsIdsLiveData, favoriteRestaurantIds -> {
                combine(
                    isUserLoggedInLiveData.getValue(),
                    favoriteRestaurantIds,
                    userWithRestaurantChoiceEntityLiveData.getValue(),
                    authRepository.getLoggedUserLiveData().getValue()
                );
            }
        );

        userEntityMediatorLiveData.addSource(userWithRestaurantChoiceEntityLiveData, userWithRestaurantChoice -> {
                combine(
                    isUserLoggedInLiveData.getValue(),
                    favoriteRestaurantsIdsLiveData.getValue(),
                    userWithRestaurantChoice,
                    authRepository.getLoggedUserLiveData().getValue()
                );
            }
        );


        userEntityMediatorLiveData.addSource(authRepository.getLoggedUserLiveData(), currentUser -> {
                combine(
                    isUserLoggedInLiveData.getValue(),
                    favoriteRestaurantsIdsLiveData.getValue(),
                    userWithRestaurantChoiceEntityLiveData.getValue(),
                    currentUser
                );
            }
        );

    }

    public LiveData<UserEntity> invoke() {
        return userEntityMediatorLiveData;
    }


    private void combine(
        @Nullable Boolean isUserLoggedIn,
        @Nullable Set<String> favoriteRestaurantsIds,
        @Nullable UserWithRestaurantChoiceEntity userWithRestaurantChoice,
        @Nullable LoggedUserEntity currentUser
    ) {
        if (isUserLoggedIn == null || currentUser == null) {
            return;
        }

        Set<String> updatedFavoriteRestaurantsIds = new HashSet<>(favoriteRestaurantsIds != null ? favoriteRestaurantsIds : Collections.emptySet());

        userEntityMediatorLiveData.setValue(
            new UserEntity(
                currentUser,
                updatedFavoriteRestaurantsIds,
                userWithRestaurantChoice != null ? userWithRestaurantChoice.getAttendingRestaurantId() : null
            )
        );
    }
}