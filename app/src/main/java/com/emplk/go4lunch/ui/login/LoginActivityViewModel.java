package com.emplk.go4lunch.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.user.use_case.AddLoggedUserEntityUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginActivityViewModel extends ViewModel {

    @NonNull
    private final AddLoggedUserEntityUseCase addLoggedUserEntityUseCase;

    @Inject
    public LoginActivityViewModel(@NonNull AddLoggedUserEntityUseCase addLoggedUserEntityUseCase) {
        this.addLoggedUserEntityUseCase = addLoggedUserEntityUseCase;
    }

    public void onLoginComplete() {
        addLoggedUserEntityUseCase.invoke();
    }
}
