package com.emplk.go4lunch.data.authentication;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthRepositoryImpl implements AuthRepository {

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<LoggedUserEntity> firebaseUserEntityMutableLiveData = new MutableLiveData<>();

    @Inject
    public AuthRepositoryImpl(
        @NonNull FirebaseAuth firebaseAuth
    ) {
        this.firebaseAuth = firebaseAuth;
    }

    @Override
    public LiveData<LoggedUserEntity> getCurrentUserLiveData() {
        if (firebaseAuth.getCurrentUser() != null) {
            String userId = firebaseAuth.getCurrentUser().getUid();
            String email = firebaseAuth.getCurrentUser().getEmail();
            String displayName = firebaseAuth.getCurrentUser().getDisplayName();
            String photoUrl;

            if (firebaseAuth.getCurrentUser().getPhotoUrl() != null) {
                photoUrl = firebaseAuth.getCurrentUser().getPhotoUrl().toString();
            } else {
                photoUrl = Uri.parse("android.resource://com.emplk.go4lunch/" + R.drawable.restaurant_table).toString();

            }
            if (email != null && displayName != null) {
                firebaseUserEntityMutableLiveData.setValue(
                    new LoggedUserEntity(
                        userId,
                        email,
                        displayName,
                        photoUrl
                    )
                );
            }
        }
        return firebaseUserEntityMutableLiveData;
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }
}
