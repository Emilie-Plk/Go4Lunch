package com.emplk.go4lunch.domain.authentication.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

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
