package com.emplk.go4lunch.data.searchview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.searchview.PredictionEntity;
import com.emplk.go4lunch.domain.searchview.SearchViewPredictionRepository;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SearchViewPredictionRepositoryImplementation implements SearchViewPredictionRepository {

    private final MutableLiveData<List<PredictionEntity>> predictionsMutableLiveData = new MutableLiveData<>();

    @Inject
    public SearchViewPredictionRepositoryImplementation() {
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
