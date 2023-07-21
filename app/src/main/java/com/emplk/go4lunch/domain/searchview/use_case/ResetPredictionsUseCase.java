package com.emplk.go4lunch.domain.searchview.use_case;

import com.emplk.go4lunch.domain.searchview.PredictionsRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class ResetPredictionsUseCase {

    @NonNull
    private final PredictionsRepository repository;

    @Inject
    public ResetPredictionsUseCase(@NonNull PredictionsRepository repository) {
        this.repository = repository;
    }

    public void invoke() {
        repository.resetUserQuery();
    }
}
