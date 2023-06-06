package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.user.use_case.GetUserInfoUseCase;

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
