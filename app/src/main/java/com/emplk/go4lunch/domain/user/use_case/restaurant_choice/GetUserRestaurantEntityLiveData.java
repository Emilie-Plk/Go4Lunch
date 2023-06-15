package com.emplk.go4lunch.domain.user.use_case.restaurant_choice;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.user.ChosenRestaurantEntity;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;

import javax.inject.Inject;

public class GetUserRestaurantEntityLiveData {

    @NonNull
    private final GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase;

    @Inject
    public GetUserRestaurantEntityLiveData(
        @NonNull GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase
    ) {
        this.getUserWithRestaurantChoiceEntityLiveDataUseCase = getUserWithRestaurantChoiceEntityLiveDataUseCase;
    }

    public LiveData<ChosenRestaurantEntity> invoke() {
        LiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityLiveData =
            getUserWithRestaurantChoiceEntityLiveDataUseCase.invoke();

        return Transformations.switchMap(userWithRestaurantChoiceEntityLiveData, userWithRestaurantChoiceEntity -> {
                MutableLiveData<ChosenRestaurantEntity> result = new MutableLiveData<>();
                if (userWithRestaurantChoiceEntity != null) {
                    result.setValue(new ChosenRestaurantEntity(
                            userWithRestaurantChoiceEntity.getAttendingRestaurantId(),
                            userWithRestaurantChoiceEntity.getAttendingRestaurantName(),
                            userWithRestaurantChoiceEntity.getAttendingRestaurantVicinity(),
                            userWithRestaurantChoiceEntity.getAttendingRestaurantPictureUrl()
                        )
                    );
                }
                return result;
            }
        );
    }
}
