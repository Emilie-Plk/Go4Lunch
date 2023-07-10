package com.emplk.go4lunch.ui.main;

import static com.emplk.go4lunch.ui.main.FragmentState.MAP_FRAGMENT;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInUseCase;
import com.emplk.go4lunch.domain.authentication.use_case.LogoutUserUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.go4lunch.ui.main.searchview.SearchViewVisibilityState;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @NonNull
    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @NonNull
    private final LogoutUserUseCase logoutUserUseCase;


    @NonNull
    private final IsUserLoggedInUseCase isUserLoggedInUseCase;

    @NonNull
    private final IsGpsEnabledUseCase isGpsEnabledUseCase;

    @NonNull
    private final StartLocationRequestUseCase startLocationRequestUseCase;

    @NonNull
    private final GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase;

    @NonNull  // TODO: will use it to display/hide searchview bar
    private final MutableLiveData<List<SearchViewVisibilityState>> searchViewVisibilityStateMutableLiveData = new MutableLiveData<>();


    @NonNull
    private final SingleLiveEvent<FragmentState> fragmentStateSingleLiveEvent = new SingleLiveEvent<>();

    @Inject
    public MainViewModel(
        @NonNull GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase,
        @NonNull LogoutUserUseCase logoutUserUseCase,
        @NonNull IsUserLoggedInUseCase isUserLoggedInUseCase,
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase,
        @NonNull GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase
    ) {
        this.getCurrentLoggedUserUseCase = getCurrentLoggedUserUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
        this.isUserLoggedInUseCase = isUserLoggedInUseCase;
        this.isGpsEnabledUseCase = isGpsEnabledUseCase;
        this.startLocationRequestUseCase = startLocationRequestUseCase;
        this.getUserWithRestaurantChoiceEntityLiveDataUseCase = getUserWithRestaurantChoiceEntityLiveDataUseCase;

        fragmentStateSingleLiveEvent.setValue(MAP_FRAGMENT);
    }

    public LiveData<LoggedUserEntity> getUserInfoLiveData() { //TODO: maybe should use the UserEntity
        return new MutableLiveData<>(getCurrentLoggedUserUseCase.invoke());
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

    public LiveData<UserWithRestaurantChoiceEntity> getUserWithRestaurantChoice() {
        return getUserWithRestaurantChoiceEntityLiveDataUseCase.invoke();
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

