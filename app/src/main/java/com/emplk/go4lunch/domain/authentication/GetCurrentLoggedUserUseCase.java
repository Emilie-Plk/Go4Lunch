package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import javax.inject.Inject;

public class GetCurrentLoggedUserUseCase {
    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public GetCurrentLoggedUserUseCase(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Nullable
    public LoggedUserEntity invoke() {
        return authRepository.getCurrentLoggedUser();
    }
}
