package com.emplk.go4lunch.domain.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserLiveDataUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase {

    @NonNull
    MediatorLiveData<List<WorkmateEntity>> workmateEntitiesWithAndWithoutRestaurantChoiceMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase(
        @NonNull GetWorkmateEntitiesWithRestaurantChoiceListUseCase getWorkmateEntitiesWithRestaurantChoiceListUseCase,
        @NonNull UserRepository userRepository,
        @NonNull GetCurrentLoggedUserLiveDataUseCase getCurrentLoggedUserLiveDataUseCase
    ) {
        LiveData<List<WorkmateEntity>> workmateEntitiesWithRestaurantChoiceLiveData = getWorkmateEntitiesWithRestaurantChoiceListUseCase.invoke();
        LiveData<List<LoggedUserEntity>> allLoggedUserEntitiesLiveData = userRepository.getLoggedUserEntitiesLiveData();
        LiveData<LoggedUserEntity> currentLoggedUserLiveData = getCurrentLoggedUserLiveDataUseCase.invoke();

        workmateEntitiesWithAndWithoutRestaurantChoiceMediatorLiveData.addSource(
            workmateEntitiesWithRestaurantChoiceLiveData, workmateEntitiesWithRestaurantChoice -> {
                combine(workmateEntitiesWithRestaurantChoice, allLoggedUserEntitiesLiveData.getValue(), currentLoggedUserLiveData.getValue());
            }
        );

        workmateEntitiesWithAndWithoutRestaurantChoiceMediatorLiveData.addSource(
            allLoggedUserEntitiesLiveData, allLoggedUserEntities -> {
                combine(workmateEntitiesWithRestaurantChoiceLiveData.getValue(), allLoggedUserEntities, currentLoggedUserLiveData.getValue());
            }
        );

        if (currentLoggedUserLiveData != null) {   //TODO: not sure but how to avoid nullability otherwise?
            workmateEntitiesWithAndWithoutRestaurantChoiceMediatorLiveData.addSource(
                currentLoggedUserLiveData, currentLoggedUser -> {
                    combine(workmateEntitiesWithRestaurantChoiceLiveData.getValue(), allLoggedUserEntitiesLiveData.getValue(), currentLoggedUser);
                }
            );
        }
    }

    public LiveData<List<WorkmateEntity>> invoke() {
        return workmateEntitiesWithAndWithoutRestaurantChoiceMediatorLiveData;
    }

    private void combine(
        @Nullable List<WorkmateEntity> workmateEntitiesWithRestaurantChoice,
        @Nullable List<LoggedUserEntity> allLoggedUserEntities,
        @Nullable LoggedUserEntity currentLoggedUser
    ) {

        if (workmateEntitiesWithRestaurantChoice == null && allLoggedUserEntities == null && currentLoggedUser == null) {
            return;
        }

        if (workmateEntitiesWithRestaurantChoice != null && allLoggedUserEntities != null && currentLoggedUser != null) {
            List<WorkmateEntity> combinedList = new ArrayList<>(workmateEntitiesWithRestaurantChoice);

            for (LoggedUserEntity loggedUserEntity : allLoggedUserEntities) {
                boolean isFound = false;
                for (WorkmateEntity workmateEntity : workmateEntitiesWithRestaurantChoice) {
                    if (workmateEntity.getLoggedUserEntity().getId().equals(loggedUserEntity.getId())) {
                        isFound = true;
                        break;
                    }
                }

                if (!isFound && !loggedUserEntity.getId().equals(currentLoggedUser.getId())) {
                    combinedList.add(
                        new WorkmateEntity(
                            loggedUserEntity,
                            null,
                            null,
                            null,
                            null
                        )
                    );
                }
            }

            workmateEntitiesWithAndWithoutRestaurantChoiceMediatorLiveData.setValue(combinedList);
        }
    }
}
