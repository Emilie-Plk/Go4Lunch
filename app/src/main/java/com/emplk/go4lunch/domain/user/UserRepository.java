package com.emplk.go4lunch.domain.user;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.workmate.WorkmateEntity;

import java.util.List;

public interface UserRepository {

    void createUser(@Nullable UserEntity userEntity);

    LiveData<UserEntity> getUser();

    void updateUser();

}
