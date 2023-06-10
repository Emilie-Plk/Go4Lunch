package com.emplk.go4lunch.domain.authentication.use_case;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import javax.inject.Inject;

public class GetCurrentLoggedUserIdUseCase {

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public GetCurrentLoggedUserIdUseCase(GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase) {
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public String invoke() {
        LoggedUserEntity loggedUserEntity = getCurrentLoggedUserUseCase.invoke();
        if (loggedUserEntity != null) {
            return loggedUserEntity.getId();
        } else {
            throw new IllegalStateException("User id is null!");
        }
    }
}
