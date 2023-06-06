package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.Nullable;

public interface AuthRepository {

    @Nullable
    LoggedUserEntity getCurrentLoggedUser();

    void signOut();
}
