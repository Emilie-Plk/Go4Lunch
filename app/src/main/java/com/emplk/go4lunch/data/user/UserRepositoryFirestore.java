package com.emplk.go4lunch.data.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
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

