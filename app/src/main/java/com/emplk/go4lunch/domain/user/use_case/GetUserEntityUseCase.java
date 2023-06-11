<<<<<<< HEAD
package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.GetFavoriteRestaurantsIdsUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import java.util.Set;

import javax.inject.Inject;

public class GetUserEntityUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @NonNull
    private final GetFavoriteRestaurantsIdsUseCase getFavoriteRestaurantsIdsUseCase;

    @Inject
    public GetUserEntityUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase,
        @NonNull GetFavoriteRestaurantsIdsUseCase getFavoriteRestaurantsIdsUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
        this.getFavoriteRestaurantsIdsUseCase = getFavoriteRestaurantsIdsUseCase;
    }

    public LiveData<UserEntity> invoke() {
        String userId = getCurrentLoggedUserIdUseCase.invoke();

        if (userId != null) {

            //   return userRepository.getLoggedUserEntityLiveData();

            // user id + contre cet id + whereEqualTo sur ma collec pour récup mes restau fav + où je vais manger (récup 1 LV pour en combiner 3)
            // ça va combiner sec lol MediatorLiveData + switchMap
        }
        return null;
    }

    private void combine(
        Set<String> favoriteRestaurantSet,
        String attendingRestaurantId
    ) {
    }
}
=======
package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class GetUserEntityUseCase {

    @NonNull
    private final UserRepository userRepository;

    @Inject
    public GetUserEntityUseCase(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserEntity> invoke() {
        // TODO Combine les 4 LiveData pour générer le UserEntity
        return userRepository.getUserEntityLiveData();
    }
}
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
