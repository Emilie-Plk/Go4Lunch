package com.emplk.go4lunch.ui.onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.permission.GPSPermissionRepository;
import com.emplk.go4lunch.ui.dispatcher.OnBoardingViewAction;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OnBoardingViewModel extends ViewModel {

    @NonNull
    private final GPSPermissionRepository gpsPermissionRepository;

    private final MutableLiveData<Boolean> userPermissionMutableLiveData = new MutableLiveData<>();

    private final LiveData<Boolean> hasGpsPermissionLiveData;

    private final MediatorLiveData<OnBoardingViewAction> onBoardingViewActionMediatorLiveData = new MediatorLiveData<>();


    @Inject
    public OnBoardingViewModel(
        @NonNull GPSPermissionRepository gpsPermissionRepository
    ) {
        this.gpsPermissionRepository = gpsPermissionRepository;

        hasGpsPermissionLiveData = gpsPermissionRepository.hasGPSPermission();

        onBoardingViewActionMediatorLiveData.addSource(hasGpsPermissionLiveData, permission -> {
                combine(permission, userPermissionMutableLiveData.getValue());
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(userPermissionMutableLiveData, userPermission -> {
                combine(hasGpsPermissionLiveData.getValue(), userPermission);
            }
        );
    }

    private void combine(
        @Nullable Boolean repositoryPermission,
        @Nullable Boolean userPermission
    ) {
        if (userPermission == null || repositoryPermission == null) {
            return;
        }

        if (!userPermission.equals(repositoryPermission)) {
            gpsPermissionRepository.refreshGPSPermission();
        }

        if (Boolean.TRUE.equals(repositoryPermission) && Boolean.TRUE.equals(userPermission)) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.MAIN_WITH_GPS_PERMISSION);
        } else if (Boolean.TRUE.equals(!repositoryPermission) && Boolean.TRUE.equals(!userPermission)) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.MAIN_WITHOUT_GPS_PERMISSION);
        }
    }

    public LiveData<OnBoardingViewAction> getOnBoardingViewAction() {
        return onBoardingViewActionMediatorLiveData;
    }

    public void setUserPermissionChoice(boolean userPermission) {
        userPermissionMutableLiveData.setValue(userPermission);
    }
}
