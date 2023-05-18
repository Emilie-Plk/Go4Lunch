package com.emplk.go4lunch.data.auth;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.R;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthRepository {

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<FirebaseUserEntity> firebaseUserEntityMutableLiveData = new MutableLiveData<>();

    @Inject
    public AuthRepository(
        @NonNull FirebaseAuth firebaseAuth
    ) {
        this.firebaseAuth = firebaseAuth;
    }

    public boolean isUserLogged() {
        return firebaseAuth.getCurrentUser() != null;
    }

    public void signOut() {
        firebaseAuth.signOut();
    }


    public LiveData<FirebaseUserEntity> getCurrentUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            String uid = firebaseAuth.getCurrentUser().getUid();
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
                    new FirebaseUserEntity(
                        uid,
                        email,
                        displayName,
                        photoUrl
                    )
                );
            }
        }
        return firebaseUserEntityMutableLiveData;
    }
}
