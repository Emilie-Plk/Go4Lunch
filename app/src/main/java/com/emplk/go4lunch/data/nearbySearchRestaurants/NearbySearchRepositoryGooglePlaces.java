package com.emplk.go4lunch.data.nearbySearchRestaurants;

import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.GoogleMapsApi;
import com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse.NearbySearchResponse;
import com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse.ResultsItem;
import com.emplk.go4lunch.domain.gps.LocationEntity;
import com.emplk.go4lunch.domain.nearby_search.NearbySearchRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class NearbySearchRepositoryGooglePlaces implements NearbySearchRepository {
    private final GoogleMapsApi googleMapsApi;

    private final LruCache<LocationKey, List<NearbySearchEntity>> nearbySearchCache;

    @Inject
    public NearbySearchRepositoryGooglePlaces(GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        nearbySearchCache = new LruCache<>(200);
    }

    @Override
    public LiveData<NearbySearchWrapper> getNearbyRestaurants(
        @NonNull String location,
        @NonNull String type,
        @NonNull String rankBy,
        @NonNull String key
    ) {
        MutableLiveData<NearbySearchWrapper> resultMutableLiveData = new MutableLiveData<>();
        LocationKey cacheKey = generateCacheKey(location, rankBy);

        List<NearbySearchEntity> cachedNearbySearchEntityList = nearbySearchCache.get(cacheKey);

        if (cachedNearbySearchEntityList == null) {
            resultMutableLiveData.setValue(new NearbySearchWrapper.Loading());
            googleMapsApi.getNearby(
                    location,
                    type,
                    rankBy,
                    key
                )
                .enqueue(
                    new Callback<NearbySearchResponse>() {
                        @Override
                        public void onResponse(
                            @NonNull Call<NearbySearchResponse> call,
                            @NonNull Response<NearbySearchResponse> response
                        ) {
                            if (response.isSuccessful() &&
                                response.body() != null &&
                                response.body().getStatus() != null &&
                                response.body().getStatus().equals("OK")
                            ) {
                                List<NearbySearchEntity> nearbySearchEntityList = fromNearbySearchResponse(response.body());
                                nearbySearchCache.put(cacheKey, nearbySearchEntityList);
                                resultMutableLiveData.setValue(new NearbySearchWrapper.Success(nearbySearchEntityList));
                            } else if (response.isSuccessful() &&
                                response.body() != null &&
                                response.body().getStatus() != null &&
                                response.body().getStatus().equals("ZERO_RESULTS")) {
                                List<NearbySearchEntity> emptyList = new ArrayList<>();
                                nearbySearchCache.put(cacheKey, emptyList);
                                resultMutableLiveData.setValue(new NearbySearchWrapper.NoResults());
                            }
                        }

                        @Override
                        public void onFailure(
                            @NonNull Call<NearbySearchResponse> call,
                            @NonNull Throwable t
                        ) {
                            resultMutableLiveData.setValue(new NearbySearchWrapper.RequestError(t));
                        }
                    }
                );
        } else {
            resultMutableLiveData.setValue(new NearbySearchWrapper.Success(cachedNearbySearchEntityList));
        }
        return resultMutableLiveData;
    }

    private LocationKey generateCacheKey(
        String location,
        @NonNull String rankBy
    ) {
        String[] coordinatesArr = location.split(",");
        Double latitude = Double.parseDouble(coordinatesArr[1]);
        Double longitude = Double.parseDouble(coordinatesArr[0]);
        LocationEntity locationEntity = new LocationEntity(latitude, longitude);

        return new LocationKey(locationEntity, rankBy);
    }

    private List<NearbySearchEntity> fromNearbySearchResponse(@Nullable NearbySearchResponse response) {
        List<NearbySearchEntity> results = new ArrayList<>();

        if (response != null && response.getResults() != null) {
            for (ResultsItem result : response.getResults()) {
                String placeId = result.getPlaceId();
                String name = result.getName();
                String vicinity = result.getVicinity();
                String photoUrl = null;
                if (result.getPhotos() != null &&
                    !result.getPhotos().isEmpty() &&
                    result.getPhotos().get(0) != null) {
                    photoUrl = result.getPhotos().get(0).getPhotoReference();
                }
                Float rating = result.getRating();

                Double latitude = null;
                Double longitude = null;
                if (result.getGeometry() != null && result.getGeometry().getLocation() != null) {
                    latitude = result.getGeometry().getLocation().getLat();
                    longitude = result.getGeometry().getLocation().getLng();
                }
                LocationEntity locationEntity = new LocationEntity(latitude, longitude);

                Boolean openingHours = null;
                if (result.getOpeningHours() != null && result.getOpeningHours().isOpenNow() != null) {
                    openingHours = result.getOpeningHours().isOpenNow();
                }
                if (placeId != null &&
                    name != null &&
                    vicinity != null
                ) {
                    NearbySearchEntity searchResult = new NearbySearchEntity(
                        placeId,
                        name,
                        vicinity,
                        photoUrl,
                        rating,
                        locationEntity,
                        openingHours);
                    results.add(searchResult);
                }
            }
        }
        return results;
    }
}
