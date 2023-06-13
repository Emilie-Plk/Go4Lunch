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
import com.emplk.go4lunch.domain.favorite_restaurant.IsRestaurantUserFavoriteUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.RemoveFavoriteRestaurantUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.use_case.AddUserRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
import com.emplk.go4lunch.domain.user.use_case.RemoveUserRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.workmate.GetWorkmateEntitiesGoingToSameRestaurantUseCase;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;
import com.emplk.go4lunch.ui.utils.RestaurantFavoriteState;
import com.emplk.go4lunch.ui.workmate_list.WorkmatesViewStateItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RestaurantDetailViewModel extends ViewModel {


    @NonNull
    private final Resources resources;

    private final MediatorLiveData<RestaurantDetailViewState> restaurantDetailViewStateMediatorLiveData = new MediatorLiveData<>();

    private final MutableLiveData<RestaurantFavoriteState> restaurantFavoriteStateMutableLiveData = new MutableLiveData<>();

    @NonNull
    private final GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase;

    @NonNull
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @NonNull
    private final IsRestaurantUserFavoriteUseCase isRestaurantUserFavoriteUseCase;
    @NonNull
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
    private final GetUserEntityUseCase getUserEntityUseCase;

    private final String restaurantId;


    @Inject
    public RestaurantDetailViewModel(
        @NonNull GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase,
        @NonNull Resources resources,
        @NonNull GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase,
        @NonNull IsRestaurantUserFavoriteUseCase isRestaurantUserFavoriteUseCase,
        @NonNull AddFavoriteRestaurantUseCase addFavoriteRestaurantUseCase,
        @NonNull RemoveFavoriteRestaurantUseCase removeFavoriteRestaurantUseCase,
        @NonNull AddUserRestaurantChoiceUseCase addUserRestaurantChoiceUseCase,
        @NonNull RemoveUserRestaurantChoiceUseCase removeUserRestaurantChoiceUseCase,
        @NonNull GetWorkmateEntitiesGoingToSameRestaurantUseCase getWorkmateEntitiesGoingToSameRestaurantUseCase,
        @NonNull GetUserEntityUseCase getUserEntityUseCase,
        @NonNull SavedStateHandle savedStateHandle
    ) {
        this.getDetailsRestaurantWrapperUseCase = getDetailsRestaurantWrapperUseCase;
        this.resources = resources;
        this.getCurrentLoggedUserIdUseCase = getCurrentLoggedUserIdUseCase;
        this.isRestaurantUserFavoriteUseCase = isRestaurantUserFavoriteUseCase;
        this.addFavoriteRestaurantUseCase = addFavoriteRestaurantUseCase;
        this.removeFavoriteRestaurantUseCase = removeFavoriteRestaurantUseCase;
        this.addUserRestaurantChoiceUseCase = addUserRestaurantChoiceUseCase;
        this.removeUserRestaurantChoiceUseCase = removeUserRestaurantChoiceUseCase;
        this.getWorkmateEntitiesGoingToSameRestaurantUseCase = getWorkmateEntitiesGoingToSameRestaurantUseCase;
        this.getUserEntityUseCase = getUserEntityUseCase;

        restaurantId = savedStateHandle.get(RestaurantDetailActivity.KEY_RESTAURANT_ID);


        LiveData<DetailsRestaurantWrapper> detailsRestaurantWrapperLiveData = getDetailsRestaurantWrapperUseCase.invoke(restaurantId);

        LiveData<Boolean> isRestaurantLikedLiveData = isRestaurantUserFavoriteUseCase.invoke(restaurantId);

        restaurantDetailViewStateMediatorLiveData.addSource(detailsRestaurantWrapperLiveData, detailsRestaurantWrapper -> {
                combine(detailsRestaurantWrapper, getUserEntityUseCase.invoke().getValue());
            }
        );

        restaurantDetailViewStateMediatorLiveData.addSource(getUserEntityUseCase.invoke(), currentUser -> {
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

        if (detailsRestaurantWrapper instanceof DetailsRestaurantWrapper.Loading) {
            restaurantDetailViewStateMediatorLiveData.setValue(
                new RestaurantDetailViewState(
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

        if (detailsRestaurantWrapper instanceof DetailsRestaurantWrapper.Success) {
            DetailsRestaurantEntity detailsRestaurantEntity = ((DetailsRestaurantWrapper.Success) detailsRestaurantWrapper).getDetailsRestaurantEntity();
            boolean isRestaurantLiked = currentUser.getFavoriteRestaurantSet().contains(restaurantId);
            boolean isAttending = Objects.equals(currentUser.getAttendingRestaurantId(), restaurantId);

            if (isRestaurantLiked) {
                restaurantFavoriteStateMutableLiveData.setValue(RestaurantFavoriteState.IS_FAVORITE);
            } else {
                restaurantFavoriteStateMutableLiveData.setValue(RestaurantFavoriteState.IS_NOT_FAVORITE);
            }

            restaurantDetailViewStateMediatorLiveData.setValue(
                new RestaurantDetailViewState(
                    restaurantId,
                    checkIfResponseFieldExist(detailsRestaurantEntity.getRestaurantName()),
                    checkIfResponseFieldExist(detailsRestaurantEntity.getVicinity()),
                    parseRestaurantPictureUrl(detailsRestaurantEntity.getPhotoReferenceUrl()),
                    convertFiveToThreeRating(detailsRestaurantEntity.getRating()),
                    detailsRestaurantEntity.getPhoneNumber(),
                    detailsRestaurantEntity.getWebsiteUrl(),
                    isAttending,
                    isRestaurantLiked,
                    detailsRestaurantEntity.getVeganFriendly(),
                    false,
                    detailsRestaurantEntity.getPhoneNumber() != null,
                    detailsRestaurantEntity.getWebsiteUrl() != null
                )
            );
        }
        //TODO: handle error case
    }


    public LiveData<RestaurantDetailViewState> getRestaurantDetails() {
        return restaurantDetailViewStateMediatorLiveData;
    }

    public LiveData<RestaurantFavoriteState> getRestaurantFavoriteState() {
        return restaurantFavoriteStateMutableLiveData;
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
                    workmatesViewStateItems.add(
                        new WorkmatesViewStateItem.WorkmatesGoingToSameRestaurant(
                            workmateEntity.getLoggedUserEntity().getId(),
                            workmateEntity.getLoggedUserEntity().getName(),
                            workmateEntity.getLoggedUserEntity().getPictureUrl(),
                            workmateEntity.getAttendingRestaurantId()
                        )
                    );
                }
                return new MutableLiveData<>(workmatesViewStateItems);
            }
        );
    }
}
