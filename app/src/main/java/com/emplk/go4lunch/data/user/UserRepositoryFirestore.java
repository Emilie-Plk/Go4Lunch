package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

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
                .set(userEntity);
        }  //TODO: tired of if/else because of null, how to properly handle this?
    }

    @Override
    public LiveData<UserEntity> getUser() {
        return null;
    }

    @Override
    public void updateUser() {
    }

}
