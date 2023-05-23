package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.data.authentication.AuthRepositoryImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LogoutUserUseCase {
    @NonNull
    private final AuthRepositoryImpl authenticationRepository;

    @Inject
    public LogoutUserUseCase(@NonNull AuthRepositoryImpl authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void invoke() {
        authenticationRepository.signOut();
    }
}
