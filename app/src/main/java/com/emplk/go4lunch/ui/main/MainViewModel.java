package com.emplk.go4lunch.ui.main;

import static com.emplk.go4lunch.ui.main.FragmentState.MAP_FRAGMENT;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInLiveDataUseCase;
import com.emplk.go4lunch.domain.authentication.use_case.LogoutUserUseCase;
import com.emplk.go4lunch.domain.autocomplete.PredictionEntity;
import com.emplk.go4lunch.domain.autocomplete.use_case.GetPredictionsUseCase;
import com.emplk.go4lunch.domain.autocomplete.use_case.ResetPredictionPlaceIdUseCase;
import com.emplk.go4lunch.domain.autocomplete.use_case.SavePredictionPlaceIdUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
import com.emplk.go4lunch.ui.main.searchview.PredictionViewState;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.Collections;
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
    private final SavePredictionPlaceIdUseCase savePredictionPlaceIdUseCase;

    @NonNull
    private final ResetPredictionPlaceIdUseCase resetPredictionPlaceIdUseCase;


    @NonNull
    private final SingleLiveEvent<FragmentState> fragmentStateSingleLiveEvent = new SingleLiveEvent<>();


    @NonNull
    private final MutableLiveData<String> userQueryMutableLiveData;


    @NonNull
    private final LiveData<List<PredictionEntity>> predictionsLiveData;


    @Inject
    public MainViewModel(
        @NonNull LogoutUserUseCase logoutUserUseCase,
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase,
        @NonNull IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase,
        @NonNull GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase,
        @NonNull GetUserEntityUseCase getUserEntityUseCase,
        @NonNull GetPredictionsUseCase getPredictionsUseCase,
        @NonNull SavePredictionPlaceIdUseCase savePredictionPlaceIdUseCase,
        @NonNull ResetPredictionPlaceIdUseCase resetPredictionPlaceIdUseCase
    ) {
        this.logoutUserUseCase = logoutUserUseCase;
        this.isGpsEnabledUseCase = isGpsEnabledUseCase;
        this.startLocationRequestUseCase = startLocationRequestUseCase;
        this.isUserLoggedInLiveDataUseCase = isUserLoggedInLiveDataUseCase;
        this.getUserWithRestaurantChoiceEntityLiveDataUseCase = getUserWithRestaurantChoiceEntityLiveDataUseCase;
        this.getUserEntityUseCase = getUserEntityUseCase;
        this.savePredictionPlaceIdUseCase = savePredictionPlaceIdUseCase;
        this.resetPredictionPlaceIdUseCase = resetPredictionPlaceIdUseCase;

        fragmentStateSingleLiveEvent.setValue(MAP_FRAGMENT);

        userQueryMutableLiveData = new MutableLiveData<>();

        predictionsLiveData = Transformations.switchMap(
            userQueryMutableLiveData, userQuery -> {
                if (userQuery == null || userQuery.isEmpty() || userQuery.length() < 3) {
                    return new MutableLiveData<>(Collections.emptyList());
                } else {
                    return getPredictionsUseCase.invoke(userQuery);
                }
            }
        );
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
        return Transformations.switchMap(predictionsLiveData, predictions -> {
                MutableLiveData<List<PredictionViewState>> predictionViewStateMutableLiveData = new MutableLiveData<>();
                List<PredictionViewState> predictionViewStateList = new ArrayList<>();
                if (predictions != null) {
                    for (PredictionEntity prediction : predictions) {
                        predictionViewStateList.add(
                            new PredictionViewState(
                                prediction.getPlaceId(),
                                prediction.getRestaurantName()
                            )
                        );
                    }
                } else {
                    predictionViewStateMutableLiveData.setValue(Collections.emptyList());
                }
                predictionViewStateMutableLiveData.setValue(predictionViewStateList);
                return predictionViewStateMutableLiveData;
            }
        );
    }

    public void onQueryChanged(String query) {
        if (query == null || query.isEmpty()) {
            resetPredictionPlaceIdUseCase.invoke();
            userQueryMutableLiveData.setValue(null);
            return;
        }
        userQueryMutableLiveData.setValue(query);
    }

    public void onPredictionPlaceIdReset() {
        userQueryMutableLiveData.setValue(null);
        resetPredictionPlaceIdUseCase.invoke();
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

    public void onPredictionClicked(String placeId) {
        savePredictionPlaceIdUseCase.invoke(placeId);
        userQueryMutableLiveData.setValue(null);
    }
}

