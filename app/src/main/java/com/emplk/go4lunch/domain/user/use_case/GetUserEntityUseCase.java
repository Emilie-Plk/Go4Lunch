package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class GetUserEntityUseCase {

    @NonNull
    private final UserRepository userRepository;

    @Inject
    public GetUserEntityUseCase(@NonNull UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<UserEntity> invoke() {
        // TODO Combine les 4 LiveData pour générer le UserEntity
        return userRepository.getUserEntityLiveData();
    }
}
