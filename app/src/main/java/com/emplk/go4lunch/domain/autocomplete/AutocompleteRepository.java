package com.emplk.go4lunch.domain.autocomplete;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.autocomplete.entity.PredictionEntity;

import java.util.List;

public interface AutocompleteRepository {


    LiveData<List<PredictionEntity>> getAutocompleteResult(
        @NonNull String query,
        @NonNull String location,
        int radius,
        @NonNull String types
    );
}
