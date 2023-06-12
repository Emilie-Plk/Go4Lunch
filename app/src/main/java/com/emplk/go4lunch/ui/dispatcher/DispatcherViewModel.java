package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DispatcherViewModel extends ViewModel {

    private final MediatorLiveData<DispatcherViewAction> dispatcherViewActionMediatorLiveData = new MediatorLiveData<>();
    @NonNull
    private final StartLocationRequestUseCase startLocationRequestUseCase;


    @Inject
    public DispatcherViewModel(
        @NonNull HasGpsPermissionUseCase hasGpsPermissionUseCase,
        @NonNull IsUserLoggedInUseCase isUserLoggedInUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase,
        @NonNull IsGpsEnabledUseCase isGpsEnabledUseCase
    ) {
        this.startLocationRequestUseCase = startLocationRequestUseCase;

        LiveData<Boolean> permissionLiveData = hasGpsPermissionUseCase.invoke();
        LiveData<Boolean> isUserLoggedInLiveData = isUserLoggedInUseCase.invoke();
        LiveData<Boolean> isGpsEnabledLiveData = isGpsEnabledUseCase.invoke();


        dispatcherViewActionMediatorLiveData.addSource(permissionLiveData, hasPermission -> {
                combine(hasPermission, isUserLoggedInLiveData.getValue(), isGpsEnabledLiveData.getValue());
            }
        );
        dispatcherViewActionMediatorLiveData.addSource(isUserLoggedInLiveData, isUserLoggedIn -> {
                combine(permissionLiveData.getValue(), isUserLoggedIn, isGpsEnabledLiveData.getValue());
            }
        );
        dispatcherViewActionMediatorLiveData.addSource(isGpsEnabledLiveData, isGpsEnabled -> {
                combine(permissionLiveData.getValue(), isUserLoggedInLiveData.getValue(), isGpsEnabled);
            }
        );

    }

    private void combine(
        @Nullable Boolean hasPermission,
        @Nullable Boolean isUserLoggedIn,
        @Nullable Boolean isGpsEnabled
    ) {
        if (hasPermission == null || isGpsEnabled == null) {
            return;
        }

        if (hasPermission) {
            startLocationRequestUseCase.invoke();
            if (isUserLoggedIn == null || !isUserLoggedIn) {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_LOGIN_ACTIVITY);
            } else {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_MAIN_ACTIVITY);
            }
        } else {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_ONBOARDING_ACTIVITY);
        }
    }

    public LiveData<DispatcherViewAction> getDispatcherViewAction() {
        return dispatcherViewActionMediatorLiveData;
    }
}