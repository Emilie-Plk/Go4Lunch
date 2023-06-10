package com.emplk.go4lunch.domain.workmate;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import java.util.List;

public interface WorkmateRepository {

    LiveData<List<LoggedUserEntity>> getLoggedUserEntitiesLiveData();

    LiveData<List<WorkmateEntity>> getWorkmateEntitiesWithRestaurantChoiceLiveData();

}
