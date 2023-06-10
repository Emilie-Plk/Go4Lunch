package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.detail.GetDetailsRestaurantWrapperUseCase;
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

    public void invoke(
        @Nullable String restaurantId,
        @Nullable String restaurantName,
        @Nullable String vicinity,
        @Nullable String pictureReferenceUrl
    ) {
        if (restaurantId != null &&
            restaurantName != null &&
            vicinity != null &&
            pictureReferenceUrl != null
        ) {
            userRepository.upsertUserRestaurantChoice(
                getCurrentLoggedUserUseCase.invoke(),
                new RestaurantEntity(
                    restaurantId,
                    restaurantName,
                    vicinity,
                    pictureReferenceUrl
                )
            );
        }
    }
}
