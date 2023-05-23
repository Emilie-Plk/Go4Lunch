package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.data.authentication.AuthRepository;
import com.emplk.go4lunch.data.authentication.FirebaseUserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GetCurrentUserUseCase {
    @NonNull
    private final AuthRepository authenticationRepository;

    @Inject
    public GetCurrentUserUseCase(@NonNull AuthRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    @Nullable
   public LiveData<FirebaseUserEntity> invoke() {
        return authenticationRepository.getCurrentUserLiveData();
    }
}
