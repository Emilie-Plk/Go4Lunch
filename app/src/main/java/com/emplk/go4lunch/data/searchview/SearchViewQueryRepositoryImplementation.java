package com.emplk.go4lunch.data.searchview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.searchview.PredictionEntity;
import com.emplk.go4lunch.domain.searchview.SearchViewQueryRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchViewQueryRepositoryImplementation implements SearchViewQueryRepository {

    private final MutableLiveData<String> searchViewQueryMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<List<PredictionEntity>> predictionsMutableLiveData = new MutableLiveData<>();

    @Inject
    public SearchViewQueryRepositoryImplementation() {
    }

    @Override
    public void saveSearchViewQuery(String query) {
        searchViewQueryMutableLiveData.setValue(query);
    }

    @Override
    public LiveData<String> getSearchViewQuery() {
        return searchViewQueryMutableLiveData;
    }

    @Override
    public void resetSearchViewQuery() {
        searchViewQueryMutableLiveData.setValue(null);
    }

    @Override
    public void savePredictions(List<PredictionEntity> predictions) {
        predictionsMutableLiveData.setValue(predictions);
    }

    @Override
    public LiveData<List<PredictionEntity>> getPredictionsMutableLiveData() {
        return predictionsMutableLiveData;
    }

    @Override
    public void resetPredictions() {
        predictionsMutableLiveData.setValue(null);
    }
}
