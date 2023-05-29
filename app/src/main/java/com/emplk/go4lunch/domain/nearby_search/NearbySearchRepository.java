package com.emplk.go4lunch.domain.nearby_search;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;

public interface NearbySearchRepository {

    LiveData<NearbySearchWrapper> getNearbyRestaurants(
        @NonNull String location,
        @NonNull String type,
        @NonNull String rankBy,
        @NonNull String key
    );
}
