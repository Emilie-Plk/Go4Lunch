package com.emplk.go4lunch.data.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.RestaurantEntity;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

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
    public LiveData<LoggedUserEntity> getLoggedUserEntityLiveData(@NonNull String userId) {
        MutableLiveData<LoggedUserEntity> loggedUserEntityMutableLiveData = new MutableLiveData<>();

        firestore.collection(USERS_COLLECTION)
            .document(userId)
            .addSnapshotListener((documentSnapshot, error) -> {   // either error/documentSnapshot
                    if (error != null) {
                        Log.e("UserRepositoryFirestore", "Error fetching user document: " + error);
                        loggedUserEntityMutableLiveData.setValue(null);
                        return;
                    }
                    if (documentSnapshot != null) {
                        LoggedUserDto loggedUserDto = documentSnapshot.toObject(LoggedUserDto.class);
                        LoggedUserEntity loggedUserEntity = mapToLoggedUserEntity(loggedUserDto);
                        loggedUserEntityMutableLiveData.setValue(loggedUserEntity);
                    }
                }
            );
        return loggedUserEntityMutableLiveData;
    }

    @Override
    public void upsertUserRestaurantChoice(
        @Nullable LoggedUserEntity loggedUserEntity,
        @NonNull RestaurantEntity restaurantEntity
    ) {
        if (loggedUserEntity != null) {
            DocumentReference userWithRestaurantChoiceDocumentRef =
                firestore
                    .collection(USERS_WITH_RESTAURANT_CHOICE)
                    .document(loggedUserEntity.getId());

            userWithRestaurantChoiceDocumentRef
                .set(
                    new UserWithRestaurantChoiceDto(
                        new LoggedUserDto(
                            loggedUserEntity.getId(),
                            loggedUserEntity.getName(),
                            loggedUserEntity.getEmail(),
                            loggedUserEntity.getPictureUrl()
                        ),
                        restaurantEntity.getAttendingRestaurantId(),
                        restaurantEntity.getAttendingRestaurantName(),
                        restaurantEntity.getAttendingRestaurantVicinity(),
                        restaurantEntity.getAttendingRestaurantPictureUrl()
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
    public void deleteUserRestaurantChoice(@Nullable LoggedUserEntity loggedUserEntity) {
        if (loggedUserEntity != null) {
            DocumentReference userDocumentRef = firestore
                .collection(USERS_WITH_RESTAURANT_CHOICE)
                .document(loggedUserEntity.getId());

            userDocumentRef
                .delete()
                .addOnSuccessListener(aVoid -> {
                        Log.i("UserRepositoryFirestore", "User document successfully deleted!");
                    }
                )
                .addOnFailureListener(e -> {
                        Log.e("UserRepositoryFirestore", "Error deleting user document: " + e);
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error deleting user document: userEntity is null!");
        }
    }

    @Override
    public LiveData<RestaurantEntity> getUserRestaurantChoiceLiveData(@Nullable LoggedUserEntity loggedUserEntity) {
        MutableLiveData<RestaurantEntity> restaurantEntityMutableLiveData = new MutableLiveData<>();

        if (loggedUserEntity != null) {
            firestore
                .collection(USERS_WITH_RESTAURANT_CHOICE)
                .document(loggedUserEntity.getId())
                .addSnapshotListener((documentSnapshot, error) -> {
                        if (error != null) {
                            Log.e("UserRepositoryFirestore", "Error fetching user with restaurant choice document: " + error);
                            restaurantEntityMutableLiveData.setValue(null);
                            return;
                        }
                        if (documentSnapshot != null) {
                            UserWithRestaurantChoiceDto userWithRestaurantChoiceDto = documentSnapshot.toObject(UserWithRestaurantChoiceDto.class);
                            RestaurantEntity restaurantEntity = mapToRestaurantEntity(userWithRestaurantChoiceDto);
                            restaurantEntityMutableLiveData.setValue(restaurantEntity);
                        }
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error fetching user with restaurant choice document: userEntity is null!");
            restaurantEntityMutableLiveData.setValue(null);
        }
        return restaurantEntityMutableLiveData;
    }

    @Override
    public LiveData<List<LoggedUserEntity>> getLoggedUserEntitiesLiveData() {
     MutableLiveData<List<LoggedUserEntity>> loggedUserEntitiesMutableLiveData = new MutableLiveData<>();

        firestore.collection(USERS_COLLECTION)
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

    private RestaurantEntity mapToRestaurantEntity(UserWithRestaurantChoiceDto userWithRestaurantChoiceDto) {
        if (userWithRestaurantChoiceDto != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantId() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantName() != null &&
            userWithRestaurantChoiceDto.getAttendingRestaurantVicinity() != null &&
            userWithRestaurantChoiceDto.getLoggedUser() != null
        ) {
            return new RestaurantEntity(
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


