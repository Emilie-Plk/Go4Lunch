package com.emplk.go4lunch.domain.autocomplete;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import java.util.List;

public interface PredictionsRepository {

    LiveData<List<PredictionEntity>> getPredictionsLiveData(
        @NonNull String query,
        @NonNull String location,
        int radius,
        @NonNull String types
    );

    void savePredictionPlaceId(@NonNull String placeId);

    LiveData<String> getPredictionPlaceIdLiveData();

    void resetPredictionPlaceIdQuery();
}
