package com.emplk.go4lunch.domain.restaurant_choice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.ChosenRestaurantEntity;
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

    public void invoke(
        @Nullable String restaurantId,
        @Nullable String restaurantName,
        @Nullable String vicinity,
        @Nullable String pictureReferenceUrl
    ) {
        if (restaurantId != null &&
            restaurantName != null &&
            vicinity != null
        ) {
            userRepository.upsertUserRestaurantChoice(
                getCurrentLoggedUserUseCase.invoke(),
                new ChosenRestaurantEntity(
                    restaurantId,
                    restaurantName,
                    vicinity,
                    pictureReferenceUrl
                )
            );
        }
    }
}
