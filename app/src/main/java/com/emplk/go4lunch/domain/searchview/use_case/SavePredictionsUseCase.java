package com.emplk.go4lunch.domain.searchview.use_case;

import com.emplk.go4lunch.domain.searchview.PredictionEntity;
import com.emplk.go4lunch.domain.searchview.SearchViewPredictionRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import javax.inject.Inject;

public class SavePredictionsUseCase {

    @NonNull
    private final SearchViewPredictionRepository searchViewPredictionRepository;

    @Inject
    public SavePredictionsUseCase(@NonNull SearchViewPredictionRepository searchViewPredictionRepository) {
        this.searchViewPredictionRepository = searchViewPredictionRepository;
    }

    public void invoke(List<PredictionEntity> predictions) {
        searchViewPredictionRepository.savePredictions(predictions);
    }
}
