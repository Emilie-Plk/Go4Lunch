package com.emplk.go4lunch.data.nearbySearchRestaurants;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.location.Location;
import android.util.Log;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.data.GoogleMapsApi;
import com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse.NearbySearchResponse;
import com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse.ResultsItem;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.nearby_search.NearbySearchRepository;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class NearbySearchRepositoryGooglePlaces implements NearbySearchRepository {
    @NonNull
    private final GoogleMapsApi googleMapsApi;

    private final LruCache<LocationKey, List<NearbySearchEntity>> nearbySearchLruCache;

    @Inject
    public NearbySearchRepositoryGooglePlaces(@NonNull GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        nearbySearchLruCache = new LruCache<>(400);
    }

    @Override
    public LiveData<NearbySearchWrapper> getNearbyRestaurants(
        @NonNull String location,
        @NonNull String type,
        int radius
    ) {
        MutableLiveData<NearbySearchWrapper> resultMutableLiveData = new MutableLiveData<>();
        LocationKey cacheKey = generateCacheKey(location);
        Log.d("NearbySearchRepository", "nearbySearchLruCache size is:" + nearbySearchLruCache.size());
        List<NearbySearchEntity> cachedNearbySearchEntityList = nearbySearchLruCache.get(cacheKey);

        if (cachedNearbySearchEntityList == null) {
            resultMutableLiveData.setValue(new NearbySearchWrapper.Loading());
            googleMapsApi.getNearby(
                    location,
                    type,
                    radius,
                    API_KEY
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
                                List<NearbySearchEntity> nearbySearchEntityList = mapToNearbySearchEntityList(response.body(), location);
                                if (nearbySearchEntityList != null) {
                                    nearbySearchLruCache.put(cacheKey, nearbySearchEntityList);
                                    resultMutableLiveData.setValue(new NearbySearchWrapper.Success(nearbySearchEntityList));
                                }
                            } else if (response.isSuccessful() &&
                                response.body() != null &&
                                response.body().getStatus() != null &&
                                response.body().getStatus().equals("ZERO_RESULTS")
                            ) {
                                List<NearbySearchEntity> emptyList = new ArrayList<>();
                                nearbySearchLruCache.put(cacheKey, emptyList);
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
        @NonNull String location
    ) {
        String[] coordinatesArr = location.split(",");
        Double latitude = Double.parseDouble(coordinatesArr[1]);
        Double longitude = Double.parseDouble(coordinatesArr[0]);
        LocationEntity locationEntity = new LocationEntity(latitude, longitude);

        return new LocationKey(locationEntity);
    }

    private List<NearbySearchEntity> mapToNearbySearchEntityList(
        @Nullable NearbySearchResponse response,
        @NonNull String userLocation
    ) {
        List<NearbySearchEntity> results = new ArrayList<>();

        if (response != null && response.getResults() != null) {

            for (ResultsItem result : response.getResults()) {
                String placeId = result.getPlaceId();
                String name = result.getName();
                String vicinity = result.getVicinity();

                String photoUrl;
                if (result.getPhotos() != null &&
                    !result.getPhotos().isEmpty() &&
                    result.getPhotos().get(0) != null) {
                    photoUrl = result.getPhotos().get(0).getPhotoReference();
                } else {
                    photoUrl = null;
                }

                Float rating;
                if (result.getRating() != null) {
                    rating = result.getRating();
                } else {
                    rating = null;
                }

                LocationEntity locationEntity;
                Integer distance;
                if (result.getGeometry() != null && result.getGeometry().getLocation() != null) {
                    Double latitude = result.getGeometry().getLocation().getLat();
                    Double longitude = result.getGeometry().getLocation().getLng();
                    locationEntity = new LocationEntity(latitude, longitude);
                    distance = getDistanceFromUserLocation(locationEntity, userLocation);
                } else {
                    locationEntity = null;
                    distance = null;
                }

                Boolean openingHours;
                if (result.getOpeningHours() != null && result.getOpeningHours().isOpenNow() != null) {
                    openingHours = result.getOpeningHours().isOpenNow();
                } else {
                    openingHours = null;
                }

                if (placeId != null &&
                    name != null &&
                    vicinity != null &&
                    locationEntity != null
                ) {
                    NearbySearchEntity searchResult = new NearbySearchEntity(
                        placeId,
                        name,
                        vicinity,
                        photoUrl,
                        rating,
                        locationEntity,
                        distance,
                        openingHours);
                    results.add(searchResult);
                } else {
                    return null;
                }
            }
        }
        return results;
    }

    private Integer getDistanceFromUserLocation(
        @NonNull LocationEntity restaurantLocationEntity,
        @NonNull String userLocationString
    ) {
        String[] coordinatesArr = userLocationString.split(",");
        double userLatitude = Double.parseDouble(coordinatesArr[0]);
        double userLongitude = Double.parseDouble(coordinatesArr[1]);

        Location userLocation = new Location("userLocation");
        userLocation.setLatitude(userLatitude);
        userLocation.setLongitude(userLongitude);

        Location restaurantLocation = new Location("nearbySearchResultRestaurantLocation");
        restaurantLocation.setLatitude(restaurantLocationEntity.getLatitude());
        restaurantLocation.setLongitude(restaurantLocationEntity.getLongitude());

        float distance = userLocation.distanceTo(restaurantLocation);

        return (int) Math.floor(distance);
    }
}
