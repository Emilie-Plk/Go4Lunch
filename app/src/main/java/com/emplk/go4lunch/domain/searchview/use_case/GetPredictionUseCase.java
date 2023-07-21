package com.emplk.go4lunch.domain.searchview.use_case;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.searchview.PredictionEntity;
import com.emplk.go4lunch.domain.searchview.PredictionsRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import javax.inject.Inject;

public class GetPredictionUseCase {

    @NonNull
    private final PredictionsRepository repository;

    @Inject
    public GetPredictionUseCase(@NonNull PredictionsRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<PredictionEntity>> invoke() {
        return repository.getUserQueryLiveData();
    }
}
