package com.emplk.go4lunch.ui.restaurant_detail;

import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.data.details.DetailsRestaurantRepository;
import com.emplk.go4lunch.data.details.DetailsRestaurantWrapper;
import com.emplk.go4lunch.data.details.detailsRestaurantResponse.Result;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantDetailViewModel extends ViewModel {

    @NonNull
    private final DetailsRestaurantRepository detailsRestaurantRepository;

    @NonNull
    private final Application application;

    private final MediatorLiveData<RestaurantDetailViewState> restaurantDetailViewStateMediatorLiveData = new MediatorLiveData<>();

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
                    "attends",
                    "attends",
                    "attends",
                    "attends",
                    0.5f,
                    "attends",
                    "attends",
                    false,
                    false,
                    false
                ));
            }

                if (restaurantDetail instanceof DetailsRestaurantWrapper.Success) {
                    Result response = ((DetailsRestaurantWrapper.Success) restaurantDetail).getResponse().getResult();
                    restaurantDetailViewStateMutableLiveData.setValue(new RestaurantDetailViewState(
                        response.getPlaceId(),
                        response.getName(),
                        response.getVicinity(),
                        parseRestaurantPictureUrl(response.getPhotos().get(0).getPhotoReference()),
                        convertFiveToThreeRating(response.getRating()),
                        response.getInternationalPhoneNumber(),
                        response.getWebsite(),
                        true,
                        false,
                        response.isServesVegetarianFood()
                    ));
                }
                return restaurantDetailViewStateMutableLiveData;
            }
        );
    }

    public float convertFiveToThreeRating(Float fiveRating) {
            float convertedRating = Math.round(fiveRating * 2) / 2f; // round to nearest 0.5
            return Math.min(3f, convertedRating / 5f * 3f); // convert 3 -> 5 with steps of 0.5
    }

    private String parseRestaurantPictureUrl(String photoReferenceUrl) {
        if (photoReferenceUrl != null) {
            return String.format(application
                .getApplicationContext()
                .getString(R.string.google_image_url), photoReferenceUrl, API_KEY);
        } else {
            Uri uri = Uri.parse("android.resource://com.emplk.go4lunch/" + ResourcesCompat.getDrawable(application.getResources(), R.drawable.restaurant_table, null));
            return uri.toString();
        }
    }
}
