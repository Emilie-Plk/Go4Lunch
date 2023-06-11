package com.emplk.go4lunch.domain.user.use_case;

import android.util.Log;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
<<<<<<< HEAD
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
=======
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
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
<<<<<<< HEAD
            userRepository.upsertLoggedUserEntity(
                    new LoggedUserEntity(
                        loggedUserEntity.getId(),
                        loggedUserEntity.getEmail(),
                        loggedUserEntity.getName(),
                        loggedUserEntity.getPictureUrl()
                    )
=======
            userRepository.upsertUser(
                new LoggedUserEntity(
                    loggedUserEntity.getUserId(),
                    loggedUserEntity.getEmail(),
                    loggedUserEntity.getUsername(),
                    loggedUserEntity.getPhotoUrl()
                )
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
            );
        } else {
            Log.e("CreateUserUseCase", "Error while getting current user");
        }
    }
}
