package com.emplk.go4lunch.domain.authentication.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import javax.inject.Inject;

public class GetCurrentLoggedUserLiveDataUseCase {
    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public GetCurrentLoggedUserLiveDataUseCase(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Nullable
    public LiveData<LoggedUserEntity> invoke() {
        MutableLiveData<LoggedUserEntity> loggedUserEntityMutableLiveData = new MutableLiveData<>();
        loggedUserEntityMutableLiveData.setValue(authRepository.getCurrentLoggedUser());
        return loggedUserEntityMutableLiveData;
    }
}
