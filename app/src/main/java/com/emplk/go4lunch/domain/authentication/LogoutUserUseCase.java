package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.data.authentication.AuthRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LogoutUserUseCase {
    @NonNull
    private final AuthRepository authenticationRepository;

    @Inject
    public LogoutUserUseCase(@NonNull AuthRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void invoke() {
        authenticationRepository.signOut();
    }
}
