package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
<<<<<<< HEAD
=======
import com.emplk.go4lunch.domain.user.use_case.IsUserLoggedInUseCase;
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283

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
<<<<<<< HEAD
=======
        @NonNull GetUserEntityUseCase getUserEntityUseCase,
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase
    ) {
        this.startLocationRequestUseCase = startLocationRequestUseCase;

        LiveData<Boolean> permissionLiveData = hasGpsPermissionUseCase.invoke();
        LiveData<Boolean> isUserLoggedInLiveData = isUserLoggedInUseCase.invoke();
<<<<<<< HEAD

=======
        LiveData<UserEntity> getUserInfoLiveData = getUserEntityUseCase.invoke();
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283

        dispatcherViewActionMediatorLiveData.addSource(permissionLiveData, hasPermission -> {
                combine(hasPermission, isUserLoggedInLiveData.getValue());
            }
        );
        dispatcherViewActionMediatorLiveData.addSource(isUserLoggedInLiveData, isUserLoggedIn -> {
                combine(permissionLiveData.getValue(), isUserLoggedIn);
            }
        );

    }

    private void combine(
        @Nullable Boolean hasPermission,
        @Nullable Boolean isUserLoggedIn
    ) {
        if (hasPermission == null) {
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