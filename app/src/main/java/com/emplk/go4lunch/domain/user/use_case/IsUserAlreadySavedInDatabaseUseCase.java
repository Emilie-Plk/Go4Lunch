package com.emplk.go4lunch.domain.user.use_case;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Inject;

public class IsUserAlreadySavedInDatabaseUseCase {

    private final static String USERS_COLLECTION = "users";

    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public IsUserAlreadySavedInDatabaseUseCase(@NonNull FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    public LiveData<Boolean> invoke(@Nullable String userId) {
        MutableLiveData<Boolean> isExistLiveData = new MutableLiveData<>();
        if (userId == null) {
            return null;
        } else {
            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            boolean isExist = document.exists();
                            isExistLiveData.setValue(isExist);
                        } else {
                            // Error occurred while checking user existence
                            isExistLiveData.setValue(false);
                        }
                    }
                );
        }
        return isExistLiveData;
    }
}
