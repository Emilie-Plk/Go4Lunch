package com.emplk.go4lunch;

import com.emplk.go4lunch.data.details.detailsRestaurantResponse.DetailsRestaurantResponse;
import com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsApi {
    @GET("nearbysearch/json")
    Call<NearbySearchResponse> getNearby(
        @Query("location") String location,
        @Query("type") String type,
        @Query("keyword") String keyword,
        @Query("rankby")  String rankBy,
        @Query("key") String apiKey
    );

    @GET("details/json")
    Call<DetailsRestaurantResponse> getDetails(
        @Query("place_id") String placeId,
        @Query("key") String apiKey
    );

}
