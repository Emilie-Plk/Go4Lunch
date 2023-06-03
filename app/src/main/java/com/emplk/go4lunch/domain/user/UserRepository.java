package com.emplk.go4lunch.domain.user;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface UserRepository {
    LiveData<UserEntity> getUser(@Nullable UserEntity userEntity);

    void createUser(@Nullable UserEntity userEntity);
}
