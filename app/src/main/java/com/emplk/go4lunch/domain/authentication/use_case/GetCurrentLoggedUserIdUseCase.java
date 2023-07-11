package com.emplk.go4lunch.domain.authentication.use_case;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.AuthRepository;

import javax.inject.Inject;

public class GetCurrentLoggedUserIdUseCase {
    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public GetCurrentLoggedUserIdUseCase(
        @NonNull AuthRepository authRepository
    ) {
        this.authRepository = authRepository;
    }

    @NonNull
    public String invoke() {
        String currentUserId = authRepository.getCurrentLoggedUserId();
        if (currentUserId != null) {
            return currentUserId;
        } else {
            throw new IllegalStateException("User is not logged in");
        }
    }
}
