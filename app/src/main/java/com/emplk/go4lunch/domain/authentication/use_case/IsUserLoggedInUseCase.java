package com.emplk.go4lunch.domain.authentication.use_case;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.AuthRepository;

import javax.inject.Inject;

public class IsUserLoggedInUseCase {

    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public IsUserLoggedInUseCase(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public LiveData<Boolean> invoke() {
        return authRepository.isUserLoggedLiveData();
    }
}
