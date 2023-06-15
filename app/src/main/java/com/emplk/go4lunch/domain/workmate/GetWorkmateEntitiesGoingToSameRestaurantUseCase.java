package com.emplk.go4lunch.domain.workmate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class GetWorkmateEntitiesGoingToSameRestaurantUseCase {

    @NonNull
    private final GetWorkmateEntitiesWithRestaurantChoiceListUseCase getWorkmateEntitiesWithRestaurantChoiceListUseCase;

    @Inject
    public GetWorkmateEntitiesGoingToSameRestaurantUseCase(@NonNull GetWorkmateEntitiesWithRestaurantChoiceListUseCase getWorkmateEntitiesWithRestaurantChoiceListUseCase) {
        this.getWorkmateEntitiesWithRestaurantChoiceListUseCase = getWorkmateEntitiesWithRestaurantChoiceListUseCase;
    }

    public LiveData<List<WorkmateEntity>> invoke(@NonNull String restaurantId) {
        LiveData<List<WorkmateEntity>> workmateEntitiesLiveData = getWorkmateEntitiesWithRestaurantChoiceListUseCase.invoke();
        return Transformations.map(workmateEntitiesLiveData, workmateEntities -> {
                List<WorkmateEntity> workmateEntitiesGoingToSameRestaurantList = new ArrayList<>();
                for (WorkmateEntity workmateEntity : workmateEntities) {
                    if (workmateEntity.getAttendingRestaurantId() != null &&
                        workmateEntity.getAttendingRestaurantId().equals(restaurantId)
                    ) {
                        workmateEntitiesGoingToSameRestaurantList.add(workmateEntity);
                    }
                }
                return workmateEntitiesGoingToSameRestaurantList;
            }
        );
    }
}
