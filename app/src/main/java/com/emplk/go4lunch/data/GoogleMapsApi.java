package com.emplk.go4lunch.data;

import com.emplk.go4lunch.data.autocomplete.autocomplete_response.AutocompleteSuggestionResponses;
import com.emplk.go4lunch.data.details.details_restaurant_response.DetailsRestaurantResponse;
import com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsApi {
    @GET("nearbysearch/json")
    Call<NearbySearchResponse> getNearby(
        @Query("location") String location,
        @Query("type") String type,
        @Query("radius") int radius,
        @Query("key") String apiKey
    );

    @GET("details/json")
    Call<DetailsRestaurantResponse> getPlaceDetails(
        @Query("place_id") String placeId,
        @Query("key") String apiKey
    );

    @GET("autocomplete/json")
    Call<AutocompleteSuggestionResponses> getAutocomplete(
        @Query("input") String input,
        @Query("location") String location,
        @Query("radius") String radius,
        @Query("types") String types,
        @Query("key") String apiKey
    );
}
