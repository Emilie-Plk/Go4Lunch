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
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.domain.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;
import com.emplk.go4lunch.domain.searchview.PredictionEntity;
import com.emplk.go4lunch.domain.searchview.use_case.ResetPredictionsUseCase;
import com.emplk.go4lunch.domain.searchview.use_case.SavePredictionsUseCase;
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
    private final SavePredictionsUseCase savePredictionsUseCase;

    @NonNull
    private final ResetPredictionsUseCase resetPredictionsUseCase;


    @NonNull
    private final SingleLiveEvent<FragmentState> fragmentStateSingleLiveEvent = new SingleLiveEvent<>();

    @NonNull
    private final MutableLiveData<String> userQueryMutableLiveData;

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
        @NonNull GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase,
        @NonNull SavePredictionsUseCase savePredictionsUseCase,
        @NonNull ResetPredictionsUseCase resetPredictionsUseCase
    ) {
        this.logoutUserUseCase = logoutUserUseCase;
        this.isGpsEnabledUseCase = isGpsEnabledUseCase;
        this.startLocationRequestUseCase = startLocationRequestUseCase;
        this.isUserLoggedInLiveDataUseCase = isUserLoggedInLiveDataUseCase;
        this.getUserWithRestaurantChoiceEntityLiveDataUseCase = getUserWithRestaurantChoiceEntityLiveDataUseCase;
        this.getUserEntityUseCase = getUserEntityUseCase;
        this.savePredictionsUseCase = savePredictionsUseCase;
        this.resetPredictionsUseCase = resetPredictionsUseCase;

        fragmentStateSingleLiveEvent.setValue(MAP_FRAGMENT);

        userQueryMutableLiveData = new MutableLiveData<>();

        LiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = getNearbySearchWrapperUseCase.invoke();

        predictionViewStateMediatorLiveData.addSource(userQueryMutableLiveData, query -> {
                combine(query, nearbySearchWrapperLiveData.getValue());
            }
        );

        predictionViewStateMediatorLiveData.addSource(nearbySearchWrapperLiveData, nearbySearchWrapper -> {
                combine(userQueryMutableLiveData.getValue(), nearbySearchWrapper);
            }
        );
    }

    private void combine(
        @Nullable String query,
        @Nullable NearbySearchWrapper nearbySearch
    ) {
        if (query == null || nearbySearch == null) {
            resetPredictionsUseCase.invoke();
            predictionViewStateMediatorLiveData.setValue(new ArrayList<>());
            return;
        }

        List<PredictionViewState> predictionViewStateList = new ArrayList<>();

        List<PredictionEntity> predictionEntityList = new ArrayList<>();

        if (nearbySearch instanceof NearbySearchWrapper.Success) {
            List<NearbySearchEntity> nearbySearchEntityList = ((NearbySearchWrapper.Success) nearbySearch).getNearbySearchEntityList();

            for (NearbySearchEntity nearbySearchEntity : nearbySearchEntityList) {
                String restaurantName = nearbySearchEntity.getRestaurantName();
                if (restaurantName.toLowerCase().contains(query.toLowerCase())) {
                    predictionEntityList.add(
                        new PredictionEntity(
                            nearbySearchEntity.getPlaceId(),
                            restaurantName
                        )
                    );
                    predictionViewStateList.add(
                        new PredictionViewState(
                            nearbySearchEntity.getPlaceId(),
                            restaurantName
                        )
                    );
                }
            }
        }

        savePredictionsUseCase.invoke(predictionEntityList);  // called twice, why?
        predictionViewStateMediatorLiveData.setValue(predictionViewStateList);
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
        if (query.isEmpty()) {
            resetPredictionsUseCase.invoke();
            userQueryMutableLiveData.setValue(null);
            return;
        }
        userQueryMutableLiveData.setValue(query);
    }

    public void onQueryReset() {
        resetPredictionsUseCase.invoke();
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

