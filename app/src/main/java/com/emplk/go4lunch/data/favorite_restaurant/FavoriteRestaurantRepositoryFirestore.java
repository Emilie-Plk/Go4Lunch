package com.emplk.go4lunch.data.favorite_restaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.favorite_restaurant.FavoriteRestaurantRepository;
import com.emplk.go4lunch.domain.user.RestaurantEntity;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FavoriteRestaurantRepositoryFirestore implements FavoriteRestaurantRepository {

    private final MutableLiveData<List<RestaurantEntity>> favoriteRestaurantEntityListMutableLiveData = new MutableLiveData<>();

    private final static String USERS_COLLECTION = "users";

    private final static String COLLECTION_PATH_FAVORITE_RESTAURANTS = "favorite_restaurants";
    @NonNull
    private final FirebaseFirestore firestore;

    @Inject
    public FavoriteRestaurantRepositoryFirestore(@NonNull FirebaseFirestore firestore) {
        this.firestore = firestore;
    }


    @Override
    public LiveData<List<RestaurantEntity>> getUserFavoriteRestaurantList(@Nullable UserEntity userEntity) {
        if (userEntity != null) {
            firestore.collection(USERS_COLLECTION)
                .document(userEntity.getLoggedUserEntity().getUserId())
                .collection(COLLECTION_PATH_FAVORITE_RESTAURANTS)
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                        if (queryDocumentSnapshots != null) {
                            favoriteRestaurantEntityListMutableLiveData.setValue(queryDocumentSnapshots.toObjects(RestaurantEntity.class));
                        }
                    }
                );
        }
        return favoriteRestaurantEntityListMutableLiveData;
    }

    @Override
    public void addFavoriteRestaurant(
        @Nullable UserEntity userEntity,
        @NonNull RestaurantEntity restaurantEntity
    ) {
        firestore.collection(USERS_COLLECTION)
            .document(userEntity.getLoggedUserEntity().getUserId())
            .collection(COLLECTION_PATH_FAVORITE_RESTAURANTS)
            .document(restaurantEntity.getPlaceId())
            .set(restaurantEntity);
    }

    @Override
    public void removeFavoriteRestaurant(
        @Nullable UserEntity userEntity,
        @NonNull RestaurantEntity restaurantEntity
    ) {

    }

    @Override
    public void updateUserFavoriteRestaurantList(
        @Nullable UserEntity userEntity,
        @NonNull RestaurantEntity restaurantEntity
    ) {

    }
}
