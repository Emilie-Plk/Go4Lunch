package com.emplk.go4lunch.data.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class UserRepositoryFirestore implements UserRepository {

    private static final String USERS_COLLECTION = "users";
    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public UserRepositoryFirestore(
        @NonNull FirebaseFirestore firestore
    ) {
        this.firestore = firestore;
    }

    @Override
    public void createUser(@Nullable UserEntity userEntity) {
        if (userEntity != null) {
            DocumentReference userDocumentRef = firestore
                .collection(USERS_COLLECTION)
                .document(userEntity.getLoggedUserEntity().getUserId());

            userDocumentRef
                .set(
                    new UserDto(
                        userEntity.getLoggedUserEntity().getUserId(),
                        userEntity.getLoggedUserEntity().getUsername(),
                        userEntity.getLoggedUserEntity().getEmail(),
                        userEntity.getLoggedUserEntity().getPhotoUrl()
                    )
                )
                .addOnSuccessListener(aVoid -> {
                    Log.i("UserRepositoryFirestore", "User document successfully created!");
                })
                .addOnFailureListener(e -> {
                    Log.e("UserRepositoryFirestore", "Error creating user document: " + e);
                });
        } else {
            Log.e("UserRepositoryFirestore", "Error creating user document: userEntity is null!");
        }
    }

    @Override
    public LiveData<LoggedUserEntity> getUserEntityLiveData(@NonNull String userId) {
        MutableLiveData<LoggedUserEntity> userEntityMutableLiveData = new MutableLiveData<>();

        firestore
            .collection(USERS_COLLECTION)
            .document(userId)
            .addSnapshotListener((documentSnapshot, error) -> {
                    if (error != null) {
                        Log.e("UserRepositoryFirestore", "Error fetching user document: " + error);
                        userEntityMutableLiveData.setValue(null);
                        return;
                    }
                    if (documentSnapshot != null) {
                        UserDto userDto = documentSnapshot.toObject(UserDto.class);
                        LoggedUserEntity userEntity = mapToUserEntity(userDto);
                        userEntityMutableLiveData.setValue(userEntity);
                    }
                }
            );

        return userEntityMutableLiveData;
    }

    private LoggedUserEntity mapToUserEntity(@Nullable UserDto result) {
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

