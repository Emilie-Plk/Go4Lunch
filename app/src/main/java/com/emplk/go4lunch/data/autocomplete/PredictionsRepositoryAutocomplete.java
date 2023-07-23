package com.emplk.go4lunch.data.autocomplete;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.util.Log;
import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.data.GooglePlacesApi;
import com.emplk.go4lunch.data.autocomplete.response.AutocompletePredictionResponse;
import com.emplk.go4lunch.data.autocomplete.response.PredictionsItem;
import com.emplk.go4lunch.domain.autocomplete.PredictionEntity;
import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class PredictionsRepositoryAutocomplete implements PredictionsRepository {

    @NonNull
    private final GooglePlacesApi googleMapsApi;

    private final LruCache<String, List<PredictionEntity>> predictionEntitiesLruCache;
    private final MutableLiveData<String> predictionPlaceIdMutableLiveData = new MutableLiveData<>();

    @Inject
    public PredictionsRepositoryAutocomplete(@NonNull GooglePlacesApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        predictionEntitiesLruCache = new LruCache<>(400);
    }

    @Override
    public LiveData<List<PredictionEntity>> getPredictionsLiveData(
        @NonNull String input,
        @NonNull Double latitude,
        @NonNull Double longitude,
        int radius,
        @NonNull String types
    ) {
        MutableLiveData<List<PredictionEntity>> predictionEntitiesLiveData = new MutableLiveData<>();
        Log.d("PredictionsRepository", "predictionEntitiesLruCache size is:" + predictionEntitiesLruCache.size());

        String cacheKey = input + latitude + longitude + radius + types;

        List<PredictionEntity> cachedPredictionEntityList = predictionEntitiesLruCache.get(cacheKey);

        String location = latitude + "," + longitude;

        if (cachedPredictionEntityList == null) {
            googleMapsApi.getPlacesAutocomplete(
                input,
                location,
                radius,
                types,
                API_KEY
            ).enqueue(
                new Callback<AutocompletePredictionResponse>() {
                    @Override
                    public void onResponse(
                        @NonNull Call<AutocompletePredictionResponse> call,
                        @NonNull Response<AutocompletePredictionResponse> response
                    ) {
                        if (response.isSuccessful() &&
                            response.body() != null &&
                            response.body().getPredictions() != null
                        ) {
                            List<PredictionEntity> predictionEntities = mapToPredictionEntityList(response.body());
                            if (predictionEntities != null) {
                                predictionEntitiesLruCache.put(cacheKey, predictionEntities);
                                predictionEntitiesLiveData.setValue(predictionEntities);
                            }
                        } else {
                            predictionEntitiesLiveData.setValue(null);
                        }
                    }

                    @Override
                    public void onFailure(
                        @NonNull Call<AutocompletePredictionResponse> call,
                        @NonNull Throwable t
                    ) {
                        predictionEntitiesLiveData.setValue(null);
                        t.printStackTrace();
                    }
                }
            );
        } else {
            predictionEntitiesLiveData.setValue(cachedPredictionEntityList);
        }
        return predictionEntitiesLiveData;
    }

    private List<PredictionEntity> mapToPredictionEntityList(@Nullable AutocompletePredictionResponse response) {
        List<PredictionEntity> results = new ArrayList<>();

        if (response != null && response.getPredictions() != null) {
            for (PredictionsItem prediction : response.getPredictions()) {
                if (prediction.getPlaceId() != null &&
                    prediction.getStructuredFormatting() != null &&
                    prediction.getStructuredFormatting().getMainText() != null) {
                    results.add(
                        new PredictionEntity(
                            prediction.getPlaceId(),
                            prediction.getStructuredFormatting().getMainText()
                        )
                    );
                } else {
                    return null;
                }
            }
        } else {
            return null;
        }
        return results;
    }

    @Override
    public void savePredictionPlaceId(@NonNull String query) {
        predictionPlaceIdMutableLiveData.setValue(query);
    }

    @Override
    public LiveData<String> getPredictionPlaceIdLiveData() {
        return predictionPlaceIdMutableLiveData;
    }

    @Override
    public void resetPredictionPlaceIdQuery() {
        predictionPlaceIdMutableLiveData.setValue(null);
    }
}
