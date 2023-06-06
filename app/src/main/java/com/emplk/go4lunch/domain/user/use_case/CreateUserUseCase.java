package com.emplk.go4lunch.domain.user.use_case;

import android.util.Log;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class CreateUserUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public CreateUserUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public void invoke() {
        LoggedUserEntity loggedUserEntity = getCurrentLoggedUserUseCase.invoke();
        if (loggedUserEntity != null) {
            userRepository.createUser(
                new UserEntity(
                    new LoggedUserEntity(
                        loggedUserEntity.getUserId(),
                        loggedUserEntity.getEmail(),
                        loggedUserEntity.getUsername(),
                        loggedUserEntity.getPhotoUrl()
                    ),
                    null  // TODO: how to retrieve correctly this value?)
                )
            );
        } else {
            Log.e("CreateUserUseCase", "Error while getting current user");
        }
    }
}
