package com.emplk.go4lunch.domain.searchview.use_case;

import com.emplk.go4lunch.domain.searchview.PredictionEntity;
import com.emplk.go4lunch.domain.searchview.PredictionsRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import javax.inject.Inject;

public class SavePredictionsUseCase {

    @NonNull
    private final PredictionsRepository predictionsRepository;

    @Inject
    public SavePredictionsUseCase(@NonNull PredictionsRepository predictionsRepository) {
        this.predictionsRepository = predictionsRepository;
    }

    public void invoke(List<PredictionEntity> predictions) {
        predictionsRepository.saveUserQuery(predictions);
    }
}
