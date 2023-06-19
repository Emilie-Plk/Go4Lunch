package com.emplk.go4lunch.domain.workmate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

public class GetAttendantsByRestaurantIdsUseCase {

    @NonNull
    private final UserRepository userRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public GetAttendantsByRestaurantIdsUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.userRepository = userRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public LiveData<Map<String, Integer>> invoke() {
        LiveData<List<UserWithRestaurantChoiceEntity>> userWithRestaurantChoiceEntitiesLiveData = userRepository.getUserWithRestaurantChoiceEntities();

        return Transformations.map(userWithRestaurantChoiceEntitiesLiveData, userWithRestaurantChoiceEntities -> {
                Map<String, Integer> attendantsByRestaurantIdsMap = new HashMap<>();
                String currentUserId = getCurrentLoggedUserIdUseCase.invoke();
                for (UserWithRestaurantChoiceEntity userWithRestaurantChoice : userWithRestaurantChoiceEntities) {
                    if (!userWithRestaurantChoice.getId().equals(currentUserId)) {
                        String restaurantId = userWithRestaurantChoice.getAttendingRestaurantId();
                        Integer count = attendantsByRestaurantIdsMap.get(restaurantId);
                        int totalCount = count != null ? count : 0;
                        attendantsByRestaurantIdsMap.put(restaurantId, totalCount + 1);
                    }
                }
                return attendantsByRestaurantIdsMap;
            }
        );
    }
}
