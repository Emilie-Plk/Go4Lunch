package com.emplk.go4lunch.domain.workmate;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.user.UserEntity;

import java.util.List;

public interface WorkmateRepository {

    LiveData<List<WorkmateEntity>> getWorkmateList();
}
