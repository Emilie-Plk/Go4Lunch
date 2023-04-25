package com.example.go4lunch.data.API.nearby_search_restaurants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.data.API.GoogleMapsApi;

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

    private final Map<Integer, List<NearbySearchModel>> nearbySearchCache = new HashMap<>();

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

        List<NearbySearchModel> cachedNearbySearchModelList = nearbySearchCache.get(cacheKey);

        if (cachedNearbySearchModelList == null) {

            resultMutableLiveData.setValue(new NearbySearchWrapper.Loading());
            googleMapsApi.getNearby(location, type, keyword, rankby, key).enqueue(
                new Callback<NearbySearchResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<NearbySearchResponse> call, @NonNull Response<NearbySearchResponse> response
                    ) {
                        if (response.isSuccessful()) {
                            List<NearbySearchModel> nearbySearchModels = NearbySearchModel.fromNearbySearchResponse(response.body());
                            nearbySearchCache.put(cacheKey, nearbySearchModels);
                            resultMutableLiveData.setValue(new NearbySearchWrapper.Success(nearbySearchModels));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<NearbySearchResponse> call, @NonNull Throwable t) {
                        resultMutableLiveData.setValue(new NearbySearchWrapper.Error(t));
                    }
                }
            );
        } else {
            resultMutableLiveData.setValue(new NearbySearchWrapper.Success(cachedNearbySearchModelList));
        }
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
