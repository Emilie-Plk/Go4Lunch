package com.emplk.go4lunch.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.authentication.FirebaseUserEntity;
import com.emplk.go4lunch.domain.authentication.GetCurrentUserUseCase;
import com.emplk.go4lunch.domain.authentication.LogoutUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @NonNull
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    @NonNull
    private final LogoutUserUseCase logoutUserUseCase;

    @Inject
    public MainViewModel(
        @NonNull GetCurrentUserUseCase getCurrentUserUseCase,
        @NonNull LogoutUserUseCase logoutUserUseCase
    ) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
    }

    public LiveData<FirebaseUserEntity> getCurrentUserLiveData() {
        return getCurrentUserUseCase.invoke();
    }

    public void signOut() {
        logoutUserUseCase.invoke();
    }
}

