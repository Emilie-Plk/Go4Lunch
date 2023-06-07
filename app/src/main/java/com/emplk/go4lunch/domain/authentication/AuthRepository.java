package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface AuthRepository {

    @Nullable
    LoggedUserEntity getCurrentLoggedUser();

    @Nullable
    String getCurrentLoggedUserId();

    LiveData<Boolean> isUserLoggedLiveData();

    void signOut();
}
