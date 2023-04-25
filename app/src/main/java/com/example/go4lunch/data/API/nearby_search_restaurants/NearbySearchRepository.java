package com.example.go4lunch.data.API.nearby_search_restaurants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.data.API.GoogleMapsApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class NearbySearchRepository {
    private final GoogleMapsApi googleMapsApi;

    private final Map<Integer, List<NearbySearchResult>> nearbySearchCache = new HashMap<>();

    @Inject
    public NearbySearchRepository(GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
    }


    public LiveData<NearbySearchWrapper> getNearbyRestaurants(
        @NonNull String location,
        @NonNull String type,
        @Nullable String keyword,
        @NonNull String rankby,
        @NonNull String key) {

        MutableLiveData<NearbySearchWrapper> resultMutableLiveData = new MutableLiveData<>();
        Integer cacheKey = generateCacheKey(location, type, keyword, rankby, key);

        if (!nearbySearchCache.containsKey(cacheKey)) {

            resultMutableLiveData.setValue(new NearbySearchWrapper.Loading());
            googleMapsApi.getNearby(location, type, keyword, rankby, key).enqueue(
                new Callback<NearbySearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<NearbySearchResponse> call, @NonNull Response<NearbySearchResponse> response) {
                        if (response.isSuccessful()) {
                            List<NearbySearchResult> nearbySearchResults = NearbySearchResult.fromNearbySearchResponse(response.body());
                            if (!nearbySearchResults.isEmpty()) {
                                nearbySearchCache.put(cacheKey, nearbySearchResults);
                            }
                            resultMutableLiveData.setValue(new NearbySearchWrapper.Success(nearbySearchResults));
                        } else {
                            resultMutableLiveData.setValue(new NearbySearchWrapper.Error(new Throwable("API call failed")));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NearbySearchResponse> call, @NonNull Throwable t) {
                        resultMutableLiveData.setValue(new NearbySearchWrapper.Error(t));
                    }
                });
        } else   resultMutableLiveData.setValue(new NearbySearchWrapper.Success(nearbySearchCache.get(cacheKey)));
        return resultMutableLiveData;

    }

    private Integer generateCacheKey(
        @NonNull String location,
        @NonNull String type,
        @Nullable String keyword,
        @NonNull String rankby,
        @NonNull String key
    ) {
        int prime = 31;
        int result = 1;
        result = prime * result + location.hashCode();
        result = prime * result + type.hashCode();
        result = prime * result + (keyword != null ? keyword.hashCode() : 0); // because Nullable
        result = prime * result + rankby.hashCode();
        result = prime * result + key.hashCode();
        return result;
    }

}
