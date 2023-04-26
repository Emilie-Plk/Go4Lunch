package com.example.go4lunch.data.nearbySearchRestaurants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.GoogleMapsApi;
import com.example.go4lunch.data.nearbySearchRestaurants.response.NearbySearchResponse;
import com.example.go4lunch.data.nearbySearchRestaurants.response.ResultsItem;

import java.util.ArrayList;
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

    private final Map<Integer, List<NearbySearchEntity>> nearbySearchCache = new HashMap<>();

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

        List<NearbySearchEntity> cachedNearbySearchEntityList = nearbySearchCache.get(cacheKey);

        if (cachedNearbySearchEntityList == null) {

            resultMutableLiveData.setValue(new NearbySearchWrapper.Loading());
            googleMapsApi.getNearby(location, type, keyword, rankby, key).enqueue(
                new Callback<NearbySearchResponse>() {
                    @Override
                    public void onResponse(
                        @NonNull Call<NearbySearchResponse> call,
                        @NonNull Response<NearbySearchResponse> response
                    ) {
                        if (response.isSuccessful()) {
                            List<NearbySearchEntity> nearbySearchEntityList = fromNearbySearchResponse(response.body());
                            nearbySearchCache.put(cacheKey, nearbySearchEntityList);
                            resultMutableLiveData.setValue(new NearbySearchWrapper.Success(nearbySearchEntityList));
                        }
                    }

                    @Override
                    public void onFailure(
                        @NonNull Call<NearbySearchResponse> call,
                        @NonNull Throwable t
                    ) {
                        resultMutableLiveData.setValue(new NearbySearchWrapper.Error(t));
                    }
                }
            );
        } else {
            resultMutableLiveData.setValue(new NearbySearchWrapper.Success(cachedNearbySearchEntityList));
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

    private List<NearbySearchEntity> fromNearbySearchResponse(@Nullable NearbySearchResponse response) {
        List<NearbySearchEntity> results = new ArrayList<>();
        if (response != null && response.getResults() != null) {
            for (ResultsItem result : response.getResults()) {
                    String placeId = result.getPlaceId();
                    String name = result.getName();
                    String vicinity = result.getVicinity();
                    String cuisine = result.getTypes().get(0);
                    String photoUrl = result.getPhotos().get(0).getPhotoReference();
                    Float rating = result.getRating();
                    Float latitude = result.getGeometry().getLocation().getLat();
                    Float longitude = result.getGeometry().getLocation().getLng();
                    Boolean openingHours = result.getOpeningHours().isOpenNow();
                    NearbySearchEntity searchResult = new NearbySearchEntity(
                        placeId,
                        name,
                        vicinity,
                        photoUrl,
                        cuisine,
                        rating,
                        latitude,
                        longitude,
                        openingHours);
                    results.add(searchResult);
                }
        }
        return results;
    }

}
