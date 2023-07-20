package com.emplk.go4lunch.ui.main;

import static com.emplk.go4lunch.ui.main.FragmentState.MAP_FRAGMENT;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInLiveDataUseCase;
import com.emplk.go4lunch.domain.authentication.use_case.LogoutUserUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.domain.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
import com.emplk.go4lunch.ui.main.searchview.PredictionViewState;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @NonNull
    private final LogoutUserUseCase logoutUserUseCase;

    @NonNull
    private final IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase;

    @NonNull
    private final IsGpsEnabledUseCase isGpsEnabledUseCase;

    @NonNull
    private final StartLocationRequestUseCase startLocationRequestUseCase;

    @NonNull
    private final GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase;

    @NonNull
    private final GetUserEntityUseCase getUserEntityUseCase;


    @NonNull
    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase;

    @NonNull
    private final SingleLiveEvent<FragmentState> fragmentStateSingleLiveEvent = new SingleLiveEvent<>();

    @NonNull
    private final MutableLiveData<String> userQueryMutableLiveData = new MutableLiveData<>();

    @NonNull
    private final MediatorLiveData<List<PredictionViewState>> predictionViewStateMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public MainViewModel(
        @NonNull LogoutUserUseCase logoutUserUseCase,
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase,
        @NonNull IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase,
        @NonNull GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase,
        @NonNull GetUserEntityUseCase getUserEntityUseCase,
        @NonNull GetCurrentLocationStateUseCase getCurrentLocationStateUseCase,
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase
    ) {
        this.logoutUserUseCase = logoutUserUseCase;
        this.isGpsEnabledUseCase = isGpsEnabledUseCase;
        this.startLocationRequestUseCase = startLocationRequestUseCase;
        this.isUserLoggedInLiveDataUseCase = isUserLoggedInLiveDataUseCase;
        this.getUserWithRestaurantChoiceEntityLiveDataUseCase = getUserWithRestaurantChoiceEntityLiveDataUseCase;
        this.getUserEntityUseCase = getUserEntityUseCase;
        this.getCurrentLocationStateUseCase = getCurrentLocationStateUseCase;

        fragmentStateSingleLiveEvent.setValue(MAP_FRAGMENT);

        LiveData<LocationStateEntity> locationStateLiveData = getCurrentLocationStateUseCase.invoke();

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();


        predictionViewStateMediatorLiveData.addSource(userQueryMutableLiveData, query -> {
                combine(query, locationStateLiveData.getValue(), nearbySearchWrapperLiveData.getValue());
            }
        );


        predictionViewStateMediatorLiveData.addSource(locationStateLiveData, locationState -> {
                combine(userQueryMutableLiveData.getValue(), locationState, nearbySearchWrapperLiveData.getValue());
            }
        );

        predictionViewStateMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper -> {
                combine(userQueryMutableLiveData.getValue(), locationStateLiveData.getValue(), nearbySearchWrapper);
            }
        );
    }

    private void combine(
        @Nullable String query,
        @Nullable LocationStateEntity locationState,
        @Nullable NearbySearchWrapper nearbySearch
    ) {
        if (query == null || query.length() < 3 || locationState == null || nearbySearch == null) {
            predictionViewStateMediatorLiveData.setValue(new ArrayList<>());
            return;
        }

        List<PredictionViewState> predictionViewStateList = new ArrayList<>();

        if (locationState instanceof LocationStateEntity.Success) {
            if (nearbySearch instanceof NearbySearchWrapper.Success) {
                List<NearbySearchEntity> nearbySearchEntityList = ((NearbySearchWrapper.Success) nearbySearch).getNearbySearchEntityList();

                for (NearbySearchEntity nearbySearchEntity : nearbySearchEntityList) {
                    String restaurantName = nearbySearchEntity.getRestaurantName();
                    if (restaurantName.toLowerCase().contains(query.toLowerCase())) {
                        predictionViewStateList.add(
                            new PredictionViewState(
                                nearbySearchEntity.getPlaceId(),
                                restaurantName
                            )
                        );
                    }
                }
            }
        }

        if (predictionViewStateList.isEmpty()) {
            predictionViewStateMediatorLiveData.setValue(new ArrayList<>());
        } else {
            predictionViewStateMediatorLiveData.setValue(predictionViewStateList);
        }
    }


    public LiveData<LoggedUserEntity> getUserInfoLiveData() {
       LiveData<UserEntity> userEntityLiveData = getUserEntityUseCase.invoke();
        return Transformations.switchMap(userEntityLiveData, userEntity -> {
                MutableLiveData<LoggedUserEntity> loggedUserEntityMutableLiveData = new MutableLiveData<>();
                LoggedUserEntity currentUser = userEntity.getLoggedUserEntity();
                loggedUserEntityMutableLiveData.setValue(
                    new LoggedUserEntity(
                        currentUser.getId(),
                        currentUser.getName(),
                        currentUser.getEmail(),
                        currentUser.getPictureUrl()
                    )
                );
                return loggedUserEntityMutableLiveData;
            }
        );
    }

    public LiveData<Boolean> onUserLogged() {
        LiveData<Boolean> isUserLoggedInLiveData = isUserLoggedInLiveDataUseCase.invoke();
        return Transformations.switchMap(isUserLoggedInLiveData, isLogged -> {
                MutableLiveData<Boolean> isUserLoggedInMutableLiveData = new MutableLiveData<>();
                if (isLogged) {
                    isUserLoggedInMutableLiveData.setValue(true);
                } else {
                    isUserLoggedInMutableLiveData.setValue(false);
                }
                return isUserLoggedInMutableLiveData;
            }
        );
    }

    public LiveData<UserWithRestaurantChoiceEntity> getUserWithRestaurantChoice() {
        return getUserWithRestaurantChoiceEntityLiveDataUseCase.invoke();
    }

    public LiveData<List<PredictionViewState>> getPredictionsLiveData() {
        return predictionViewStateMediatorLiveData;
    }

    public void onQueryChanged(@NonNull String query) {
        userQueryMutableLiveData.setValue(query);
    }

    public LiveData<Boolean> isGpsEnabledLiveData() {
        return isGpsEnabledUseCase.invoke();
    }

    public void signOut() {
        logoutUserUseCase.invoke();
    }

    @NonNull
    public SingleLiveEvent<FragmentState> getFragmentStateSingleLiveEvent() {
        return fragmentStateSingleLiveEvent;
    }

    public void onChangeFragmentView(@NonNull FragmentState fragmentState) {
        fragmentStateSingleLiveEvent.setValue(fragmentState);
    }

    public void onResume() {
        startLocationRequestUseCase.invoke();
    }
}

