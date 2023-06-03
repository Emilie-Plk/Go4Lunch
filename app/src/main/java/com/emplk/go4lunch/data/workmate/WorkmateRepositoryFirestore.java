package com.emplk.go4lunch.data.workmate;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.workmate.WorkmateEntity;
import com.emplk.go4lunch.domain.workmate.WorkmateRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class WorkmateRepositoryFirestore implements WorkmateRepository {

    private static final String USERS_COLLECTION = "users";
    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public WorkmateRepositoryFirestore(@NonNull FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public LiveData<List<WorkmateEntity>> getWorkmateList() {
        MutableLiveData<List<WorkmateEntity>> workmateEntityListMutableLiveData = new MutableLiveData<>();

        firestore.collection(USERS_COLLECTION)
            .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        List<WorkmateDto> workmateDtoList = value.toObjects(WorkmateDto.class);
                        List<WorkmateEntity> workmateEntityList = mapToWorkmateEntityList(workmateDtoList);
                        workmateEntityListMutableLiveData.setValue(workmateEntityList);
                    } else {
                        // TODO: Handle case where value is null
                        workmateEntityListMutableLiveData.setValue(new ArrayList<>());
                    }
                }
            );

        return workmateEntityListMutableLiveData;
    }


    private List<WorkmateEntity> mapToWorkmateEntityList(List<WorkmateDto> workmateDtoList) {
        List<WorkmateEntity> workmateEntityList = new ArrayList<>();

        for (WorkmateDto workmateDto : workmateDtoList) {
            String id = workmateDto.getUserId() != null ? workmateDto.getUserId() : "";
            String displayName = workmateDto.getUsername() != null ? workmateDto.getUsername() : "";
            String pictureUrl = workmateDto.getPictureUrl() != null ? workmateDto.getPictureUrl() : null;

            WorkmateEntity workmateEntity =
                new WorkmateEntity(
                    id,
                    displayName,
                    pictureUrl
                );
            workmateEntityList.add(workmateEntity);
        }
        return workmateEntityList;
    }
}
