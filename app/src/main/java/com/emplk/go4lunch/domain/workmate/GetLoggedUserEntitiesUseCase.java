package com.emplk.go4lunch.domain.workmate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import java.util.List;

import javax.inject.Inject;

public class GetLoggedUserEntitiesUseCase {

    @NonNull
    private final UserRepository userRepository;

    @Inject
    public GetLoggedUserEntitiesUseCase(
        @NonNull UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }

    public LiveData<List<LoggedUserEntity>> invoke() {
        return userRepository.getLoggedUserEntitiesLiveData();
    }
}
