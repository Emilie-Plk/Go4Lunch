package com.emplk.go4lunch.ui.workmate_list;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.workmate.GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class WorkmatesViewModel extends ViewModel {

    @NonNull
    private final GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Inject
    public WorkmatesViewModel(
        @NonNull GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase = getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
    }

    public LiveData<List<WorkmatesViewStateItem>> getWorkmates() {
        LoggedUserEntity currentLoggedUser = getCurrentLoggedUserUseCase.invoke();
        if (currentLoggedUser != null) {
            String currentUserId = currentLoggedUser.getId();
            return Transformations.switchMap(getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase.invoke(), workmateEntities -> {
                    List<WorkmatesViewStateItem> workmatesViewStateItems = new ArrayList<>();
                    MutableLiveData<List<WorkmatesViewStateItem>> result = new MutableLiveData<>();
                    for (WorkmateEntity workmate : workmateEntities) {
                        if (!workmate.getLoggedUserEntity().getId().equals(currentUserId)) {
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
                    }
                    result.setValue(workmatesViewStateItems);
                    return result;
                }
            );
        } else {
            MutableLiveData<List<WorkmatesViewStateItem>> emptyResult = new MutableLiveData<>();
            emptyResult.setValue(Collections.emptyList());
            return emptyResult;
        }
    }
}

