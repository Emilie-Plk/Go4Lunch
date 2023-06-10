package com.emplk.go4lunch.data.authentication;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.google.firebase.auth.FirebaseAuth;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthRepositoryFirebaseAuth implements AuthRepository {

    private final FirebaseAuth firebaseAuth;
    private final MutableLiveData<Boolean> isUserLoggedMutableLiveData = new MutableLiveData<>();

    @Inject
    public AuthRepositoryFirebaseAuth(
        @NonNull FirebaseAuth firebaseAuth
    ) {
        this.firebaseAuth = firebaseAuth;

        firebaseAuth.addAuthStateListener(
            new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    boolean isUserLogged = firebaseAuth.getCurrentUser() != null;
                    isUserLoggedMutableLiveData.setValue(isUserLogged);
                }
            }
        );
    }

    @Override
    public LoggedUserEntity getCurrentLoggedUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            String id = firebaseAuth.getCurrentUser().getUid();
            String email = firebaseAuth.getCurrentUser().getEmail();
            String name = firebaseAuth.getCurrentUser().getDisplayName();
            String pictureUrl;

            if (firebaseAuth.getCurrentUser().getPhotoUrl() != null) {
                pictureUrl = firebaseAuth.getCurrentUser().getPhotoUrl().toString();
            } else {
                pictureUrl = Uri.parse("android.resource://com.emplk.go4lunch/" + R.drawable.restaurant_table).toString();
            }
            if (email != null && name != null) {
                return new LoggedUserEntity(
                    id,
                    name,
                    email,
                    pictureUrl
                );
            }
        }
        Log.e("AuthRepository", "Error while getting current user");
        return null;
    }

    @Nullable
    @Override
    public String getCurrentLoggedUserId() {
        String loggedUserId;
        if (firebaseAuth.getCurrentUser() != null) {
            loggedUserId = firebaseAuth.getCurrentUser().getUid();
        } else {
            loggedUserId = null;
        }
        return loggedUserId;
    }

    @Override
    public LiveData<Boolean> isUserLoggedLiveData() {
        return isUserLoggedMutableLiveData;
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }
}
