package com.emplk.go4lunch.ui.restaurant_detail;


import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.content.res.Resources;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.authentication.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.detail.GetDetailsRestaurantWrapperUseCase;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantEntity;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;
import com.emplk.go4lunch.domain.favorite_restaurant.AddFavoriteRestaurantUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.RemoveFavoriteRestaurantUseCase;
import com.emplk.go4lunch.domain.user.use_case.GetUserInfoUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantDetailViewModel extends ViewModel {


    @NonNull
    private final Resources resources;

    private final MediatorLiveData<RestaurantDetailViewState> restaurantDetailViewStateMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<String> restaurantIdMutableLiveData = new MutableLiveData<>();

    @NonNull
    private final GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase;

    @NonNull
    private final AddFavoriteRestaurantUseCase addFavoriteRestaurantUseCase;

    @NonNull
    private final RemoveFavoriteRestaurantUseCase removeFavoriteRestaurantUseCase;

    @NonNull
    private final GetUserInfoUseCase getUserInfoUseCase;

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Nullable
    private final LoggedUserEntity loggedUserEntity;


    @Inject
    public RestaurantDetailViewModel(
        @NonNull GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase,
        @NonNull Resources resources,
        @NonNull AddFavoriteRestaurantUseCase addFavoriteRestaurantUseCase,
        @NonNull RemoveFavoriteRestaurantUseCase removeFavoriteRestaurantUseCase,
        @NonNull GetUserInfoUseCase getUserInfoUseCase,
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase
    ) {
        this.getDetailsRestaurantWrapperUseCase = getDetailsRestaurantWrapperUseCase;
        this.resources = resources;
        this.addFavoriteRestaurantUseCase = addFavoriteRestaurantUseCase;
        this.removeFavoriteRestaurantUseCase = removeFavoriteRestaurantUseCase;
        this.getUserInfoUseCase = getUserInfoUseCase;
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;

        loggedUserEntity = getCurrentLoggedUserUseCase.invoke();

        LiveData<String> userIdLiveData = Transformations.map(getUserInfoUseCase.invoke(), userInfo -> {
                if (userInfo != null) {
                    return userInfo.getLoggedUserEntity().getUserId();
                }
                return null;
            }
        );

        LiveData<DetailsRestaurantWrapper> detailsRestaurantWrapperLiveData = Transformations.switchMap(restaurantIdMutableLiveData, restaurantId -> {
                return getDetailsRestaurantWrapperUseCase.invoke(restaurantId);
            }
        );

        LiveData<Boolean> isRestaurantLikedLiveData = Transformations.switchMap(getUserInfoUseCase.invoke(), userInfo -> {
                return Transformations.map(restaurantIdMutableLiveData, restaurantId -> {
                        if (restaurantId != null) {
                            return userInfo.getFavoriteRestaurantIds().containsKey(restaurantId);
                        } else if (userInfo.getFavoriteRestaurantIds().isEmpty()) {
                            return false;
                        }
                        return null;
                    }
                );
            }
        );

        restaurantDetailViewStateMediatorLiveData.addSource(restaurantIdMutableLiveData, restaurantId -> {
                combine(restaurantId, userIdLiveData.getValue(), detailsRestaurantWrapperLiveData.getValue(), isRestaurantLikedLiveData.getValue());
            }
        );

        restaurantDetailViewStateMediatorLiveData.addSource(userIdLiveData, userId -> {
                combine(restaurantIdMutableLiveData.getValue(), userId, detailsRestaurantWrapperLiveData.getValue(), isRestaurantLikedLiveData.getValue());
            }
        );

        restaurantDetailViewStateMediatorLiveData.addSource(detailsRestaurantWrapperLiveData, detailsRestaurantWrapper -> {
                combine(restaurantIdMutableLiveData.getValue(), userIdLiveData.getValue(), detailsRestaurantWrapper, isRestaurantLikedLiveData.getValue());
            }
        );

        restaurantDetailViewStateMediatorLiveData.addSource(isRestaurantLikedLiveData, isRestaurantLiked -> {
                combine(restaurantIdMutableLiveData.getValue(), userIdLiveData.getValue(), detailsRestaurantWrapperLiveData.getValue(), isRestaurantLiked);
            }
        );
    }

    private void combine(
        @Nullable String restaurantId,
        @Nullable String userId,
        @Nullable DetailsRestaurantWrapper detailsRestaurantWrapper,
        @Nullable Boolean isRestaurantLiked
    ) {
        if (restaurantId == null || userId == null) {
            return;
        }

        if (detailsRestaurantWrapper instanceof DetailsRestaurantWrapper.Loading) {
            restaurantDetailViewStateMediatorLiveData.setValue(new RestaurantDetailViewState(
                    restaurantId,
                    "",    // ugly...
                    "",
                    "",
                    0f,
                    "",
                    "",
                    isRestaurantLiked,
                    false,
                    false,
                    true,    // ...but I need it for this somehow
                    false,
                    false
                )
            );
        }

        if (detailsRestaurantWrapper instanceof DetailsRestaurantWrapper.Success) {
            DetailsRestaurantEntity detailsRestaurantEntity = ((DetailsRestaurantWrapper.Success) detailsRestaurantWrapper).getDetailsRestaurantEntity();
            restaurantDetailViewStateMediatorLiveData.setValue(new RestaurantDetailViewState(
                    restaurantId,
                    checkIfResponseFieldExist(detailsRestaurantEntity.getRestaurantName()),
                    checkIfResponseFieldExist(detailsRestaurantEntity.getVicinity()),
                    parseRestaurantPictureUrl(detailsRestaurantEntity.getPhotoReferenceUrl()),
                    convertFiveToThreeRating(detailsRestaurantEntity.getRating()),
                    detailsRestaurantEntity.getPhoneNumber(),
                    detailsRestaurantEntity.getWebsiteUrl(),
                    isRestaurantLiked,
                    false,
                    detailsRestaurantEntity.getVeganFriendly(),
                    false,
                    detailsRestaurantEntity.getPhoneNumber() != null,
                    detailsRestaurantEntity.getWebsiteUrl() != null
                )
            );
        }

        //TODO: handle error case
    }

    public LiveData<RestaurantDetailViewState> getDetailsRestaurantWrapper() {
        return restaurantDetailViewStateMediatorLiveData;
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

    public void getRestaurantId(@NonNull String restaurantId) {
        restaurantIdMutableLiveData.setValue(restaurantId);
    }

    public void onAddFavoriteRestaurant(@NonNull String restaurantId) {
        if (loggedUserEntity != null) {
            addFavoriteRestaurantUseCase.invoke(restaurantId, loggedUserEntity.getUserId());
        }
    }

    public void onRemoveFavoriteRestaurant(@NonNull String restaurantId) {
        if (loggedUserEntity != null) {
            removeFavoriteRestaurantUseCase.invoke(restaurantId, loggedUserEntity.getUserId());
        }
    }
}
