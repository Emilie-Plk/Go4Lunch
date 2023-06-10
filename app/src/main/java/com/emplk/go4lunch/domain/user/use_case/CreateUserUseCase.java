package com.emplk.go4lunch.domain.user.use_case;

import android.util.Log;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
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
            userRepository.upsertLoggedUserEntity(
                    new LoggedUserEntity(
                        loggedUserEntity.getId(),
                        loggedUserEntity.getEmail(),
                        loggedUserEntity.getName(),
                        loggedUserEntity.getPictureUrl()
                    )
            );
        } else {
            Log.e("CreateUserUseCase", "Error while getting current user");
        }
    }
}
