package com.emplk.go4lunch.domain.autocomplete;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.data.autocomplete.autocomplete_response.AutocompleteResponse;

public interface AutocompleteRepository {

    LiveData<AutocompleteResponse> getAutocompleteResult(@NonNull String input);
}
