package com.example.go4lunch.data.API.nearby_search_restaurants;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.data.API.GoogleMapsApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NearbySearchRepository {
    private final GoogleMapsApi googleMapsApi;

    @Inject
    public NearbySearchRepository(GoogleMapsApi googleMapsApi) {
        this.googleMapsApi = googleMapsApi;
    }


    public LiveData<NearbySearchWrapper> getNearbyRestaurants(
        @NonNull String keyword,
        @NonNull String location,
        int radius,
        @NonNull String type,
        @NonNull String key,
        int limit) {

        MutableLiveData<NearbySearchWrapper> resultMutableLiveData = new MutableLiveData<>();
        resultMutableLiveData.setValue(new NearbySearchWrapper.Loading());

        googleMapsApi.getNearby(keyword, location, radius, type, key, limit).enqueue(new Callback<NearbySearchResponse>() {
            @Override
            public void onResponse(Call<NearbySearchResponse> call, Response<NearbySearchResponse> response) {
                if (response.isSuccessful()) {
                    List<NearbySearchResult> nearbySearchResults = NearbySearchResult.fromNearbySearchResponse(response.body());
                    if (nearbySearchResults.size() > limit) {
                        nearbySearchResults = nearbySearchResults.subList(0, limit);
                    }
                    resultMutableLiveData.setValue(new NearbySearchWrapper.Success(nearbySearchResults));
                } else {
                    resultMutableLiveData.setValue(new NearbySearchWrapper.Error(new Throwable("API call failed")));
                }
            }

            @Override
            public void onFailure(Call<NearbySearchResponse> call, Throwable t) {
                resultMutableLiveData.setValue(new NearbySearchWrapper.Error(t));
            }
        });

        return resultMutableLiveData;
    }

    }
