package com.emplk.go4lunch.domain.autocomplete.use_case;

import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class SaveUserQueryUseCase {

    @NonNull
    private final PredictionsRepository predictionsRepository;

    @Inject
    public SaveUserQueryUseCase(@NonNull PredictionsRepository predictionsRepository) {
        this.predictionsRepository = predictionsRepository;
    }

    public void invoke(@NonNull String query) {
        predictionsRepository.saveUserQuery(query);
    }
}
