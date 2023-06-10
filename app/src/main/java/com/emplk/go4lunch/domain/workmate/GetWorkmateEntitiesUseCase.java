package com.emplk.go4lunch.domain.workmate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetWorkmateEntitiesUseCase {

    @NonNull
    private final WorkmateRepository workmateRepository;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Inject
    public GetWorkmateEntitiesUseCase(
        @NonNull WorkmateRepository workmateRepository,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase
    ) {
        this.workmateRepository = workmateRepository;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
    }

    public LiveData<List<WorkmateEntity>> invoke() {
        LiveData<List<WorkmateEntity>> workmateEntitiesWithRestaurantChoiceLiveData = workmateRepository.getWorkmateEntitiesWithRestaurantChoiceLiveData();

        List<WorkmateEntity> workmateEntityList = new ArrayList<>();
        return Transformations.switchMap(workmateEntitiesWithRestaurantChoiceLiveData, workmateEntities -> {
                MutableLiveData<List<WorkmateEntity>> result = new MutableLiveData<>();
                for (WorkmateEntity workmate : workmateEntities) {
                    if (!workmate.getLoggedUserEntity().getId().equals(getCurrentLoggedUserIdUseCase.invoke())) {
                        workmateEntityList.add(
                            new WorkmateEntity(
                                workmate.getLoggedUserEntity(),
                                workmate.getAttendingRestaurantId(),
                                workmate.getAttendingRestaurantName(),
                                workmate.getAttendingRestaurantVicinity(),
                                workmate.getAttendingRestaurantPictureUrl()
                            )
                        );
                    }
                    result.setValue(workmateEntityList);
                }
                return result;
            }
        );
    }
}
