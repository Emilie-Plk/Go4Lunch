package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.data.authentication.AuthRepositoryImpl;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetCurrentUserUseCase {
    @NonNull
    private final AuthRepositoryImpl authenticationRepository;

    @Inject
    public GetCurrentUserUseCase(@NonNull AuthRepositoryImpl authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Nullable
   public LiveData<LoggedUserEntity> invoke() {
        return authenticationRepository.getCurrentUserLiveData();
    }
}
