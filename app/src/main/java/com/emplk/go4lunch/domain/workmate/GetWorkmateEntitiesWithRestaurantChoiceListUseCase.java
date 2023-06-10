package com.emplk.go4lunch.domain.workmate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetWorkmateEntitiesWithRestaurantChoiceListUseCase {

    @NonNull
    private final WorkmateRepository workmateRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public GetWorkmateEntitiesWithRestaurantChoiceListUseCase(
        @NonNull WorkmateRepository workmateRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.workmateRepository = workmateRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public LiveData<List<WorkmateEntity>> invoke() {
        LiveData<List<LoggedUserEntity>> loggedUserEntityListLiveData = workmateRepository.getLoggedUserEntitiesWithRestaurantChoiceLiveData();
        List<WorkmateEntity> workmateEntityList = new ArrayList<>();
        return Transformations.switchMap(loggedUserEntityListLiveData, loggedUserEntities -> {
                MutableLiveData<List<WorkmateEntity>> result = new MutableLiveData<>();
                for (LoggedUserEntity loggedUserEntity : loggedUserEntities) {
                    if (!loggedUserEntity.getId().equals(getCurrentLoggedUserIdUseCase.invoke())) {
                        workmateEntityList.add(new WorkmateEntity(loggedUserEntity, null));
                    }
                    result.setValue(workmateEntityList);
                }
                return result;
            }
        );
    }
}

