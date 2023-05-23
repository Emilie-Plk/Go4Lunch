package com.emplk.go4lunch.domain.authentication;

import androidx.lifecycle.LiveData;

public interface AuthRepository {

    LiveData<LoggedUserEntity> getCurrentUserLiveData();

    void signOut();
}
