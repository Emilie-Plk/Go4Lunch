package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface AuthRepository {

    @Nullable
    LiveData<LoggedUserEntity> getCurrentUserLiveData();

    void signOut();
}
