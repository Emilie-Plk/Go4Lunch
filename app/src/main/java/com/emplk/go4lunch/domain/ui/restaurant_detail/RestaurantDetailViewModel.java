package com.emplk.go4lunch.domain.ui.restaurant_detail;


import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.data.details.DetailsRestaurantEntity;
import com.emplk.go4lunch.data.details.DetailsRestaurantRepository;
import com.emplk.go4lunch.data.details.DetailsRestaurantWrapper;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantDetailViewModel extends ViewModel {

    @NonNull
    private final DetailsRestaurantRepository detailsRestaurantRepository;

    @NonNull
    private final Application application;

    //  private final MediatorLiveData<RestaurantDetailViewState> restaurantDetailViewStateMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public RestaurantDetailViewModel(
        @NonNull DetailsRestaurantRepository detailsRestaurantRepository,
        @NonNull Application application) {
        this.detailsRestaurantRepository = detailsRestaurantRepository;
        this.application = application;
    }

    public LiveData<RestaurantDetailViewState> getRestaurantDetails(@NonNull String restaurantId) {
        return Transformations.switchMap(detailsRestaurantRepository.getRestaurantDetails(restaurantId, API_KEY), restaurantDetail -> {
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
                    DetailsRestaurantEntity response = ((DetailsRestaurantWrapper.Success) restaurantDetail).getResponse();
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

    public float convertFiveToThreeRating(@Nullable Float fiveRating) {
        return fiveRating == null ? 0f : Math.round(fiveRating * 2) / 2f;
    }

    private String parseRestaurantPictureUrl(@Nullable String photoReferenceUrl) {
        if (photoReferenceUrl != null && !photoReferenceUrl.isEmpty()) {
            return String.format(application
                .getApplicationContext()
                .getString(R.string.google_image_url), photoReferenceUrl, API_KEY);
        } else {
            Uri uri = Uri.parse("android.resource://com.emplk.go4lunch/" + R.drawable.restaurant_table);
            return uri.toString();
        }
    }

    private String checkIfResponseFieldExist(@Nullable String input) {
        return input != null && !input.isEmpty() ? input : application.getString(R.string.detail_missing_information);
    }
}
