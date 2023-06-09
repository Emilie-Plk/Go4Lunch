package com.emplk.go4lunch.domain.user.use_case;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.detail.GetRestaurantEntityUseCase;
import com.emplk.go4lunch.domain.user.RestaurantEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Inject;

public class AddUserRestaurantChoiceUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetRestaurantEntityUseCase getRestaurantEntityUseCase;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public AddUserRestaurantChoiceUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetRestaurantEntityUseCase getRestaurantEntityUseCase,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.userRepository = userRepository;
        this.getRestaurantEntityUseCase = getRestaurantEntityUseCase;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public void invoke(
        @Nullable String restaurantId,
        @Nullable String restaurantName,
        @Nullable String vicinity,
        @Nullable String photoReferenceUrl
    ) {
        if (restaurantId != null &&
            restaurantName != null &&
            vicinity != null &&
            photoReferenceUrl != null
        ) {
            userRepository.upsertUserRestaurantChoice(
                getCurrentLoggedUserUseCase.invoke(),
                new RestaurantEntity(
                    restaurantId,
                    restaurantName,
                    vicinity,
                    photoReferenceUrl
                )
            );
        }
    }
}
