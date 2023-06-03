package com.emplk.go4lunch.data.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class UserRepositoryFirestore implements UserRepository {

    private static final String USERS_COLLECTION = "users";
    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public UserRepositoryFirestore(@NonNull FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void createUser(@Nullable UserEntity userEntity) {
        if (userEntity != null) {
            firestore
                .collection(USERS_COLLECTION)
                .document(userEntity.getLoggedUserEntity().getUserId())
                .set(userEntity)
                .addOnSuccessListener(aVoid -> {
                        // Document creation successful
                        Log.i("UserRepositoryFirestore", "User document successfully created!");
                    }
                )
                .addOnFailureListener(e -> {
                        Log.e("UserRepositoryFirestore", "Error creating user document: ", e);
                    }
                );
        } else {
            Log.e("UserRepositoryFirestore", "Error creating user document: userId is null");
        }
    }

    @Override
    public LiveData<UserEntity> getUser(@Nullable UserEntity userEntity) {
        MutableLiveData<UserEntity> userEntityMutableLiveData = new MutableLiveData<>();
        if (userEntity != null) {
            firestore.collection(USERS_COLLECTION)
                .document(userEntity.getLoggedUserEntity().getUserId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            UserDto result = document.toObject(UserDto.class);
                            UserEntity user = mapToUserEntity(result);
                            userEntityMutableLiveData.setValue(user);
                        } else {
                            // User document not found
                            userEntityMutableLiveData.setValue(null);
                        }
                    } else {
                        // Error occurred while retrieving user document
                        userEntityMutableLiveData.setValue(null);
                    }
                });
        } else {
            // if userId is null
            userEntityMutableLiveData.setValue(null);
        }
        return userEntityMutableLiveData;
    }

    private UserEntity mapToUserEntity(@Nullable UserDto result) {
        if (result != null &&
            result.getUserId() != null &&
            result.getUsername() != null &&
            result.getEmail() != null &&
            result.getPictureUrl() != null
        ) {
            LoggedUserEntity loggedUserEntity = new LoggedUserEntity(
                result.getUserId(),
                result.getUsername(),
                result.getEmail(),
                result.getPictureUrl()
            );
            return new UserEntity(
                loggedUserEntity,
                result.getFavorite_restaurant_list()
            );
        } else {
            return null;
        }
    }

}
