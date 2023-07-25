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

    private final MutableLiveData<LoggedUserEntity> loggedUserMutableLiveData = new MutableLiveData<>();

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

                    if (firebaseAuth.getCurrentUser() != null &&
                        firebaseAuth.getCurrentUser().getPhotoUrl() != null &&
                        firebaseAuth.getCurrentUser().getDisplayName() != null) {
                        loggedUserMutableLiveData.setValue(new LoggedUserEntity(
                                firebaseAuth.getCurrentUser().getUid(),
                                firebaseAuth.getCurrentUser().getDisplayName(),
                                firebaseAuth.getCurrentUser().getEmail(),
                                firebaseAuth.getCurrentUser().getPhotoUrl().toString()
                            )
                        );
                    }
                }
            }
        );
    }

    @Override
    @Nullable
    public LoggedUserEntity getCurrentLoggedUser() {
        if (firebaseAuth.getCurrentUser() != null) {
            String id = firebaseAuth.getCurrentUser().getUid();
            String email = firebaseAuth.getCurrentUser().getEmail();
            String name = firebaseAuth.getCurrentUser().getDisplayName();
            String pictureUrl;

            if (firebaseAuth.getCurrentUser().getPhotoUrl() != null) {
                pictureUrl = firebaseAuth.getCurrentUser().getPhotoUrl().toString();
            } else {
                pictureUrl = Uri.parse("android.resource://com.emplk.go4lunch/" + R.drawable.baseline_person_24).toString();
            }
            if (name != null) {
                return new LoggedUserEntity(
                    id,
                    name,
                    email,
                    pictureUrl
                );
            }
        } else {
            Log.e("AuthRepository", "Error while getting current user");
        }
        return null;
    }

    @Nullable
    @Override
    public String getCurrentLoggedUserId() {
        if (firebaseAuth.getCurrentUser() != null) {
            return firebaseAuth.getCurrentUser().getUid();
        }
        return null;
    }

    @Override
    public LiveData<Boolean> isUserLoggedLiveData() {
        return isUserLoggedMutableLiveData;
    }

    @Override
    public LiveData<LoggedUserEntity> getLoggedUserLiveData() {
        return loggedUserMutableLiveData;
    }

    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }
}
