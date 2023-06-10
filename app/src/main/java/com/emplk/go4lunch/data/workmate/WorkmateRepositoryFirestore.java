package com.emplk.go4lunch.data.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.data.user.LoggedUserDto;
import com.emplk.go4lunch.data.user.UserWithRestaurantChoiceDto;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;
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

        firestore
            .collection(USERS)
            .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        List<LoggedUserDto> loggedUserDtoList = value.toObjects(LoggedUserDto.class);
                        List<LoggedUserEntity> loggedUserEntityList = mapToLoggedUserEntity(loggedUserDtoList);
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
    public LiveData<List<WorkmateEntity>> getWorkmateEntitiesWithRestaurantChoiceLiveData() {
        MutableLiveData<List<WorkmateEntity>> workmateEntityListMutableLiveData = new MutableLiveData<>();

        firestore
            .collection(USERS_WITH_RESTAURANT_CHOICE)
            .addSnapshotListener((value, error) -> {
                    if (value != null) {
                        List<UserWithRestaurantChoiceDto> userWithRestaurantChoiceDtoList = value.toObjects(UserWithRestaurantChoiceDto.class);
                        List<WorkmateEntity> workmateEntityList = mapToWorkmateEntityList(userWithRestaurantChoiceDtoList);
                        workmateEntityListMutableLiveData.setValue(workmateEntityList);
                    } else {
                        // TODO: Handle case where value is null
                        workmateEntityListMutableLiveData.setValue(new ArrayList<>());
                    }
                }
            );
        return workmateEntityListMutableLiveData;
    }


    private List<LoggedUserEntity> mapToLoggedUserEntity(@Nullable List<LoggedUserDto> loggedUserDtoList) {
        List<LoggedUserEntity> loggedUserEntityList = new ArrayList<>();

        String errorPlaceholder = String.valueOf(R.string.error_placeholder);
        if (loggedUserDtoList != null) {
            for (LoggedUserDto loggedUserDto : loggedUserDtoList) {
                LoggedUserEntity loggedUserEntity = new LoggedUserEntity(
                    loggedUserDto.getId() != null ? loggedUserDto.getId() : errorPlaceholder,
                    loggedUserDto.getName() != null ? loggedUserDto.getName() : errorPlaceholder,
                    loggedUserDto.getPictureUrl() != null ? loggedUserDto.getPictureUrl() : errorPlaceholder,
                    loggedUserDto.getEmail() != null ? loggedUserDto.getEmail() : errorPlaceholder
                );
                loggedUserEntityList.add(loggedUserEntity);
            }
        }
        return loggedUserEntityList;
    }


    private List<WorkmateEntity> mapToWorkmateEntityList(List<UserWithRestaurantChoiceDto> userWithRestaurantChoiceDtos) {
        List<WorkmateEntity> workmateEntityList = new ArrayList<>();

        for (UserWithRestaurantChoiceDto userWithRestaurantChoiceDto : userWithRestaurantChoiceDtos) {
            String errorPlaceholder = String.valueOf(R.string.error_placeholder);

            LoggedUserEntity loggedUserEntity;
            String attendingRestaurantId;
            String attendingRestaurantName;
            String attendingRestaurantVicinity;
            String attendingRestaurantPictureUrl;

            if (userWithRestaurantChoiceDto.getLoggedUser() != null) {
                loggedUserEntity = new LoggedUserEntity(
                    userWithRestaurantChoiceDto.getLoggedUser().getId() != null ? userWithRestaurantChoiceDto.getLoggedUser().getId() : errorPlaceholder,
                    userWithRestaurantChoiceDto.getLoggedUser().getName() != null ? userWithRestaurantChoiceDto.getLoggedUser().getName() : errorPlaceholder,
                    userWithRestaurantChoiceDto.getLoggedUser().getEmail() != null ? userWithRestaurantChoiceDto.getLoggedUser().getEmail() : errorPlaceholder,
                    userWithRestaurantChoiceDto.getLoggedUser().getPictureUrl() != null ? userWithRestaurantChoiceDto.getLoggedUser().getPictureUrl() : errorPlaceholder
                );
                attendingRestaurantId = userWithRestaurantChoiceDto.getAttendingRestaurantId() != null ? userWithRestaurantChoiceDto.getAttendingRestaurantId() : null;
                attendingRestaurantName = userWithRestaurantChoiceDto.getAttendingRestaurantName() != null ? userWithRestaurantChoiceDto.getAttendingRestaurantName() : null;
                attendingRestaurantVicinity = userWithRestaurantChoiceDto.getAttendingRestaurantVicinity() != null ? userWithRestaurantChoiceDto.getAttendingRestaurantVicinity() : null;
                attendingRestaurantPictureUrl = userWithRestaurantChoiceDto.getAttendingRestaurantPictureUrl() != null ? userWithRestaurantChoiceDto.getAttendingRestaurantPictureUrl() : null;
            } else {
                loggedUserEntity = new LoggedUserEntity(
                    errorPlaceholder,
                    errorPlaceholder,
                    errorPlaceholder,
                    errorPlaceholder
                );
                attendingRestaurantId = errorPlaceholder;
                attendingRestaurantName = errorPlaceholder;
                attendingRestaurantVicinity = errorPlaceholder;
                attendingRestaurantPictureUrl = errorPlaceholder;
            }

            WorkmateEntity workmateEntity = new WorkmateEntity(
                loggedUserEntity,
                attendingRestaurantId,
                attendingRestaurantName,
                attendingRestaurantVicinity,
                attendingRestaurantPictureUrl
            );
            workmateEntityList.add(workmateEntity);
        }
        return workmateEntityList;
    }
}
