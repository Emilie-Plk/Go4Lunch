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
        // TODO: make sure I get userid non null: je travaille pas si pas d'id

     //   return userRepository.getLoggedUserEntityLiveData();
return null;
        // user id + contre cet id + whereEqualTo sur ma collec pour récup mes restau fav + où je vais manger (récup 1 LV pour en combiner 3)
        // ça va combiner sec lol MediatorLiveData + switchMap
    }
}
