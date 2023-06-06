package com.emplk.go4lunch.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.user.use_case.CreateUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginActivityViewModel extends ViewModel {

    @NonNull
    private final CreateUserUseCase createUserUseCase;

    @Inject
    public LoginActivityViewModel(@NonNull CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    public void onLoginComplete() {
        createUserUseCase.invoke();
    }
}
