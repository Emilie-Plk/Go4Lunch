package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
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
