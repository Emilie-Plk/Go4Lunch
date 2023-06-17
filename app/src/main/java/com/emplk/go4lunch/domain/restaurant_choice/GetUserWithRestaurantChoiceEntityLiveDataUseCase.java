package com.emplk.go4lunch.domain.restaurant_choice;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;

import javax.inject.Inject;

public class GetUserWithRestaurantChoiceEntityLiveDataUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public GetUserWithRestaurantChoiceEntityLiveDataUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public LiveData<UserWithRestaurantChoiceEntity> invoke() {
        LoggedUserEntity loggedUserEntity = getCurrentLoggedUserUseCase.invoke();
        if (loggedUserEntity != null) {
            return userRepository.getUserWithRestaurantChoiceEntity(loggedUserEntity.getId());
        }
        return null;
    }
}
