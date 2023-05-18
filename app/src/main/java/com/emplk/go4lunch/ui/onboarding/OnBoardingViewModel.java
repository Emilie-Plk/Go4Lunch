package com.emplk.go4lunch.ui.onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.auth.AuthRepository;
import com.emplk.go4lunch.data.auth.FirebaseUserEntity;
import com.emplk.go4lunch.data.permission.GPSPermissionRepository;
import com.emplk.go4lunch.ui.dispatcher.OnBoardingViewAction;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OnBoardingViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isGpsPermissionAskedLiveData = new MutableLiveData<>(false);

    private final MediatorLiveData<OnBoardingViewAction> onBoardingViewActionMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public OnBoardingViewModel(
        @NonNull GPSPermissionRepository gpsPermissionRepository
    ) {
        LiveData<Boolean> hasGpsPermissionLiveData = gpsPermissionRepository.hasGPSPermission();

        onBoardingViewActionMediatorLiveData.addSource(hasGpsPermissionLiveData, permission -> {
                combine(permission, isGpsPermissionAskedLiveData.getValue());
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(isGpsPermissionAskedLiveData, userPermission -> {
                combine(hasGpsPermissionLiveData.getValue(), userPermission);
            }
        );
    }

    public LiveData<OnBoardingViewAction> getOnBoardingViewAction() {
        return onBoardingViewActionMediatorLiveData;
    }

    private void combine(
        @Nullable Boolean hasGps,
        @Nullable Boolean isGpsPermissionAsked
    ) {
        if (hasGps == null || isGpsPermissionAsked == null) {
            return;
        }

        if (hasGps) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.CONTINUE);
        } else if (isGpsPermissionAsked) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.SHOW_RATIONALE);
        } else {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.ASK_GPS_PERMISSION);
        }
    }

    public void onPermissionResult() {
        isGpsPermissionAskedLiveData.setValue(true);
    }

    public void onAllowClicked(boolean shouldShowRequestPermissionRationale) {
        // TODO Emilie
    }
}
