package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.authentication.AuthRepositoryImpl;
import com.emplk.go4lunch.data.permission.GPSPermissionRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DispatcherViewModel extends ViewModel {

    @NonNull
    private final GPSPermissionRepository gpsPermissionRepository;

    @NonNull
    private final AuthRepositoryImpl authRepository;   //TODO: wtf puis-je le virer ?

    private final MediatorLiveData<DispatcherViewAction> dispatcherViewActionMediatorLiveData = new MediatorLiveData<>();

    private final LiveData<LoggedUserEntity> firebaseUserEntityLiveData;
    private final LiveData<Boolean> hasGPSPermissionLiveData;


    @Inject
    public DispatcherViewModel(
        @NonNull GPSPermissionRepository gpsPermissionRepository,
        @NonNull AuthRepositoryImpl authRepository
    ) {
        this.gpsPermissionRepository = gpsPermissionRepository;
        this.authRepository = authRepository;

        firebaseUserEntityLiveData = authRepository.getCurrentUserLiveData();
        hasGPSPermissionLiveData = gpsPermissionRepository.hasGPSPermissionLiveData();

        gpsPermissionRepository.refreshGPSPermission();

        dispatcherViewActionMediatorLiveData.addSource(hasGPSPermissionLiveData, permission -> {
                combine(permission, firebaseUserEntityLiveData.getValue());
            }
        );

        dispatcherViewActionMediatorLiveData.addSource(firebaseUserEntityLiveData, firebaseUserEntity -> {
                combine(hasGPSPermissionLiveData.getValue(), firebaseUserEntity);
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
        } else if (firebaseUser == null) {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_LOGIN_ACTIVITY);
        } else {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_ONBOARDING_ACTIVITY);
        }
    }

    public MediatorLiveData<DispatcherViewAction> getDispatcherViewActionMediatorLiveData() {
        return dispatcherViewActionMediatorLiveData;
    }

    public LiveData<DispatcherViewAction> getDispatcherViewAction() {
        return Transformations.switchMap(gpsPermissionRepository.hasGPSPermissionLiveData(), permission -> {
            if (Boolean.TRUE.equals(permission)) {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_LOGIN_ACTIVITY);
            } else {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.GO_TO_ONBOARDING_ACTIVITY);
            }
            return dispatcherViewActionMediatorLiveData;
        });
    }
}