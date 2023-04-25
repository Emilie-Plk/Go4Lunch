package com.example.go4lunch.data.API;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.go4lunch.data.API.nearby_search_restaurants.NearbySearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleMapsApi {
    @GET("nearbysearch/json")
    Call<NearbySearchResponse> getNearby(
        @Query("location") @NonNull  String location,
        @Query("type") @NonNull String type,
        @Query("keyword") @Nullable String keyword,
        @Query("rankby") @NonNull String rankby,
        @Query("key") @NonNull String key);

}
