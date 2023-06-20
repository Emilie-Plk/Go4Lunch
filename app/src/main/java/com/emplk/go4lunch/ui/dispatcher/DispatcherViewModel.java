package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledAtAppLaunchUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DispatcherViewModel extends ViewModel {

    private final SingleLiveEvent<DispatcherViewAction> dispatcherViewActionMediatorLiveData = new SingleLiveEvent<>();
    @NonNull
    private final StartLocationRequestUseCase startLocationRequestUseCase;

    @Inject
    public DispatcherViewModel(
        @NonNull HasGpsPermissionUseCase hasGpsPermissionUseCase,
        @NonNull IsUserLoggedInUseCase isUserLoggedInUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase,
        @NonNull IsGpsEnabledAtAppLaunchUseCase isGpsEnabledAtAppLaunchUseCase
    ) {
        this.startLocationRequestUseCase = startLocationRequestUseCase;

        LiveData<Boolean> permissionLiveData = hasGpsPermissionUseCase.invoke();
        LiveData<Boolean> isUserLoggedInLiveData = isUserLoggedInUseCase.invoke();
        LiveData<Boolean> isGpsEnabledLiveData = isGpsEnabledAtAppLaunchUseCase.invoke();

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
        if (hasPermission == null || isUserLoggedIn == null || isGpsEnabled == null) {
            return;
        }

        if (hasPermission) {
            startLocationRequestUseCase.invoke();
            if (!isUserLoggedIn) {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_LOGIN_ACTIVITY);
            } else if (!isGpsEnabled) {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.DISPLAY_GPS_DIALOG);
            } else {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_MAIN_ACTIVITY);
            }
        } else {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_ONBOARDING_ACTIVITY);
        }
    }

    public SingleLiveEvent<DispatcherViewAction> getDispatcherViewAction() {
        return dispatcherViewActionMediatorLiveData;
    }
}