package com.emplk.go4lunch.domain.nearby_search;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;

public interface NearbySearchRepository {

    LiveData<NearbySearchWrapper> getNearbyRestaurants(
        @NonNull Double latitude,
        @NonNull Double longitude,
        @NonNull String type,
        int radius
    );
}
