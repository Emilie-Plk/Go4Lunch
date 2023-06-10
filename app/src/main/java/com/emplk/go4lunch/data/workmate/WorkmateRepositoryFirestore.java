package com.emplk.go4lunch.data.workmate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.workmate.WorkmateRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WorkmateRepositoryFirestore implements WorkmateRepository {

    private static final String USERS_WITH_RESTAURANT_CHOICE = "usersWithRestaurantChoice";

    private static final String USERS = "users";

    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public WorkmateRepositoryFirestore(@NonNull FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public LiveData<List<LoggedUserEntity>> getLoggedUserEntitiesLiveData() {
        MutableLiveData<List<LoggedUserEntity>> loggedUserEntityListMutableLiveData = new MutableLiveData<>();

        firestore.collection(USERS)
            .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        List<WorkmateDto> workmateDtoList = value.toObjects(WorkmateDto.class);
                        List<LoggedUserEntity> loggedUserEntityList = mapToWorkmateEntityList(workmateDtoList);
                        loggedUserEntityListMutableLiveData.setValue(loggedUserEntityList);
                    } else {
                        // TODO: Handle case where value is null
                        loggedUserEntityListMutableLiveData.setValue(new ArrayList<>());
                    }
                }
            );
        return loggedUserEntityListMutableLiveData;
    }

    @Override
    public LiveData<List<LoggedUserEntity>> getLoggedUserEntitiesWithRestaurantChoiceLiveData() {
        MutableLiveData<List<LoggedUserEntity>> loggedUserEntityListMutableLiveData = new MutableLiveData<>();

        firestore.collection(USERS_WITH_RESTAURANT_CHOICE)
            .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        List<WorkmateDto> workmateDtoList = value.toObjects(WorkmateDto.class);
                        List<LoggedUserEntity> loggedUserEntityList = mapToWorkmateEntityList(workmateDtoList);
                        loggedUserEntityListMutableLiveData.setValue(loggedUserEntityList);
                    } else {
                        // TODO: Handle case where value is null
                        loggedUserEntityListMutableLiveData.setValue(new ArrayList<>());
                    }
                }
            );
        return loggedUserEntityListMutableLiveData;
    }


    private List<LoggedUserEntity> mapToWorkmateEntityList(List<WorkmateDto> workmateDtoList) {
        List<LoggedUserEntity> loggedUserEntityList = new ArrayList<>();

        for (WorkmateDto workmateDto : workmateDtoList) {
            String errorPlaceholder = String.valueOf(R.string.error_placeholder);
            String id = workmateDto.getId() != null ? workmateDto.getId() : errorPlaceholder;
            String userName = workmateDto.getName() != null ? workmateDto.getName() : errorPlaceholder;
            String email = workmateDto.getEmail() != null ? workmateDto.getEmail() : errorPlaceholder;
            String pictureUrl = workmateDto.getPictureUrl() != null ? workmateDto.getPictureUrl() : null;

            LoggedUserEntity loggedUserEntity =
                new LoggedUserEntity(
                    id,
                    userName,
                    email,
                    pictureUrl
                );
            loggedUserEntityList.add(loggedUserEntity);
        }
        return loggedUserEntityList;
    }
}
