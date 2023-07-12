package com.emplk.go4lunch.domain.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;

import javax.inject.Inject;

public class GetDetailsRestaurantWrapperUseCase {

    @NonNull
    private final DetailsRestaurantRepository detailsRestaurantRepository;

    @Inject
    public GetDetailsRestaurantWrapperUseCase(@NonNull DetailsRestaurantRepository detailsRestaurantRepository) {
        this.detailsRestaurantRepository = detailsRestaurantRepository;
    }

    public LiveData<DetailsRestaurantWrapper> invoke(@NonNull String restaurantId) {
        return detailsRestaurantRepository.getRestaurantDetails(restaurantId);
    }
}
