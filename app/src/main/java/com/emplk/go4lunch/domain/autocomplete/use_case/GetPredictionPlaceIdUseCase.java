package com.emplk.go4lunch.domain.autocomplete.use_case;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class GetPredictionPlaceIdUseCase {

    @NonNull
    private final PredictionsRepository repository;

    @Inject
    public GetPredictionPlaceIdUseCase(@NonNull PredictionsRepository repository) {
        this.repository = repository;
    }

    public LiveData<String> invoke() {
        return repository.getPredictionPlaceIdLiveData();
    }
}
