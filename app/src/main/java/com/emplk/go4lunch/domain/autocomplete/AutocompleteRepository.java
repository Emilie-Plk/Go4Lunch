package com.emplk.go4lunch.domain.autocomplete;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.data.autocomplete.autocomplete_response.AutocompleteSuggestionResponses;

public interface AutocompleteRepository {
    LiveData<AutocompleteSuggestionResponses> getAutocompleteResult(@NonNull String input, @NonNull String location);
}
