package com.emplk.go4lunch.domain.searchview.use_case;

import com.emplk.go4lunch.domain.searchview.SearchViewQueryRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class ResetPredictionsUseCase {

    @NonNull
    private final SearchViewQueryRepository repository;

    @Inject
    public ResetPredictionsUseCase(@NonNull SearchViewQueryRepository repository) {
        this.repository = repository;
    }

    public void invoke() {
        repository.resetPredictions();
    }
}
