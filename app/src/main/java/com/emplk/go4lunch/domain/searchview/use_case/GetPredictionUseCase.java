package com.emplk.go4lunch.domain.searchview.use_case;

import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.searchview.PredictionEntity;
import com.emplk.go4lunch.domain.searchview.SearchViewQueryRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

import javax.inject.Inject;

public class GetPredictionUseCase {

    @NonNull
    private final SearchViewQueryRepository repository;

    @Inject
    public GetPredictionUseCase(@NonNull SearchViewQueryRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<PredictionEntity>> invoke() {
        return repository.getPredictionsMutableLiveData();
    }
}
