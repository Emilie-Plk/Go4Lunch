package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.permission.GPSPermissionRepository;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DispatcherViewModel extends ViewModel {

    @NonNull
    private final GPSPermissionRepository gpsPermissionRepository;

    private final SingleLiveEvent<DispatcherViewAction> dispatcherViewActionSingleLiveEvent;


    @Inject
    public DispatcherViewModel(
        @NonNull GPSPermissionRepository gpsPermissionRepository
    ) {
        this.gpsPermissionRepository = gpsPermissionRepository;
        dispatcherViewActionSingleLiveEvent = new SingleLiveEvent<>();
        gpsPermissionRepository.refreshGPSPermission();
    }

    public LiveData<DispatcherViewAction> getDispatcherViewAction() {
        return Transformations.switchMap(gpsPermissionRepository.hasGPSPermission(), permission -> {
            if (Boolean.TRUE.equals(permission)) {
                dispatcherViewActionSingleLiveEvent.setValue(DispatcherViewAction.LOGIN_ACTIVITY);
            } else {
                dispatcherViewActionSingleLiveEvent.setValue(DispatcherViewAction.ONBOARDING_ACTIVITY);
            }
            return dispatcherViewActionSingleLiveEvent;
        });
    }
}