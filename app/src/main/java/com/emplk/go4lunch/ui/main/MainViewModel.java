package com.emplk.go4lunch.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.GetCurrentUserUseCase;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.LogoutUserUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @NonNull
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    @NonNull
    private final LogoutUserUseCase logoutUserUseCase;

    @NonNull
    private final StartLocationRequestUseCase startLocationRequestUseCase;

    @Inject
    public MainViewModel(
        @NonNull GetCurrentUserUseCase getCurrentUserUseCase,
        @NonNull LogoutUserUseCase logoutUserUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
        this.startLocationRequestUseCase = startLocationRequestUseCase;
    }

    public LiveData<LoggedUserEntity> getCurrentUserLiveData() {
        return getCurrentUserUseCase.invoke();
    }

    public void signOut() {
        logoutUserUseCase.invoke();
    }

    public void startLocationRequest() {
        startLocationRequestUseCase.invoke();
    }
}

