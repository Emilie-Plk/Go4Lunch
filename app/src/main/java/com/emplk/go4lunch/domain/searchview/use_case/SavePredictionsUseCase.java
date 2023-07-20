package com.emplk.go4lunch.domain.searchview.use_case;

import com.emplk.go4lunch.domain.searchview.PredictionEntity;
import com.emplk.go4lunch.domain.searchview.SearchViewQueryRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import javax.inject.Inject;

public class SavePredictionsUseCase {

    @NonNull
    private final SearchViewQueryRepository searchViewQueryRepository;

    @Inject
    public SavePredictionsUseCase(@NonNull SearchViewQueryRepository searchViewQueryRepository) {
        this.searchViewQueryRepository = searchViewQueryRepository;
    }

    public void invoke(List<PredictionEntity> predictions) {
        searchViewQueryRepository.savePredictions(predictions);
    }
}
