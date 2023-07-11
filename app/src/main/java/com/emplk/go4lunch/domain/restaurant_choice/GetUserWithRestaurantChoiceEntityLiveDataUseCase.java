package com.emplk.go4lunch.domain.restaurant_choice;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;

import javax.inject.Inject;

public class GetUserWithRestaurantChoiceEntityLiveDataUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public GetUserWithRestaurantChoiceEntityLiveDataUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase,
        @NonNull AuthRepository authRepository
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
        this.authRepository = authRepository;
    }

    public LiveData<UserWithRestaurantChoiceEntity> invoke() {
        return Transformations.switchMap(authRepository.getLoggedUserLiveData(), user ->
            userRepository.getUserWithRestaurantChoiceEntity(user.getId()
            )
        );
    }
}
