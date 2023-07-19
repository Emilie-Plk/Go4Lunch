package com.emplk.go4lunch.domain.autocomplete;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.autocomplete.entity.PredictionEntity;

import java.util.List;

import javax.inject.Inject;

public class GetAutocompletePredictionsUseCase {

    private static final int RADIUS = 1_000;
    private static final String TYPE = "restaurant";

    @NonNull
    private final AutocompleteRepository autocompleteRepository;

    @Inject
    public GetAutocompletePredictionsUseCase(@NonNull AutocompleteRepository autocompleteRepository) {
        this.autocompleteRepository = autocompleteRepository;
    }

    public LiveData<List<PredictionEntity>> invoke(
        String query,
        String location
    ) {
        return autocompleteRepository.getAutocompleteResult(
            query,
            location,
            RADIUS,
            TYPE
        );
    }
}
