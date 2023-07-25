package com.emplk.go4lunch.data.details;


import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.util.Log;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.data.GooglePlacesApi;
import com.emplk.go4lunch.data.details.details_restaurant_response.DetailsRestaurantResponse;
import com.emplk.go4lunch.domain.detail.DetailsRestaurantRepository;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantEntity;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class DetailsRestaurantRepositoryGooglePlaces implements DetailsRestaurantRepository {

    private final GooglePlacesApi googlePlacesApi;

    // TODO: maybe add a DiskLruCache with double hookup
    private final LruCache<String, DetailsRestaurantEntity> detailsLruCache;

    @Inject
    public DetailsRestaurantRepositoryGooglePlaces(GooglePlacesApi googlePlacesApi) {
        this.googlePlacesApi = googlePlacesApi;
        detailsLruCache = new LruCache<>(200);
    }

    public LiveData<DetailsRestaurantWrapper> getRestaurantDetails(
        @NonNull String placeId
    ) {
        MutableLiveData<DetailsRestaurantWrapper> resultMutableLiveData = new MutableLiveData<>();
        Log.d("DetailsRestaurantRepo", "detailsLruCache size is:" + detailsLruCache.size());
        DetailsRestaurantEntity cachedDetailsEntity = detailsLruCache.get(placeId);

        if (cachedDetailsEntity == null) {
            resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Loading());
            googlePlacesApi.getPlaceDetails(placeId, API_KEY)
                .enqueue(
                    new Callback<DetailsRestaurantResponse>() {
                        @Override
                        public void onResponse(
                            @NonNull Call<DetailsRestaurantResponse> call,
                            @NonNull Response<DetailsRestaurantResponse> response
                        ) {
                            DetailsRestaurantResponse body = response.body();
                            if (response.isSuccessful() &&
                                body != null &&
                                body.getStatus() != null &&
                                body.getStatus().equals("OK") &&
                                body.getResult() != null &&
                                body.getResult().getPlaceId() != null &&
                                body.getResult().getName() != null &&
                                body.getResult().getVicinity() != null
                            ) {
                                DetailsRestaurantEntity detailsRestaurantEntity = mapToDetailsRestaurantEntity(response.body());
                                if (detailsRestaurantEntity != null) {
                                    detailsLruCache.put(placeId, detailsRestaurantEntity);
                                    resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Success(detailsRestaurantEntity));
                                } else {
                                    resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Error(new Exception("Error while fetching details")));
                                }
                            } else {
                                resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Error(new Exception("Error while fetching details")));
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

    private DetailsRestaurantEntity mapToDetailsRestaurantEntity(
        @Nullable DetailsRestaurantResponse
            response
    ) {
        String placeId = null;
        String name = null;
        String vicinity = null;
        String photoReference;
        Float rating;
        String formattedPhoneNumber;
        String website;
        Boolean isVeganFriendly;

        if (response != null &&
            response.getResult() != null
        ) {
            if (response.getResult().getPlaceId() != null &&
                response.getResult().getName() != null &&
                response.getResult().getVicinity() != null
            ) {
                placeId = response.getResult().getPlaceId();
                name = response.getResult().getName();
                vicinity = response.getResult().getVicinity();
            }

            if (response.getResult().getPhotos() != null &&
                response.getResult().getPhotos().size() > 0 &&
                response.getResult().getPhotos().get(0).getPhotoReference() != null) {
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
            if (placeId != null &&
                name != null &&
                vicinity != null
            ) {
                return new DetailsRestaurantEntity(
                    placeId,
                    name,
                    vicinity,
                    photoReference,
                    rating,
                    formattedPhoneNumber,
                    website,
                    isVeganFriendly
                );
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
