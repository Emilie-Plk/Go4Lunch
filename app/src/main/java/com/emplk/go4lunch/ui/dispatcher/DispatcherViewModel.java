package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
import com.emplk.go4lunch.domain.user.use_case.IsUserLoggedInUseCase;

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
        @NonNull GetUserEntityUseCase getUserEntityUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase
    ) {
        this.startLocationRequestUseCase = startLocationRequestUseCase;

        LiveData<Boolean> permissionLiveData = hasGpsPermissionUseCase.invoke();
        LiveData<Boolean> isUserLoggedInLiveData = isUserLoggedInUseCase.invoke();
        LiveData<UserEntity> getUserInfoLiveData = getUserEntityUseCase.invoke();

        dispatcherViewActionMediatorLiveData.addSource(permissionLiveData, hasPermission -> {
                combine(hasPermission, isUserLoggedInLiveData.getValue(), getUserInfoLiveData.getValue());
            }
        );
        dispatcherViewActionMediatorLiveData.addSource(isUserLoggedInLiveData, isUserLoggedIn -> {
                combine(permissionLiveData.getValue(), isUserLoggedIn, getUserInfoLiveData.getValue());
            }
        );

        dispatcherViewActionMediatorLiveData.addSource(getUserInfoLiveData, userEntity -> {  // says the NPE is here
                combine(permissionLiveData.getValue(), isUserLoggedInLiveData.getValue(), userEntity);
            }
        );
    }

    private void combine(
        @Nullable Boolean hasPermission,
        @Nullable Boolean isUserLoggedIn,
        @Nullable UserEntity userEntity
    ) {
        if (hasPermission == null) {
            return;
        }

        if (hasPermission) {
            startLocationRequestUseCase.invoke();
            if (isUserLoggedIn == null || !isUserLoggedIn || userEntity == null) {
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