package com.emplk.go4lunch.data.autocomplete;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.util.LruCache;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.data.GoogleMapsApi;
import com.emplk.go4lunch.data.autocomplete.autocomplete_response.AutocompleteSuggestionResponses;
import com.emplk.go4lunch.data.nearbySearchRestaurants.LocationKey;
import com.emplk.go4lunch.domain.autocomplete.AutocompleteRepository;
import com.emplk.go4lunch.domain.gps.LocationEntity;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class AutocompleteRepositoryGooglePlaces implements AutocompleteRepository {

    private static final String RADIUS = "1500";
    private static final String TYPES = "restaurant";

    private final LruCache<LocationKey, UserQuery> userQueryLruCache;

    @NonNull
    private final GoogleMapsApi googleMapsApi;

    @Inject
    public AutocompleteRepositoryGooglePlaces(@NonNull GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
        userQueryLruCache = new LruCache<>(400);
    }

    @Override
    public LiveData<AutocompleteSuggestionResponses> getAutocompleteResult(@NonNull String input, @NonNull String location) {
        MutableLiveData<String> userSearchInput = new MutableLiveData<>();

        LocationKey locationKey = generateCacheKey(location);

        googleMapsApi.getAutocomplete(
                input,
                location,
                RADIUS,
                TYPES,
                API_KEY
            )
            .enqueue(
                new Callback<AutocompleteSuggestionResponses>() {
                    @Override
                    public void onResponse(
                        @NonNull Call<AutocompleteSuggestionResponses> call,
                        @NonNull Response<AutocompleteSuggestionResponses> response
                    ) {
                        if (response.isSuccessful()) {
                            AutocompleteSuggestionResponses body = response.body();
                            if (body != null) {
                                //         userQueryLruCache.put(locationKey, new UserQuery(input, body.getPredictions()));
                                userSearchInput.setValue(input);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<AutocompleteSuggestionResponses> call, @NonNull Throwable t) {
                    }
                }
            );
        return null;
    }

    private LocationKey generateCacheKey(
        @NonNull String location
    ) {
        String[] coordinatesArr = location.split(",");
        Double latitude = Double.parseDouble(coordinatesArr[1]);
        Double longitude = Double.parseDouble(coordinatesArr[0]);
        LocationEntity locationEntity = new LocationEntity(latitude, longitude);

        return new LocationKey(locationEntity);
    }
}
