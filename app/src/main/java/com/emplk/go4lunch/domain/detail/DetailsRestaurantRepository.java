package com.emplk.go4lunch.domain.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface DetailsRestaurantRepository {

    LiveData<DetailsRestaurantWrapper> getRestaurantDetails(
        @NonNull String placeId,
        @NonNull String apiKey
    );
}
