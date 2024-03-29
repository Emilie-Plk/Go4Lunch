package com.emplk.go4lunch.domain.notification;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetNotificationEntityUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public GetNotificationEntityUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    @Nullable
    public NotificationEntity invoke() {
        List<UserWithRestaurantChoiceEntity> usersWithRestaurantChoiceEntities = userRepository.getUsersWithRestaurantChoiceEntitiesAsync();
        String currentLoggedUserId = getCurrentLoggedUserIdUseCase.invoke();

        List<String> workmatesGoingToSameRestaurantAsUser = new ArrayList<>();
        String restaurantId = null;
        String restaurantName = null;
        String restaurantVicinity = null;

        if (usersWithRestaurantChoiceEntities != null) {

            for (UserWithRestaurantChoiceEntity user : usersWithRestaurantChoiceEntities) {
                if (user.getId().equals(currentLoggedUserId)) {
                    restaurantId = user.getAttendingRestaurantId();
                    restaurantName = user.getAttendingRestaurantName();
                    restaurantVicinity = user.getAttendingRestaurantVicinity();

                    for (UserWithRestaurantChoiceEntity workmate : usersWithRestaurantChoiceEntities) {
                        if (!workmate.getId().equals(user.getId()) && workmate.getAttendingRestaurantId().equals(restaurantId)) {
                            workmatesGoingToSameRestaurantAsUser.add(workmate.getAttendingUsername());
                        }
                    }
                }
            }
            if (restaurantId != null) {
                return new NotificationEntity(
                    restaurantName,
                    restaurantId,
                    restaurantVicinity,
                    workmatesGoingToSameRestaurantAsUser
                );
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}

