package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.auth.AuthRepository;
import com.emplk.go4lunch.data.auth.FirebaseUserEntity;
import com.emplk.go4lunch.data.permission.GPSPermissionRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DispatcherViewModel extends ViewModel {

    @NonNull
    private final GPSPermissionRepository gpsPermissionRepository;

    @NonNull
    private final AuthRepository authRepository;   //TODO: wtf puis-je le virer ?

    private final MediatorLiveData<DispatcherViewAction> dispatcherViewActionMediatorLiveData = new MediatorLiveData<>();

    private final LiveData<FirebaseUserEntity> firebaseUserEntityLiveData;
    private final LiveData<Boolean> hasGPSPermissionLiveData;


    @Inject
    public DispatcherViewModel(
        @NonNull GPSPermissionRepository gpsPermissionRepository,
        @NonNull AuthRepository authRepository
    ) {
        this.gpsPermissionRepository = gpsPermissionRepository;
        this.authRepository = authRepository;

        firebaseUserEntityLiveData = authRepository.getCurrentUser();
        hasGPSPermissionLiveData = gpsPermissionRepository.hasGPSPermission();

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
        @Nullable FirebaseUserEntity firebaseUser
    ) {

        if (Boolean.FALSE.equals(permission)) {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.ONBOARDING_ACTIVITY);
        } else if (firebaseUser == null) {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.LOGIN_ACTIVITY);
        } else {
            dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.MAIN_ACTIVITY);
        }
    }

    public MediatorLiveData<DispatcherViewAction> getDispatcherViewActionMediatorLiveData() {
        return dispatcherViewActionMediatorLiveData;
    }

    public LiveData<DispatcherViewAction> getDispatcherViewAction() {
        return Transformations.switchMap(gpsPermissionRepository.hasGPSPermission(), permission -> {
            if (Boolean.TRUE.equals(permission)) {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.LOGIN_ACTIVITY);
            } else {
                dispatcherViewActionMediatorLiveData.setValue(DispatcherViewAction.ONBOARDING_ACTIVITY);
            }
            return dispatcherViewActionMediatorLiveData;
        });
    }
}