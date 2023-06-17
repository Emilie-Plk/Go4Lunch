package com.emplk.go4lunch.data.util;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class FirestoreFavoriteRestaurantIdsLiveData extends LiveData<Set<String>> {

    private static final String TAG = "FirestoreFavResIdsLD";

    private final CollectionReference favoriteRestaurantsCollectionRef;

    private final EventListener<QuerySnapshot> eventListener = (querySnapshot, error) -> {
        if (error != null) {
            Log.i(TAG, "Listen failed", error);
            setValue(Collections.emptySet());
            return;
        }

        if (querySnapshot != null) {
            Set<String> favoriteRestaurantIds = new HashSet<>();
            for (QueryDocumentSnapshot document : querySnapshot) {
                String favoriteRestaurantId = document.getId();
                favoriteRestaurantIds.add(favoriteRestaurantId);
            }
            setValue(favoriteRestaurantIds);
        }
    };

    private ListenerRegistration registration;

    public FirestoreFavoriteRestaurantIdsLiveData(DocumentReference userDocumentRef, CollectionReference favoriteRestaurantsCollectionRef) {
        this.favoriteRestaurantsCollectionRef = favoriteRestaurantsCollectionRef;
    }

    @Override
    protected void onActive() {
        registration = favoriteRestaurantsCollectionRef.addSnapshotListener(eventListener);
    }

    @Override
    protected void onInactive() {
        registration.remove();
        registration = null;
    }
}
