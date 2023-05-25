package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.permission.GpsPermissionRepositoryImpl;
import com.emplk.go4lunch.domain.authentication.GetCurrentUserUseCase;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.permission.GetGpsPermissionUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DispatcherViewModel extends ViewModel {

    private final MediatorLiveData<DispatcherViewAction> dispatcherViewActionMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public DispatcherViewModel(
        @NonNull GetGpsPermissionUseCase getGpsPermissionUseCase,
        @NonNull GetCurrentUserUseCase getCurrentUserUseCase
    ) {

        LiveData<Boolean> hasGpsPermissionLiveData = getGpsPermissionUseCase.invoke();

        LiveData<LoggedUserEntity> firebaseUserEntityLiveData = getCurrentUserUseCase.invoke();

        dispatcherViewActionMediatorLiveData.addSource(hasGpsPermissionLiveData, permission -> {
                combine(permission, firebaseUserEntityLiveData.getValue());   // TODO: but that's ok if firebaseUserEntityLiveData.getValue() is null...
            }
        );

        dispatcherViewActionMediatorLiveData.addSource(firebaseUserEntityLiveData, firebaseUserEntity -> {
                combine(hasGpsPermissionLiveData.getValue(), firebaseUserEntity);
            }
        );
    }

    private void combine(
        @Nullable Boolean permission,
        @Nullable LoggedUserEntity firebaseUser
    ) {
        if (permission == null) {
            return;
        }

        if (permission) {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_MAIN_ACTIVITY);
            if (firebaseUser == null) {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_LOGIN_ACTIVITY);
            }
        } else {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_ONBOARDING_ACTIVITY);
        }
    }

    public MediatorLiveData<DispatcherViewAction> getDispatcherViewActionMediatorLiveData() {
        return dispatcherViewActionMediatorLiveData;
    }
}