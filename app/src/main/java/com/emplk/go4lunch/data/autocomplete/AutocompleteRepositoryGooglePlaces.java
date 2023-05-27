package com.emplk.go4lunch.data.autocomplete;

import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.data.GoogleMapsApi;
import com.emplk.go4lunch.data.autocomplete.autocomplete_response.AutocompleteSuggestionResponse;
import com.emplk.go4lunch.data.nearbySearchRestaurants.LocationKey;
import com.emplk.go4lunch.domain.autocomplete.AutocompleteRepository;
import com.emplk.go4lunch.domain.gps.LocationEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

// @Singleton
public class AutocompleteRepositoryGooglePlaces implements AutocompleteRepository {
    @Override
    public LiveData<AutocompleteSuggestionResponse> getAutocompleteResult(@NonNull String input) {
        return null;
    }

/*
    private final LruCache<LocationKey, UserQuery> userQueryLruCache;

    @NonNull
    private final GoogleMapsApi googleMapsApi;

    @Inject
    public AutocompleteRepositoryGooglePlaces(@NonNull GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        userQueryLruCache = new LruCache<>(400);
    }

    @Override
    public LiveData<AutocompleteSuggestionResponse> getAutocompleteResult(@NonNull String input, @NonNull LocationEntity location) {
        private final MutableLiveData<String> userSearchInput = new MutableLiveData<>();

        LocationKey locationKey = new LocationKey();*/
  //  }
}
