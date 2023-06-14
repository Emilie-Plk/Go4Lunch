package com.emplk.go4lunch.ui.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.user.use_case.UpsertLoggedUserEntityUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginActivityViewModel extends ViewModel {

    @NonNull
    private final UpsertLoggedUserEntityUseCase upsertLoggedUserEntityUseCase;

    @Inject
    public LoginActivityViewModel(@NonNull UpsertLoggedUserEntityUseCase upsertLoggedUserEntityUseCase) {
        this.upsertLoggedUserEntityUseCase = upsertLoggedUserEntityUseCase;
    }

    public void onLoginComplete() {
        upsertLoggedUserEntityUseCase.invoke();
    }
}
