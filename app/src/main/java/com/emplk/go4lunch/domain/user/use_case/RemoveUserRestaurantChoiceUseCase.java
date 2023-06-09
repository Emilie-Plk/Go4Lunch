package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class RemoveUserRestaurantChoiceUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public RemoveUserRestaurantChoiceUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public void invoke() {
        userRepository.deleteUserRestaurantChoice(
            getCurrentLoggedUserUseCase.invoke()
        );
    }
}
