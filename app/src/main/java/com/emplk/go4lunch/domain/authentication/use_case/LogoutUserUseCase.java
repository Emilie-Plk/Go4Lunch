package com.emplk.go4lunch.domain.authentication.use_case;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.AuthRepository;

import javax.inject.Inject;

public class LogoutUserUseCase {
    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public LogoutUserUseCase(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public void invoke() {
        authRepository.signOut();
    }
}
