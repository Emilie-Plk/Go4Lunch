package com.emplk.go4lunch.ui.workmate_list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.workmate.GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.workmate.GetWorkmateEntitiesWithRestaurantChoiceListUseCase;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WorkmatesViewModel extends ViewModel {

    @NonNull
    private final GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase;

    @Inject
    public WorkmatesViewModel(@NonNull GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase) {
        this.getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase = getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase;
    }


    public LiveData<List<WorkmatesViewStateItem>> getWorkmates() {
        return Transformations.switchMap(getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase.invoke(), workmateEntities -> {
                List<WorkmatesViewStateItem> workmatesViewStateItems = new ArrayList<>();
                MutableLiveData<List<WorkmatesViewStateItem>> result = new MutableLiveData<>();
                for (WorkmateEntity workmate : workmateEntities) {
                    workmatesViewStateItems.add(
                        new WorkmatesViewStateItem.AllWorkmates(
                            workmate.getLoggedUserEntity().getId(),
                            workmate.getLoggedUserEntity().getName(),
                            workmate.getLoggedUserEntity().getPictureUrl(),
                            workmate.getAttendingRestaurantId() != null ? workmate.getAttendingRestaurantId() : null,
                            workmate.getAttendingRestaurantName() != null ? workmate.getAttendingRestaurantName() : null
                        )
                    );
                }
                result.setValue(workmatesViewStateItems);
                return result;
            }
        );
    }
}

