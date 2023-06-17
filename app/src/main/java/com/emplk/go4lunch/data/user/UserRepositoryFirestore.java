package com.emplk.go4lunch.data.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.ChosenRestaurantEntity;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class UserRepositoryFirestore implements UserRepository {

    private static final String USERS_COLLECTION = "users";

    private static final String USERS_WITH_RESTAURANT_CHOICE = "usersWithRestaurantChoice";
    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public UserRepositoryFirestore(
        @NonNull FirebaseFirestore firestore
    ) {
        this.firestore = firestore;
    }

    @Override
    public void upsertLoggedUserEntity(@Nullable LoggedUserEntity loggedUserEntity) {
        if (loggedUserEntity != null) {
            DocumentReference userDocumentRef = firestore
                .collection(USERS_COLLECTION)
                .document(loggedUserEntity.getId());

            userDocumentRef
                .set(
                    new LoggedUserDto(
                        loggedUserEntity.getId(),
                        loggedUserEntity.getName(),
                        loggedUserEntity.getEmail(),
                        loggedUserEntity.getPictureUrl()
                    )
                )
                .addOnSuccessListener(aVoid -> {
                        Log.i("UserRepositoryFirestore", "User document successfully created!");
                    }
                )
                .addOnFailureListener(e -> {
                        Log.e("UserRepositoryFirestore", "Error creating user document: " + e);
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error creating user document: userEntity is null!");
        }
    }


    @Override
    public void upsertUserRestaurantChoice(
        @Nullable LoggedUserEntity loggedUserEntity,
        @NonNull ChosenRestaurantEntity chosenRestaurantEntity
    ) {
        if (loggedUserEntity != null) {
            DocumentReference userWithRestaurantChoiceDocumentRef =
                firestore
                    .collection(USERS_WITH_RESTAURANT_CHOICE)
                    .document(loggedUserEntity.getId());

            userWithRestaurantChoiceDocumentRef
                .set(
                    new UserWithRestaurantChoiceDto(
                        chosenRestaurantEntity.getAttendingRestaurantId(),
                        chosenRestaurantEntity.getAttendingRestaurantName(),
                        chosenRestaurantEntity.getAttendingRestaurantVicinity(),
                        chosenRestaurantEntity.getAttendingRestaurantPictureUrl()
                    )
                )
                .addOnSuccessListener(aVoid -> {
                        Log.i("UserRepositoryFirestore", "User document successfully created!");
                    }
                )
                .addOnFailureListener(e -> {
                        Log.e("UserRepositoryFirestore", "Error creating user document: " + e);
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error creating user document: userEntity is null!");
        }
    }

    @Override
    public LiveData<List<UserWithRestaurantChoiceEntity>> getUserWithRestaurantChoiceEntities() {
        MutableLiveData<List<UserWithRestaurantChoiceEntity>> userWithRestaurantChoiceEntitiesMutableLiveData = new MutableLiveData<>();

        firestore.collection(USERS_WITH_RESTAURANT_CHOICE)
            .addSnapshotListener((queryDocumentSnapshots, error) -> {
                    if (error != null) {
                        Log.e("UserRepositoryFirestore", "Error fetching users documents: " + error);
                        userWithRestaurantChoiceEntitiesMutableLiveData.setValue(null);
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        List<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntities = new ArrayList<>();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String documentId = documentSnapshot.getId();
                            UserWithRestaurantChoiceDto userWithRestaurantChoiceDto = documentSnapshot.toObject(UserWithRestaurantChoiceDto.class);
                            userWithRestaurantChoiceEntities.add(mapToUserWithRestaurantChoiceEntity(userWithRestaurantChoiceDto, documentId));
                        }
                        userWithRestaurantChoiceEntitiesMutableLiveData.setValue(userWithRestaurantChoiceEntities);
                    }
                }
            );
        return userWithRestaurantChoiceEntitiesMutableLiveData;
    }

    @Override
    public LiveData<UserWithRestaurantChoiceEntity> getUserWithRestaurantChoiceEntity(@NonNull String userId) {
        MutableLiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityMutableLiveData = new MutableLiveData<>();

        firestore
            .collection(USERS_WITH_RESTAURANT_CHOICE)
            .document(userId)
            .addSnapshotListener((documentSnapshot, error) -> {
                    if (error != null) {
                        Log.e("UserRepositoryFirestore", "Error fetching user document: " + error);
                        userWithRestaurantChoiceEntityMutableLiveData.setValue(null);
                        return;
                    }
                    if (documentSnapshot != null) {
                        UserWithRestaurantChoiceDto userWithRestaurantChoiceDto = documentSnapshot.toObject(UserWithRestaurantChoiceDto.class);
                        if (userWithRestaurantChoiceDto != null) {
                            UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity = mapToUserWithRestaurantChoiceEntity(userWithRestaurantChoiceDto, userId);
                            userWithRestaurantChoiceEntityMutableLiveData.setValue(userWithRestaurantChoiceEntity);
                        } else {
                            userWithRestaurantChoiceEntityMutableLiveData.setValue(null);
                        }
                    }
                }
            );
        return userWithRestaurantChoiceEntityMutableLiveData;
    }

    @Override
    public void deleteUserRestaurantChoice(@Nullable LoggedUserEntity loggedUserEntity) {
        if (loggedUserEntity != null) {
            DocumentReference userDocumentRef = firestore
                .collection(USERS_WITH_RESTAURANT_CHOICE)
                .document(loggedUserEntity.getId());

            userDocumentRef
                .delete()
                .addOnSuccessListener(aVoid -> {
                        Log.i("UserRepositoryFirestore", "User's restaurant choice successfully deleted!");
                    }
                )
                .addOnFailureListener(e -> {
                        Log.e("UserRepositoryFirestore", "Error deleting user's restaurant choice: " + e);
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error deleting user's restaurant choice: userEntity is null!");
        }
    }


    @Override
    public LiveData<List<LoggedUserEntity>> getLoggedUserEntitiesLiveData() {
        MutableLiveData<List<LoggedUserEntity>> loggedUserEntitiesMutableLiveData = new MutableLiveData<>();

        firestore
            .collection(USERS_COLLECTION)
            .addSnapshotListener((queryDocumentSnapshots, error) -> {
                    if (error != null) {
                        Log.e("UserRepositoryFirestore", "Error fetching users documents: " + error);
                        loggedUserEntitiesMutableLiveData.setValue(null);
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        List<LoggedUserDto> loggedUserDtos = queryDocumentSnapshots.toObjects(LoggedUserDto.class);
                        List<LoggedUserEntity> loggedUserEntities = new ArrayList<>();
                        for (LoggedUserDto loggedUserDto : loggedUserDtos) {
                            loggedUserEntities.add(mapToLoggedUserEntity(loggedUserDto));
                        }
                        loggedUserEntitiesMutableLiveData.setValue(loggedUserEntities);
                    }
                }
            );
        return loggedUserEntitiesMutableLiveData;
    }

    private LoggedUserEntity mapToLoggedUserEntity(@Nullable LoggedUserDto result) {
        if (result != null &&
            result.getId() != null &&
            result.getName() != null &&
            result.getEmail() != null &&
            result.getPictureUrl() != null
        ) {
            return new LoggedUserEntity(
                result.getId(),
                result.getName(),
                result.getEmail(),
                result.getPictureUrl()
            );
        } else {
            return null;
        }
    }

    private UserWithRestaurantChoiceEntity mapToUserWithRestaurantChoiceEntity(
        UserWithRestaurantChoiceDto userWithRestaurantChoiceDto,
        String userId
    ) {
        if (userWithRestaurantChoiceDto.getAttendingRestaurantId() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantName() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantVicinity() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantPictureUrl() != null
        ) {
            return new UserWithRestaurantChoiceEntity(
                userId,
                userWithRestaurantChoiceDto.getAttendingRestaurantId(),
                userWithRestaurantChoiceDto.getAttendingRestaurantName(),
                userWithRestaurantChoiceDto.getAttendingRestaurantVicinity(),
                userWithRestaurantChoiceDto.getAttendingRestaurantPictureUrl()
            );
        } else {
            return null;
        }
    }
}


