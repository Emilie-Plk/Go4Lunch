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
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;

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
    private final SingleLiveEvent<FragmentState> fragmentStateSingleLiveEvent = new SingleLiveEvent<>();

    @Inject
    public MainViewModel(
        @NonNull LogoutUserUseCase logoutUserUseCase,
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase,
        @NonNull IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase,
        @NonNull GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase,
        @NonNull GetUserEntityUseCase getUserEntityUseCase
    ) {
        this.logoutUserUseCase = logoutUserUseCase;
        this.isGpsEnabledUseCase = isGpsEnabledUseCase;
        this.startLocationRequestUseCase = startLocationRequestUseCase;
        this.isUserLoggedInLiveDataUseCase = isUserLoggedInLiveDataUseCase;
        this.getUserWithRestaurantChoiceEntityLiveDataUseCase = getUserWithRestaurantChoiceEntityLiveDataUseCase;
        this.getUserEntityUseCase = getUserEntityUseCase;

        fragmentStateSingleLiveEvent.setValue(MAP_FRAGMENT);
    }

    public LiveData<LoggedUserEntity> getUserInfoLiveData() {
        return Transformations.switchMap(getUserEntityUseCase.invoke(), userEntity -> {
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
        return Transformations.switchMap(isUserLoggedInLiveDataUseCase.invoke(), isLogged -> {
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

