package com.emplk.go4lunch.data.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;


@Singleton
public class UserRepositoryFirestore implements UserRepository {

    private static final String USERS_COLLECTION = "users";
    @NonNull
    private final FirebaseFirestore firestore;

    @NonNull
    private final MutableLiveData<String> userIdMutableLiveData = new MutableLiveData<>();

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
            userIdMutableLiveData.setValue(userEntity.getLoggedUserEntity().getUserId());

            userDocumentRef
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document != null && document.exists()) {
                            Log.i("UserRepositoryFirestore", "User document already exists!");
                        } else {
                            // Create a new user document with the specified fields
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("name", userEntity.getLoggedUserEntity().getUsername());
                            userData.put("email", userEntity.getLoggedUserEntity().getEmail());
                            userData.put("pictureUrl", userEntity.getLoggedUserEntity().getPhotoUrl());

                            userDocumentRef
                                .set(userData)
                                .addOnSuccessListener(aVoid -> {
                                    // Create the subcollection of favorite restaurant IDs
                                    createFavoriteRestaurantsSubcollection(userDocumentRef);
                                    Log.i("UserRepositoryFirestore", "User document successfully created!");
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("UserRepositoryFirestore", "Error creating user document: " + e);
                                });
                        }
                    } else {
                        // Error occurred while checking if user document exists
                        Log.e("UserRepositoryFirestore", "Error checking if user document exists: ", task.getException());
                    }
                });
        } else {
            Log.e("UserRepositoryFirestore", "Error creating user document: userEntity is null!");
        }
    }

    private void createFavoriteRestaurantsSubcollection(DocumentReference userDocumentRef) {
        DocumentReference favoritesDocumentRef = userDocumentRef.collection("favoriteRestaurantIds").document("favorites");

        // Create an empty document to represent the subcollection
        favoritesDocumentRef
            .set(new HashMap<String, Object>())  // Use an empty HashMap as the document data to represent an empty document
            .addOnSuccessListener(aVoid -> {
                    Log.i("UserRepositoryFirestore", "Empty subcollection created.");
                }
            )
            .addOnFailureListener(e -> {
                    Log.e("UserRepositoryFirestore", "Error creating empty subcollection: " + e);
                }
            );
    }

    @Override
    public LiveData<UserEntity> getUserEntityLiveData() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        MutableLiveData<UserEntity> userEntityMutableLiveData = new MutableLiveData<>();
        if (firebaseAuth.getCurrentUser() != null) {
            String userId = firebaseAuth.getCurrentUser().getUid();
            DocumentReference documentRef = firestore.collection(USERS_COLLECTION).document(userId);
            Log.i("UserRepositoryFirestore", documentRef.getPath());
            documentRef
                .addSnapshotListener((documentSnapshot, error) -> {

                        if (error != null) {
                            Log.e("UserRepositoryFirestore", "Error fetching user document: " + error);
                            userEntityMutableLiveData.setValue(null);
                            return;
                        }
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            UserDto userDto = documentSnapshot.toObject(UserDto.class);
                            UserEntity userEntity = mapToUserEntity(userDto);
                                userEntityMutableLiveData.setValue(userEntity);
                        } else {
                            Log.e("UserRepositoryFirestore", "User document not found.");
                            userEntityMutableLiveData.setValue(null);
                        }
                    }
                );
        } else {
            userEntityMutableLiveData.setValue(null);
        }
        return userEntityMutableLiveData;
    }

    private UserEntity mapToUserEntity(@Nullable UserDto result) {
        if (result != null &&
            result.getId() != null &&
            result.getName() != null &&
            result.getEmail() != null &&
            result.getPictureUrl() != null &&
            result.getFavoriteRestaurantIds() != null
        ) {
            return new UserEntity(
                new LoggedUserEntity(
                    result.getId(),
                    result.getName(),
                    result.getEmail(),
                    result.getPictureUrl()
                ),
                result.getFavoriteRestaurantIds()
            );
        } else {
            return null;
        }
    }
}

