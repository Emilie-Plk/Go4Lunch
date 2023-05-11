package com.emplk.go4lunch.data.details;

import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.GoogleMapsApi;
import com.emplk.go4lunch.data.details.details_restaurant_response.DetailsRestaurantResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class DetailsRestaurantRepository {

    private final GoogleMapsApi googleMapsApi;

    // TODO: maybe add a DiskLruCache with double hookup
    private final LruCache<DetailsKey, DetailsRestaurantEntity> detailsCache;

    @Inject
    public DetailsRestaurantRepository(GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        detailsCache = new LruCache<>(200);
    }

    public LiveData<DetailsRestaurantWrapper> getRestaurantDetails(
        @NonNull String placeId,
        @NonNull String apiKey
    ) {
        MutableLiveData<DetailsRestaurantWrapper> resultMutableLiveData = new MutableLiveData<>();
        DetailsKey cacheKey = generateCacheKey(placeId);

        DetailsRestaurantEntity cachedDetailsEntity = detailsCache.get(cacheKey);

        if (cachedDetailsEntity == null) {
            resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Loading());
            googleMapsApi.getPlaceDetails(placeId, apiKey).enqueue(
                new Callback<DetailsRestaurantResponse>() {
                    @Override
                    public void onResponse(
                        @NonNull Call<DetailsRestaurantResponse> call,
                        @NonNull Response<DetailsRestaurantResponse> response
                    ) {
                        DetailsRestaurantResponse body = response.body();
                        if (response.isSuccessful() &&
                            body != null && body.getStatus() != null &&
                            body.getStatus().equals("OK")
                        ) {
                            DetailsRestaurantEntity detailsRestaurantEntity = fromDetailsResponse(response.body());
                            detailsCache.put(cacheKey, detailsRestaurantEntity);
                            resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Success(detailsRestaurantEntity));
                        }
                    }

                    @Override
                    public void onFailure(
                        @NonNull Call<DetailsRestaurantResponse> call,
                        @NonNull Throwable t
                    ) {
                        resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Error(t));
                    }
                }
            );
        } else {
            resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Success(cachedDetailsEntity));
        }
        return resultMutableLiveData;
    }

    private DetailsKey generateCacheKey(@NonNull String placeId) {
        return new DetailsKey(placeId);
    }

    private DetailsRestaurantEntity fromDetailsResponse(@Nullable DetailsRestaurantResponse response) {
        String placeId;
        String name;
        String vicinity;
        String photoReference;
        Float rating;
        String formattedPhoneNumber;
        String website;
        Boolean isVeganFriendly;

        if (response != null &&
            response.getResult() != null
            && response.getResult().getPlaceId() != null &&
            response.getResult().getName() != null &&
            response.getResult().getVicinity() != null) {
            placeId = response.getResult().getPlaceId();
            name = response.getResult().getName();
            vicinity = response.getResult().getVicinity();
        } else return null;

        if (response.getResult().getPhotos() != null &&
            response.getResult().getPhotos().get(0) != null &&
            !response.getResult().getPhotos().get(0).getPhotoReference().isEmpty()) {
            photoReference = response.getResult().getPhotos().get(0).getPhotoReference();
        } else {
            photoReference = null;
        }

        if (response.getResult().getRating() != null) {
            rating = response.getResult().getRating();
        } else {
            rating = null;
        }

        if (response.getResult().getFormattedPhoneNumber() != null) {
            formattedPhoneNumber = response.getResult().getFormattedPhoneNumber();
        } else {
            formattedPhoneNumber = null;
        }

        if (response.getResult().getWebsite() != null) {
            website = response.getResult().getWebsite();
        } else {
            website = null;
        }

        if (response.getResult().isServesVegetarianFood() != null) {
            isVeganFriendly = response.getResult().isServesVegetarianFood();
        } else {
            isVeganFriendly = null;
        }

        return new DetailsRestaurantEntity(
            placeId,
            name,
            vicinity,
            photoReference,
            rating,
            formattedPhoneNumber,
            website,
            isVeganFriendly);

    }
}
