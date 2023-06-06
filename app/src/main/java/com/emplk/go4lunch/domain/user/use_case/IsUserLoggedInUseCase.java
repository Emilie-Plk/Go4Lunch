package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class IsUserLoggedInUseCase {
    @NonNull
    private final AuthRepository authRepository;

    @Inject
    public IsUserLoggedInUseCase(
        @NonNull AuthRepository authRepository
    ) {
        this.authRepository = authRepository;
    }

    public LiveData<Boolean> invoke() {
        MutableLiveData<Boolean> isUserLoggedIn = new MutableLiveData<>();

        LoggedUserEntity currentLoggedUser = authRepository.getCurrentLoggedUser();
        if (currentLoggedUser != null) {
            isUserLoggedIn.setValue(true);
        } else {
            isUserLoggedIn.setValue(false);
        }
        return isUserLoggedIn;
    }
}
