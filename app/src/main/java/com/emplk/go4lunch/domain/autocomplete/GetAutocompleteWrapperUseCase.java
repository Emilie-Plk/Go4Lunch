package com.emplk.go4lunch.domain.autocomplete;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.autocomplete.entity.AutocompleteWrapper;
import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;

import javax.inject.Inject;

public class GetAutocompleteWrapperUseCase {

    @NonNull
    private final AutocompleteRepository autocompleteRepository;

    @NonNull
    private final GetCurrentLocationUseCase getCurrentLocationUseCase;

    @Inject
    public GetAutocompleteWrapperUseCase(
        @NonNull AutocompleteRepository autocompleteRepository,
        @NonNull GetCurrentLocationUseCase getCurrentLocationUseCase
    ) {
        this.autocompleteRepository = autocompleteRepository;
        this.getCurrentLocationUseCase = getCurrentLocationUseCase;
    }

    public LiveData<AutocompleteWrapper> invoke(@NonNull String input) {
        return Transformations.switchMap(getCurrentLocationUseCase.invoke(), location -> {
                return autocompleteRepository.getAutocompleteResult(
                    input,
                    location.getLatitude() + "," + location.getLongitude(),
                    "1500",
                    "restaurant",
                    API_KEY
                );
            }
        );
    }
}
