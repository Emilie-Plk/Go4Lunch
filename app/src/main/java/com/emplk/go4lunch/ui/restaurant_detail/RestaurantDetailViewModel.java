package com.emplk.go4lunch.ui.restaurant_detail;


import static com.emplk.go4lunch.BuildConfig.API_KEY;

import android.content.res.Resources;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.detail.GetDetailsRestaurantWrapperUseCase;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantEntity;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;
import com.emplk.go4lunch.domain.favorite_restaurant.AddFavoriteRestaurantUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.RemoveFavoriteRestaurantUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
import com.emplk.go4lunch.domain.user.use_case.restaurant_choice.AddUserRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.user.use_case.restaurant_choice.RemoveUserRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.workmate.GetWorkmateEntitiesGoingToSameRestaurantUseCase;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;
import com.emplk.go4lunch.ui.utils.RestaurantFavoriteState;
import com.emplk.go4lunch.ui.workmate_list.WorkmatesViewStateItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantDetailViewModel extends ViewModel {

    @NonNull
    private final Resources resources;

    private final MediatorLiveData<RestaurantDetailViewState> restaurantDetailViewStateMediatorLiveData = new MediatorLiveData<>();

    private final AddFavoriteRestaurantUseCase addFavoriteRestaurantUseCase;

    @NonNull
    private final RemoveFavoriteRestaurantUseCase removeFavoriteRestaurantUseCase;

    @NonNull
    private final AddUserRestaurantChoiceUseCase addUserRestaurantChoiceUseCase;

    @NonNull
    private final RemoveUserRestaurantChoiceUseCase removeUserRestaurantChoiceUseCase;

    @NonNull
    private final GetWorkmateEntitiesGoingToSameRestaurantUseCase getWorkmateEntitiesGoingToSameRestaurantUseCase;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;


    private final String restaurantId;


    @Inject
    public RestaurantDetailViewModel(
        @NonNull GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase,
        @NonNull Resources resources,
        @NonNull AddFavoriteRestaurantUseCase addFavoriteRestaurantUseCase,
        @NonNull RemoveFavoriteRestaurantUseCase removeFavoriteRestaurantUseCase,
        @NonNull AddUserRestaurantChoiceUseCase addUserRestaurantChoiceUseCase,
        @NonNull RemoveUserRestaurantChoiceUseCase removeUserRestaurantChoiceUseCase,
        @NonNull GetWorkmateEntitiesGoingToSameRestaurantUseCase getWorkmateEntitiesGoingToSameRestaurantUseCase,
        @NonNull GetUserEntityUseCase getUserEntityUseCase,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase,
        @NonNull SavedStateHandle savedStateHandle
    ) {
        this.resources = resources;
        this.addFavoriteRestaurantUseCase = addFavoriteRestaurantUseCase;
        this.removeFavoriteRestaurantUseCase = removeFavoriteRestaurantUseCase;
        this.addUserRestaurantChoiceUseCase = addUserRestaurantChoiceUseCase;
        this.removeUserRestaurantChoiceUseCase = removeUserRestaurantChoiceUseCase;
        this.getWorkmateEntitiesGoingToSameRestaurantUseCase = getWorkmateEntitiesGoingToSameRestaurantUseCase;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;

        restaurantId = savedStateHandle.get(RestaurantDetailActivity.KEY_RESTAURANT_ID);


        LiveData<DetailsRestaurantWrapper> detailsRestaurantWrapperLiveData = getDetailsRestaurantWrapperUseCase.invoke(restaurantId);
        LiveData<UserEntity> currentUserEntityLiveData = getUserEntityUseCase.invoke();

        restaurantDetailViewStateMediatorLiveData.addSource(detailsRestaurantWrapperLiveData, detailsRestaurantWrapper -> {
                combine(detailsRestaurantWrapper, currentUserEntityLiveData.getValue());
            }
        );

        restaurantDetailViewStateMediatorLiveData.addSource(currentUserEntityLiveData, currentUser -> {
                combine(detailsRestaurantWrapperLiveData.getValue(), currentUser);
            }
        );
    }

    private void combine(
        @Nullable DetailsRestaurantWrapper detailsRestaurantWrapper,
        @Nullable UserEntity currentUser
    ) {
        if (detailsRestaurantWrapper == null || currentUser == null) {
            return;
        }

        boolean isRestaurantLiked = currentUser.getFavoriteRestaurantSet() != null && !currentUser.getFavoriteRestaurantSet().isEmpty() && currentUser.getFavoriteRestaurantSet().contains(restaurantId);
        boolean isAttending = (currentUser.getAttendingRestaurantId() != null && currentUser.getAttendingRestaurantId().equals(restaurantId));

        RestaurantFavoriteState restaurantFavoriteState = isRestaurantLiked ? RestaurantFavoriteState.IS_FAVORITE : RestaurantFavoriteState.IS_NOT_FAVORITE;
        AttendanceState attendanceState = isAttending ? AttendanceState.IS_ATTENDING : AttendanceState.IS_NOT_ATTENDING;

        if (detailsRestaurantWrapper instanceof DetailsRestaurantWrapper.Loading) {
            restaurantDetailViewStateMediatorLiveData.setValue(
                new RestaurantDetailViewState.Loading()
            );
        }

        if (detailsRestaurantWrapper instanceof DetailsRestaurantWrapper.Success) {

            DetailsRestaurantEntity detailsRestaurantEntity = ((DetailsRestaurantWrapper.Success) detailsRestaurantWrapper).getDetailsRestaurantEntity();

            restaurantDetailViewStateMediatorLiveData.setValue(
                new RestaurantDetailViewState.RestaurantDetail(
                    restaurantId,
                    checkIfResponseFieldExist(detailsRestaurantEntity.getRestaurantName()),
                    checkIfResponseFieldExist(detailsRestaurantEntity.getVicinity()),
                    parseRestaurantPictureUrl(detailsRestaurantEntity.getPhotoReferenceUrl()),
                    convertFiveToThreeRating(detailsRestaurantEntity.getRating()),
                    detailsRestaurantEntity.getPhoneNumber(),
                    detailsRestaurantEntity.getWebsiteUrl(),
                    attendanceState,
                    restaurantFavoriteState,
                    detailsRestaurantEntity.getVeganFriendly(),
                    detailsRestaurantEntity.getPhoneNumber() != null,
                    detailsRestaurantEntity.getWebsiteUrl() != null
                )
            );
        }

        if (detailsRestaurantWrapper instanceof DetailsRestaurantWrapper.Error) {
            if (((DetailsRestaurantWrapper.Error) detailsRestaurantWrapper).getThrowable().getMessage() != null) {
                restaurantDetailViewStateMediatorLiveData.setValue(
                    new RestaurantDetailViewState.Error(
                        ((DetailsRestaurantWrapper.Error) detailsRestaurantWrapper).getThrowable().getMessage()
                    )
                );
            }
        }
    }

    public LiveData<RestaurantDetailViewState> getRestaurantDetails() {
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


    public void onAddFavoriteRestaurant() {
        addFavoriteRestaurantUseCase.invoke(restaurantId);

    }

    public void onRemoveFavoriteRestaurant() {
        removeFavoriteRestaurantUseCase.invoke(restaurantId);
    }

    public void onAddUserRestaurantChoice(
        @NonNull String restaurantName,
        @NonNull String vicinity,
        @Nullable String photoReferenceUrl
    ) {
        addUserRestaurantChoiceUseCase.invoke(restaurantId, restaurantName, vicinity, photoReferenceUrl);
    }

    public void onRemoveUserRestaurantChoice() {
        removeUserRestaurantChoiceUseCase.invoke();
    }

    public LiveData<List<WorkmatesViewStateItem>> getWorkmatesGoingToRestaurant() {
        return Transformations.switchMap(getWorkmateEntitiesGoingToSameRestaurantUseCase.invoke(restaurantId), workmatesGoingToSameRestaurant -> {
                List<WorkmatesViewStateItem> workmatesViewStateItems = new ArrayList<>();
                for (WorkmateEntity workmateEntity : workmatesGoingToSameRestaurant) {
                    if (!workmateEntity.getLoggedUserEntity().getId().equals(getCurrentLoggedUserIdUseCase.invoke())) {
                        workmatesViewStateItems.add(
                            new WorkmatesViewStateItem.WorkmatesGoingToSameRestaurant(
                                workmateEntity.getLoggedUserEntity().getId(),
                                workmateEntity.getLoggedUserEntity().getName(),
                                workmateEntity.getLoggedUserEntity().getPictureUrl(),
                                workmateEntity.getAttendingRestaurantId()
                            )
                        );
                    }
                }
                return new MutableLiveData<>(workmatesViewStateItems);
            }
        );
    }
}
