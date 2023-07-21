package com.emplk.go4lunch.domain.autocomplete.use_case;

import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class SavePredictionPlaceIdUseCase {

    @NonNull
    private final PredictionsRepository predictionsRepository;

    @Inject
    public SavePredictionPlaceIdUseCase(@NonNull PredictionsRepository predictionsRepository) {
        this.predictionsRepository = predictionsRepository;
    }

    public void invoke(@NonNull String placeId) {
        predictionsRepository.savePredictionPlaceId(placeId);
    }
}
