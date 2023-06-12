package com.emplk.go4lunch.data.autocomplete;

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
import com.emplk.go4lunch.domain.autocomplete.entity.AutocompleteWrapper;
import com.emplk.go4lunch.domain.autocomplete.entity.PredictionEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;

import java.util.ArrayList;
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
    public LiveData<AutocompleteWrapper> getAutocompleteResult(
        @NonNull String input,
        @NonNull String location,
        @NonNull String radius,
        @NonNull String types,
        @NonNull String key
    ) {
        MutableLiveData<AutocompleteWrapper> resultMutableLiveData = new MutableLiveData<>();
        LocationKey cacheKey = generateCacheKey(location);

        List<PredictionEntity> cachedPredictionEntityList = userQueryLruCache.get(cacheKey);

        if (cachedPredictionEntityList == null) {
            googleMapsApi.getAutocomplete(
                    input,
                    location,
                    radius,
                    types,
                    key
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
                                userQueryLruCache.put(cacheKey, predictionEntityList);
                                resultMutableLiveData.setValue(new AutocompleteWrapper.Success(predictionEntityList));
                            } else if (response.isSuccessful() &&
                                response.body() != null &&
                                response.body().getStatus() != null &&
                                response.body().getStatus().equals("ZERO_RESULTS")
                            ) {
                                List<PredictionEntity> emptyList = new ArrayList<>();
                                userQueryLruCache.put(cacheKey, emptyList);
                                resultMutableLiveData.setValue(new AutocompleteWrapper.NoResults());
                            }
                        }

                        @Override
                        public void onFailure(
                            @NonNull Call<AutocompleteSuggestionResponses> call,
                            @NonNull Throwable t
                        ) {
                            resultMutableLiveData.setValue(new AutocompleteWrapper.Error(t));
                        }
                    }
                );
        } else {
            resultMutableLiveData.setValue(new AutocompleteWrapper.Success(cachedPredictionEntityList));
        }
        return resultMutableLiveData;
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

                String vicinity;
                if (prediction.getStructuredFormatting() != null &&
                    prediction.getStructuredFormatting().getSecondaryText() != null) {
                    vicinity = prediction.getStructuredFormatting().getSecondaryText();
                } else {
                    vicinity = null;
                }

                results.add(
                    new PredictionEntity(placeId, name, vicinity)
                );
            }
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
