package com.emplk.go4lunch.domain.searchview;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface SearchViewPredictionRepository {

    void savePredictions(List<PredictionEntity> predictions);

    LiveData<List<PredictionEntity>> getPredictionsMutableLiveData();

    void resetPredictions();
}
