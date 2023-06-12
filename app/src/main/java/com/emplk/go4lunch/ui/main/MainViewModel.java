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
    private final GetCurrentLoggedUserLiveDataUseCase getCurrentLoggedUserLiveDataUseCase;

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

        @NonNull GetCurrentLoggedUserLiveDataUseCase getCurrentLoggedUserLiveDataUseCase,
        @NonNull LogoutUserUseCase logoutUserUseCase,
        @NonNull GetAutocompleteWrapperUseCase getAutocompleteWrapperUseCase,
        @NonNull IsUserLoggedInUseCase isUserLoggedInUseCase
    ) {

        this.getCurrentLoggedUserLiveDataUseCase = getCurrentLoggedUserLiveDataUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
        this.getAutocompleteWrapperUseCase = getAutocompleteWrapperUseCase;
        this.isUserLoggedInUseCase = isUserLoggedInUseCase;

        fragmentStateSingleLiveEvent.setValue(MAP_FRAGMENT);
    }


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

