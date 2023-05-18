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

    private final MutableLiveData<Boolean> hasChosenPermission = new MutableLiveData<>(false);

    private final LiveData<Boolean> hasGpsPermissionLiveData;


    private final MediatorLiveData<OnBoardingViewAction> onBoardingViewActionMediatorLiveData = new MediatorLiveData<>();


    @Inject
    public OnBoardingViewModel(
        @NonNull GPSPermissionRepository gpsPermissionRepository
    ) {
        this.gpsPermissionRepository = gpsPermissionRepository;

        hasGpsPermissionLiveData = gpsPermissionRepository.hasGPSPermission();

        onBoardingViewActionMediatorLiveData.addSource(hasGpsPermissionLiveData, permission -> {
                combine(permission, hasChosenPermission.getValue());
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(hasChosenPermission, userPermission -> {
                combine(hasGpsPermissionLiveData.getValue(), userPermission);
            }
        );
    }

    public LiveData<OnBoardingViewAction> getOnBoardingViewAction() {
        return onBoardingViewActionMediatorLiveData;
    }

    private void combine(
        @Nullable Boolean repositoryPermission,
        @Nullable Boolean hasChosenPermission
    ) {
        if (Boolean.FALSE.equals(hasChosenPermission)) {
            return;
        }

        if (Boolean.TRUE.equals(repositoryPermission)) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.MAIN_WITH_GPS_PERMISSION);
        } else {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.MAIN_WITHOUT_GPS_PERMISSION);
        }
    }


    public LiveData<Boolean> isShowRationale() {
        return hasGpsPermissionLiveData;
    }
}
