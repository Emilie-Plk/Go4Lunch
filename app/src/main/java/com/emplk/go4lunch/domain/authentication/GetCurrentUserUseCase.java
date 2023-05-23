package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetCurrentUserUseCase {
    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public GetCurrentUserUseCase(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Nullable
    public LiveData<LoggedUserEntity> invoke() {
        return authRepository.getCurrentUserLiveData();
    }
}
