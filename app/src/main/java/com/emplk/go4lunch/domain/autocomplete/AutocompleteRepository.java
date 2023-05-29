package com.emplk.go4lunch.domain.autocomplete;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.autocomplete.entity.AutocompleteWrapper;

public interface AutocompleteRepository {
    LiveData<AutocompleteWrapper> getAutocompleteResult(
        @NonNull String input,
        @NonNull String location,
        @NonNull String radius,
        @NonNull String types,
        @NonNull String key
    );
}
