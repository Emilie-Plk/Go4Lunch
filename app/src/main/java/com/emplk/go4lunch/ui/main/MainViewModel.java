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
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserLiveDataUseCase;
import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInUseCase;
import com.emplk.go4lunch.domain.authentication.use_case.LogoutUserUseCase;
import com.emplk.go4lunch.domain.autocomplete.GetAutocompleteWrapperUseCase;
import com.emplk.go4lunch.domain.autocomplete.entity.AutocompleteWrapper;
import com.emplk.go4lunch.domain.autocomplete.entity.PredictionEntity;
<<<<<<< HEAD
=======
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
import com.emplk.go4lunch.ui.main.searchview.PredictionViewState;
import com.emplk.go4lunch.ui.main.searchview.SearchViewVisibilityState;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @NonNull
<<<<<<< HEAD
    private final GetCurrentLoggedUserLiveDataUseCase getCurrentLoggedUserLiveDataUseCase;
=======
    private final GetUserEntityUseCase getUserEntityUseCase;
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283

    @NonNull
    private final LogoutUserUseCase logoutUserUseCase;

    @NonNull
    private final GetAutocompleteWrapperUseCase getAutocompleteWrapperUseCase;

    @NonNull
    private final IsUserLoggedInUseCase isUserLoggedInUseCase;

    @NonNull  // TODO: will use it to display/hide searchview bar
    private final MutableLiveData<List<SearchViewVisibilityState>> searchViewVisibilityStateMutableLiveData = new MutableLiveData<>();

    @NonNull
    private final MediatorLiveData<List<PredictionViewState>> predictionViewStateMediatorLiveData = new MediatorLiveData<>();

    @NonNull
    private final SingleLiveEvent<FragmentState> fragmentStateSingleLiveEvent = new SingleLiveEvent<>();

    @Inject
    public MainViewModel(
<<<<<<< HEAD
        @NonNull GetCurrentLoggedUserLiveDataUseCase getCurrentLoggedUserLiveDataUseCase,
=======
        @NonNull GetUserEntityUseCase getUserEntityUseCase,
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
        @NonNull LogoutUserUseCase logoutUserUseCase,
        @NonNull GetAutocompleteWrapperUseCase getAutocompleteWrapperUseCase,
        @NonNull IsUserLoggedInUseCase isUserLoggedInUseCase
    ) {
<<<<<<< HEAD
        this.getCurrentLoggedUserLiveDataUseCase = getCurrentLoggedUserLiveDataUseCase;
=======
        this.getUserEntityUseCase = getUserEntityUseCase;
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
        this.logoutUserUseCase = logoutUserUseCase;
        this.getAutocompleteWrapperUseCase = getAutocompleteWrapperUseCase;
        this.isUserLoggedInUseCase = isUserLoggedInUseCase;

        fragmentStateSingleLiveEvent.setValue(MAP_FRAGMENT);
    }

<<<<<<< HEAD
    public LiveData<LoggedUserEntity> getUserInfoLiveData() { //TODO: just use the LoggedUserEntity
        return getCurrentLoggedUserLiveDataUseCase.invoke();
    }

    public LiveData<UserLoggingState> onUserLogged() {
        return Transformations.switchMap(isUserLoggedInUseCase.invoke(), isLogged -> {
                MutableLiveData<UserLoggingState> userLoggingStateLiveData = new MutableLiveData<>();
                if (isLogged) {
                    userLoggingStateLiveData.setValue(UserLoggingState.IS_LOGGED);
                } else {
                    userLoggingStateLiveData.setValue(UserLoggingState.IS_NOT_LOGGED);
                }
                return userLoggingStateLiveData;
            }
        );
=======
    public LiveData<UserEntity> getUserInfoLiveData() {
        return getUserEntityUseCase.invoke();
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
    }

    public LiveData<List<PredictionViewState>> onUserSearchQuery(@Nullable String input) {
        MutableLiveData<List<PredictionViewState>> predictionListViewStateMutableLiveData = new MutableLiveData<>();
        List<PredictionViewState> result = new ArrayList<>();
        if (input != null) {
            return Transformations.switchMap(getAutocompleteWrapperUseCase.invoke(input), predictionWrapper -> {
                    if (predictionWrapper instanceof AutocompleteWrapper.Success) {
                        for (PredictionEntity predictionEntity : ((AutocompleteWrapper.Success) predictionWrapper).getPredictionEntityList()) {
                            result.add(
                                new PredictionViewState(
                                    predictionEntity.getPlaceId(),
                                    predictionEntity.getRestaurantName(),
                                    predictionEntity.getVicinity()
                                )
                            );
                        }
                    } else if (predictionWrapper instanceof AutocompleteWrapper.NoResults) {
                        result.add(new PredictionViewState("No results found", "", null));

                    } else if (predictionWrapper instanceof AutocompleteWrapper.Error) {
                        result.add(new PredictionViewState("Error", "", null));
                    }
                    predictionListViewStateMutableLiveData.setValue(result);
                    return predictionListViewStateMutableLiveData;
                }
            );
        }
        return predictionListViewStateMutableLiveData;
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
}

