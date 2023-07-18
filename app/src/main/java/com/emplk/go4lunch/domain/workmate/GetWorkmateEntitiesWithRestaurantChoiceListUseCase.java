package com.emplk.go4lunch.domain.workmate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetWorkmateEntitiesWithRestaurantChoiceListUseCase {

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    private final String currentLoggedUserId;

    MediatorLiveData<List<WorkmateEntity>> workmateEntitiesWithRestaurantChoiceLiveData = new MediatorLiveData<>();

    @Inject
    public GetWorkmateEntitiesWithRestaurantChoiceListUseCase(
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase,
        @NonNull GetLoggedUserEntitiesUseCase getLoggedUserEntitiesUseCase
    ) {
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;

        LiveData<List<LoggedUserEntity>> loggedUserEntitiesLiveData = getLoggedUserEntitiesUseCase.invoke();
        LiveData<List<UserWithRestaurantChoiceEntity>> userWithRestaurantChoiceEntitiesLiveData = userRepository.getUsersWithRestaurantChoiceEntities();
        currentLoggedUserId = getCurrentLoggedUserIdUseCase.invoke();

        workmateEntitiesWithRestaurantChoiceLiveData.addSource(loggedUserEntitiesLiveData, loggedUserEntities -> {
                combine(loggedUserEntities, userWithRestaurantChoiceEntitiesLiveData.getValue());
            }
        );

        workmateEntitiesWithRestaurantChoiceLiveData.addSource(userWithRestaurantChoiceEntitiesLiveData, userWithRestaurantChoiceEntities -> {
                combine(loggedUserEntitiesLiveData.getValue(), userWithRestaurantChoiceEntities);
            }
        );
    }

    public LiveData<List<WorkmateEntity>> invoke() {
        return workmateEntitiesWithRestaurantChoiceLiveData;
    }

    private void combine(
        @Nullable List<LoggedUserEntity> loggedUserEntities,
        @Nullable List<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntities
    ) {
        if (loggedUserEntities == null || userWithRestaurantChoiceEntities == null) {
            return;
        }

        List<WorkmateEntity> workmateEntitiesWithRestaurantChoiceList = new ArrayList<>();
        for (UserWithRestaurantChoiceEntity userWithRestaurantChoice : userWithRestaurantChoiceEntities) {
            for (LoggedUserEntity loggedUserEntity : loggedUserEntities) {
                if (userWithRestaurantChoice.getId().equals(loggedUserEntity.getId()) && !userWithRestaurantChoice.getId().equals(currentLoggedUserId)) {
                    workmateEntitiesWithRestaurantChoiceList.add(
                        new WorkmateEntity(
                            new LoggedUserEntity(
                                loggedUserEntity.getId(),
                                loggedUserEntity.getName(),
                                loggedUserEntity.getEmail(),
                                loggedUserEntity.getPictureUrl()
                            ),
                            userWithRestaurantChoice.getAttendingRestaurantId(),
                            userWithRestaurantChoice.getAttendingRestaurantName(),
                            userWithRestaurantChoice.getAttendingRestaurantVicinity()
                        )
                    );
                }
            }
        }
        workmateEntitiesWithRestaurantChoiceLiveData.setValue(workmateEntitiesWithRestaurantChoiceList);
    }
}

