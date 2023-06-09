package com.emplk.go4lunch.domain.user.use_case;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.RestaurantEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class AddUserRestaurantChoiceUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public AddUserRestaurantChoiceUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public void invoke(@Nullable RestaurantEntity restaurantEntity) {
        if (restaurantEntity != null) {
            userRepository.addUserRestaurantChoice(
                getCurrentLoggedUserUseCase.invoke(),
                restaurantEntity
            );
        } else {
            Log.e("AddUserRestaurantChoice", "RestaurantEntity is null!");
        }
    }
}
