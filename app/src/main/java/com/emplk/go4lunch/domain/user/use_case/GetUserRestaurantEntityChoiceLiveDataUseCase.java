package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.RestaurantEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class GetUserRestaurantEntityChoiceLiveDataUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public GetUserRestaurantEntityChoiceLiveDataUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public LiveData<RestaurantEntity> invoke() {
        return userRepository.getUserRestaurantChoiceLiveData(getCurrentLoggedUserUseCase.invoke());
    }
}
