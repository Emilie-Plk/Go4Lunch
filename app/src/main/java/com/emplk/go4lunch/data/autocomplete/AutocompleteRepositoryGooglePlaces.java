package com.emplk.go4lunch.data.autocomplete;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.data.GoogleMapsApi;
import com.emplk.go4lunch.data.autocomplete.autocomplete_response.AutocompleteSuggestionResponses;
import com.emplk.go4lunch.data.autocomplete.autocomplete_response.PredictionsItem;
import com.emplk.go4lunch.data.nearbySearchRestaurants.LocationKey;
import com.emplk.go4lunch.domain.autocomplete.AutocompleteRepository;
import com.emplk.go4lunch.domain.autocomplete.entity.PredictionEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AutocompleteRepositoryGooglePlaces implements AutocompleteRepository {

    private final LruCache<LocationKey, List<PredictionEntity>> userQueryLruCache;

    @NonNull
    private final GoogleMapsApi googleMapsApi;

    @Inject
    public AutocompleteRepositoryGooglePlaces(@NonNull GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        userQueryLruCache = new LruCache<>(400);
    }

    @Override
    public LiveData<List<PredictionEntity>> getAutocompleteResult(
        @NonNull String query,
        @NonNull String location,
        int radius,
        @NonNull String types
    ) {
        MutableLiveData<List<PredictionEntity>> predictionEntities = new MutableLiveData<>();
        LocationKey cacheKey = generateCacheKey(location);

        List<PredictionEntity> cachedPredictionEntityList = userQueryLruCache.get(cacheKey);

        if (cachedPredictionEntityList == null) {
            googleMapsApi.getAutocomplete(
                    query,
                    location,
                    radius,
                    types,
                    API_KEY
                )
                .enqueue(
                    new Callback<AutocompleteSuggestionResponses>() {
                        @Override
                        public void onResponse(
                            @NonNull Call<AutocompleteSuggestionResponses> call,
                            @NonNull Response<AutocompleteSuggestionResponses> response
                        ) {
                            if (response.isSuccessful() &&
                                response.body() != null &&
                                response.body().getStatus() != null &&
                                response.body().getStatus().equals("OK")
                            ) {

                                List<PredictionEntity> predictionEntityList = mapToPredictionEntityList(response.body());
                                if (predictionEntityList != null) {
                                    userQueryLruCache.put(cacheKey, predictionEntityList);
                                    predictionEntities.setValue(predictionEntityList);
                                }
                            } else {
                                predictionEntities.setValue(null);
                            }
                        }

                        @Override
                        public void onFailure(
                            @NonNull Call<AutocompleteSuggestionResponses> call,
                            @NonNull Throwable t
                        ) {
                            t.printStackTrace();
                        }
                    }
                );
        } else {
            predictionEntities.setValue(Collections.emptyList());
        }
        return predictionEntities;
    }

    private List<PredictionEntity> mapToPredictionEntityList(@Nullable AutocompleteSuggestionResponses response) {
        List<PredictionEntity> results = new ArrayList<>();

        if (response != null && response.getPredictions() != null) {
            for (PredictionsItem prediction : response.getPredictions()) {
                String placeId;
                if (prediction.getPlaceId() != null) {
                    placeId = prediction.getPlaceId();
                } else {
                    placeId = null;
                }

                String name;
                if (prediction.getStructuredFormatting() != null &&
                    prediction.getStructuredFormatting().getMainText() != null
                ) {
                    name = prediction.getStructuredFormatting().getMainText();
                } else {
                    name = null;
                }

                results.add(
                    new PredictionEntity(placeId, name)
                );
            }
        } else {
            return null;
        }
        return results;
    }

    private LocationKey generateCacheKey(@NonNull String location) {
        String[] coordinatesArr = location.split(",");
        Double latitude = Double.parseDouble(coordinatesArr[1]);
        Double longitude = Double.parseDouble(coordinatesArr[0]);
        LocationEntity locationEntity = new LocationEntity(latitude, longitude);

        return new LocationKey(locationEntity);
    }
}
