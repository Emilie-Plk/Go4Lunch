package com.emplk.go4lunch.domain.workmate;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

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
        return Transformations.switchMap(workmateRepository.getWorkmateEntitiesWithRestaurantChoiceLiveData(), workmateEntities -> {
                List<WorkmateEntity> workmateEntitiesWithoutCurrentUserList = new ArrayList<>();
                MutableLiveData<List<WorkmateEntity>> workmateEntitiesWithoutCurrentUserMutableLiveData = new MutableLiveData<>();
                for (WorkmateEntity workmateEntity : workmateEntities) {
                    if (!workmateEntity.getLoggedUserEntity().getId().equals(getCurrentLoggedUserIdUseCase.invoke())) {
                        workmateEntitiesWithoutCurrentUserList.add(workmateEntity);
                    }
                }
                workmateEntitiesWithoutCurrentUserMutableLiveData.setValue(workmateEntitiesWithoutCurrentUserList);
                return workmateEntitiesWithoutCurrentUserMutableLiveData;
            }
        );
    }
}

