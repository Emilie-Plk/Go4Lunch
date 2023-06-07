package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

public interface UserRepository {

    void upsertLoggedUserEntity(@Nullable LoggedUserEntity userEntity);

    LiveData<LoggedUserEntity> getLoggedUserEntityLiveData(@NonNull String userId);
}
