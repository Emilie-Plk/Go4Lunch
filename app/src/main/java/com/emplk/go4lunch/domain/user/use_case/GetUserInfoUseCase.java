package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class GetUserInfoUseCase {

    @NonNull
    private final UserRepository userRepository;

    @Inject
    public GetUserInfoUseCase(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserEntity> invoke() {
        return userRepository.getUserEntityLiveData();
    }
}
