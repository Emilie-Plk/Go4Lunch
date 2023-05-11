package com.emplk.go4lunch.data.details;

import android.util.LruCache;

import androidx.annotation.NonNull;
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
    private final LruCache<DetailsKey, DetailsRestaurantResponse> detailsCache;

    @Inject
    public DetailsRestaurantRepository(GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        // Initialize the cache with a maximum size of 200 entries
        detailsCache = new LruCache<>(200);
    }

    public LiveData<DetailsRestaurantWrapper> getRestaurantDetails(
        @NonNull String placeId,
        @NonNull String apiKey
    ) {
        MutableLiveData<DetailsRestaurantWrapper> resultMutableLiveData = new MutableLiveData<>();
        DetailsKey cacheKey = generateCacheKey(placeId);

        DetailsRestaurantResponse cachedDetailsEntity = detailsCache.get(cacheKey);

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
                            detailsCache.put(cacheKey, body);
                        }
                        resultMutableLiveData.setValue(new DetailsRestaurantWrapper.Success(body));
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
}
