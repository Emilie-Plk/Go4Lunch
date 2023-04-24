package com.example.go4lunch.data.API;

import com.example.go4lunch.data.API.nearby_search_restaurants.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsApi {

    @GET("nearbysearch/json")
    Call<NearbySearchResponse> getNearby(
        @Query("keyword") String keyword,
        @Query("location") String location,
        @Query("radius") int radius,
        @Query("type") String type,
        @Query("key") String key,
        @Query("maxResults") int maxResults);


/*    https://maps.googleapis.com/maps/api/place/nearbysearch/json
        ?keyword=cruise
  &location=-33.8670522%2C151.1957362
        &radius=1500
        &type=restaurant
  &key=YOUR_API_KEY*/
}
