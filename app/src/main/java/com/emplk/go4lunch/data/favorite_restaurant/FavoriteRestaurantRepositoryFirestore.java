package com.emplk.go4lunch.data.favorite_restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.favorite_restaurant.FavoriteRestaurantRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FavoriteRestaurantRepositoryFirestore implements FavoriteRestaurantRepository {

    private final MutableLiveData<Map<String, Object>> favoriteRestaurantIdMapMutableLiveData = new MutableLiveData<>();

    private final static String USERS_COLLECTION = "users";

    private final static String COLLECTION_PATH_FAVORITE_RESTAURANTS = "favorite_restaurant_ids";
    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public FavoriteRestaurantRepositoryFirestore(
        @NonNull FirebaseFirestore firestore
    ) {
        this.firestore = firestore;
    }

    @Override
    public void addFavoriteRestaurant(
        @Nullable String userId,
        @NonNull String restaurantId
    ) {
        if (userId != null) {
            Map<String, Object> restaurantData = new HashMap<>();
            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(COLLECTION_PATH_FAVORITE_RESTAURANTS)
                .document(restaurantId)
                .set(restaurantData);
        }
    }

    @Override
    public void removeFavoriteRestaurant(
        @Nullable String userId,
        @NonNull String restaurantId
    ) {
        if (userId != null) {
            firestore.collection(USERS_COLLECTION)
                .document(userId)
                .collection(COLLECTION_PATH_FAVORITE_RESTAURANTS)
                .document(restaurantId)
                .delete();
        }
    }
}
