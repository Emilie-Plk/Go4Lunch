package com.emplk.go4lunch.ui.restaurant_detail;


import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.content.res.Resources;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.detail.GetDetailsRestaurantWrapperUseCase;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantEntity;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantDetailViewModel extends ViewModel {


    @NonNull
    private final Resources resources;

    //  private final MediatorLiveData<RestaurantDetailViewState> restaurantDetailViewStateMediatorLiveData = new MediatorLiveData<>();

    private final GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase;

    @Inject
    public RestaurantDetailViewModel(
        GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase,
        @NonNull Resources resources
    ) {
        this.getDetailsRestaurantWrapperUseCase = getDetailsRestaurantWrapperUseCase;
        this.resources = resources;
    }

    public LiveData<RestaurantDetailViewState> getRestaurantDetails(@NonNull String restaurantId) {
        return Transformations.switchMap(getDetailsRestaurantWrapperUseCase.invoke(restaurantId), restaurantDetail -> {
                MutableLiveData<RestaurantDetailViewState> restaurantDetailViewStateMutableLiveData = new MutableLiveData<>();
                if (restaurantDetail instanceof DetailsRestaurantWrapper.Loading) {
                    restaurantDetailViewStateMutableLiveData.setValue(new RestaurantDetailViewState(
                            restaurantId,
                            "",    // ugly...
                            "",
                            "",
                            0f,
                            "",
                            "",
                            false,
                            false,
                            false,
                            true,    // ...but I need it for this somehow
                            false,
                            false
                        )
                    );
                }

                if (restaurantDetail instanceof DetailsRestaurantWrapper.Success) {
                    DetailsRestaurantEntity response = ((DetailsRestaurantWrapper.Success) restaurantDetail).getDetailsRestaurantEntity();
                    restaurantDetailViewStateMutableLiveData.setValue(
                        new RestaurantDetailViewState(
                            restaurantId,
                            checkIfResponseFieldExist(response.getRestaurantName()),
                            checkIfResponseFieldExist(response.getVicinity()),
                            parseRestaurantPictureUrl(response.getPhotoReferenceUrl()),
                            convertFiveToThreeRating(response.getRating()),
                            response.getPhoneNumber(),
                            response.getWebsiteUrl(),
                            true,
                            false,
                            response.getVeganFriendly(),
                            false,
                            response.getPhoneNumber() != null,
                            response.getWebsiteUrl() != null
                        )
                    );
                }
                return restaurantDetailViewStateMutableLiveData;
            }
        );
    }

    private Float convertFiveToThreeRating(@Nullable Float fiveRating) {
        if (fiveRating == null) {
            return 0f;
        } else {
            float convertedRating = Math.round(fiveRating * 2) / 2f;
            return Math.min(3f, convertedRating / 5f * 3f);
        }
    }

    private String parseRestaurantPictureUrl(@Nullable String photoReferenceUrl) {
        if (photoReferenceUrl != null && !photoReferenceUrl.isEmpty()) {
            return resources
                .getString(R.string.google_image_url, photoReferenceUrl, API_KEY);
        } else {
            Uri uri = Uri.parse("android.resource://com.emplk.go4lunch/" + R.drawable.restaurant_table);
            return uri.toString();
        }
    }

    private String checkIfResponseFieldExist(@Nullable String input) {
        return input != null && !input.isEmpty() ? input : resources.getString(R.string.detail_missing_information);
    }
}
