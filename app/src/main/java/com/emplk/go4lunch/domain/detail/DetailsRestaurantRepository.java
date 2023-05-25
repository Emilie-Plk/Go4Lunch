package com.emplk.go4lunch.domain.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.data.details.DetailsRestaurantWrapper;

public interface DetailsRestaurantRepository {

    LiveData<DetailsRestaurantWrapper> getRestaurantDetails(
        @NonNull String placeId,
        @NonNull String apiKey
    );
}
