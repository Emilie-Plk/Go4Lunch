package com.emplk.go4lunch.domain.searchview;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface SearchViewQueryRepository {

     void saveSearchViewQuery(String query);

     LiveData<String> getSearchViewQuery();

     void resetSearchViewQuery();

     void savePredictions(List<PredictionEntity> predictions);

     LiveData<List<PredictionEntity>> getPredictionsMutableLiveData();

     void resetPredictions();
}
