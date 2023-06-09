package com.emplk.go4lunch.domain.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantEntity;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;
import com.emplk.go4lunch.domain.user.RestaurantEntity;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.inject.Inject;

public class GetRestaurantEntityUseCase {

    @NonNull
    private final GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase;

    @Inject
    public GetRestaurantEntityUseCase(
        @NonNull GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase
    ) {
        this.getDetailsRestaurantWrapperUseCase = getDetailsRestaurantWrapperUseCase;
    }

    public LiveData<RestaurantEntity> invoke(String restaurantId) {
        LiveData<DetailsRestaurantWrapper> detailsRestaurantWrapperLiveData = getDetailsRestaurantWrapperUseCase.invoke(restaurantId);

        return Transformations.map(detailsRestaurantWrapperLiveData, detailsRestaurantWrapper -> {
                if (detailsRestaurantWrapper instanceof DetailsRestaurantWrapper.Success) {
                    DetailsRestaurantEntity detailsRestaurantEntity = ((DetailsRestaurantWrapper.Success) detailsRestaurantWrapper).getDetailsRestaurantEntity();
                    return new RestaurantEntity(
                        detailsRestaurantEntity.getPlaceId(),
                        detailsRestaurantEntity.getRestaurantName(),
                        detailsRestaurantEntity.getVicinity(),
                        detailsRestaurantEntity.getPhotoReferenceUrl()
                    );
                } else {
                    return null;
                }
            }
        );
    }
}

