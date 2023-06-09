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

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class UserRepositoryFirestore implements UserRepository {

    private static final String USERS_COLLECTION = "users";

    private static final String USERS_WITH_RESTAURANT_CHOICE = "UserRepositoryFirestore";
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
                .document(loggedUserEntity.getUserId());

            userDocumentRef
                .set(
                    new UserDto(
                        loggedUserEntity.getUserId(),
                        loggedUserEntity.getUsername(),
                        loggedUserEntity.getEmail(),
                        loggedUserEntity.getPhotoUrl()
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
                        UserDto userDto = documentSnapshot.toObject(UserDto.class);
                        LoggedUserEntity loggedUserEntity = mapToLoggedUserEntity(userDto);
                        loggedUserEntityMutableLiveData.setValue(loggedUserEntity);
                    }
                }
            );
        return loggedUserEntityMutableLiveData;
    }

    @Override
    public void addUserRestaurantChoice(
        @Nullable LoggedUserEntity loggedUserEntity,
        @NonNull RestaurantEntity restaurantEntity
    ) {
        if (loggedUserEntity != null) {
            DocumentReference userDocumentRef = firestore
                .collection(USERS_WITH_RESTAURANT_CHOICE)
                .document(loggedUserEntity.getUserId());

            userDocumentRef
                .set(
                    new UserWithRestaurantChoiceDto(
                        loggedUserEntity.getUserId(),
                        loggedUserEntity.getUsername(),
                        loggedUserEntity.getEmail(),
                        loggedUserEntity.getPhotoUrl(),
                        restaurantEntity.getPlaceId(),
                        restaurantEntity.getName()
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
    public void removeUserRestaurantChoice(@Nullable LoggedUserEntity loggedUserEntity) {
        if (loggedUserEntity != null) {
            DocumentReference userDocumentRef = firestore
                .collection(USERS_WITH_RESTAURANT_CHOICE)
                .document(loggedUserEntity.getUserId());

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
    public LiveData<RestaurantEntity> getUserRestaurantChoiceLiveData(@NonNull String userId) {
        MutableLiveData<RestaurantEntity> restaurantEntityMutableLiveData = new MutableLiveData<>();

        firestore.collection(USERS_WITH_RESTAURANT_CHOICE)
            .document(userId)
            .addSnapshotListener((documentSnapshot, error) -> {
                    if (error != null) {
                        Log.e("UserRepositoryFirestore", "Error fetching user document: " + error);
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
        return restaurantEntityMutableLiveData;
    }

    private RestaurantEntity mapToRestaurantEntity(UserWithRestaurantChoiceDto userWithRestaurantChoiceDto) {
        if (userWithRestaurantChoiceDto != null &&
            userWithRestaurantChoiceDto.getRestaurantId() != null &&
            userWithRestaurantChoiceDto.getRestaurantName() != null &&
            userWithRestaurantChoiceDto.getVicinity() != null &&
            userWithRestaurantChoiceDto.getPictureUrl() != null
        ) {
            return new RestaurantEntity(
                userWithRestaurantChoiceDto.getRestaurantId(),
                userWithRestaurantChoiceDto.getRestaurantName(),
                userWithRestaurantChoiceDto.getVicinity(),
                userWithRestaurantChoiceDto.getPictureUrl()
            );
        } else {
            return null;
        }
    }


    private LoggedUserEntity mapToLoggedUserEntity(@Nullable UserDto result) {
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
}

