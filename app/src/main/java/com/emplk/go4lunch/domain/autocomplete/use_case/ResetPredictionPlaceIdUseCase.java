package com.emplk.go4lunch.domain.autocomplete.use_case;

import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class ResetPredictionPlaceIdUseCase {

    @NonNull
    private final PredictionsRepository repository;

    @Inject
    public ResetPredictionPlaceIdUseCase(@NonNull PredictionsRepository repository) {
        this.repository = repository;
    }

    public void invoke() {
        repository.resetPredictionPlaceIdQuery();
    }
}
