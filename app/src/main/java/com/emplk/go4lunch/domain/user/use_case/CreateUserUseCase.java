package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.GetCurrentUserUseCase;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class CreateUserUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    @Inject
    public CreateUserUseCase(@NonNull UserRepository userRepository,
                             @NonNull GetCurrentUserUseCase getCurrentUserUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public void invoke() {
        userRepository.createUser(
            new UserEntity(
                new LoggedUserEntity(
                    getCurrentUserUseCase.invoke()
                )
            )
        );
    }
}
