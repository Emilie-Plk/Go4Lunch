package com.emplk.go4lunch.domain.autocomplete.use_case;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class GetUserQueryUseCase {

    @NonNull
    private final PredictionsRepository repository;

    @Inject
    public GetUserQueryUseCase(@NonNull PredictionsRepository repository) {
        this.repository = repository;
    }

    public LiveData<String> invoke() {
        return repository.getUserQueryLiveData();
    }
}
