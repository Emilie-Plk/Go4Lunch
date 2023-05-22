package com.emplk.go4lunch.ui.onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.permission.GPSPermissionRepository;
import com.emplk.go4lunch.ui.utils.SingleLiveEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OnBoardingViewModel extends ViewModel {

    private final MutableLiveData<Boolean> hasGPSPermissionBeenAskedMutableLiveData = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> isShowRationaleMutableLiveData = new MutableLiveData<>();

    private final MediatorLiveData<OnBoardingViewAction> onBoardingViewActionMediatorLiveData = new MediatorLiveData<>();

    private final SingleLiveEvent<Boolean> isChangeAppSettingsClickedSingleLiveEvent = new SingleLiveEvent<>();

    @Inject
    public OnBoardingViewModel(
        @NonNull GPSPermissionRepository gpsPermissionRepository
    ) {
        LiveData<Boolean> hasGpsPermissionLiveData = gpsPermissionRepository.hasGPSPermissionLiveData();


        onBoardingViewActionMediatorLiveData.addSource(hasGpsPermissionLiveData, permission -> {
                combine(
                    permission,
                    hasGPSPermissionBeenAskedMutableLiveData.getValue(),
                    isShowRationaleMutableLiveData.getValue(),
                    isChangeAppSettingsClickedSingleLiveEvent.getValue()
                );
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(hasGPSPermissionBeenAskedMutableLiveData, userPermission -> {
                combine(
                    hasGpsPermissionLiveData.getValue(),
                    userPermission,
                    isShowRationaleMutableLiveData.getValue(),
                    isChangeAppSettingsClickedSingleLiveEvent.getValue()
                );
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(isShowRationaleMutableLiveData, showRationale -> {
                combine(
                    hasGpsPermissionLiveData.getValue(),
                    hasGPSPermissionBeenAskedMutableLiveData.getValue(),
                    showRationale,
                    isChangeAppSettingsClickedSingleLiveEvent.getValue()
                );
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(isChangeAppSettingsClickedSingleLiveEvent, changeAppSettings -> {
                combine(
                    hasGpsPermissionLiveData.getValue(),
                    hasGPSPermissionBeenAskedMutableLiveData.getValue(),
                    isShowRationaleMutableLiveData.getValue(),
                    changeAppSettings
                );
            }
        );
    }

    public LiveData<OnBoardingViewAction> getOnBoardingViewAction() {
        return onBoardingViewActionMediatorLiveData;
    }

    private void combine(
        @Nullable Boolean hasGPSPermission,
        @Nullable Boolean hasGPSPermissionBeenAsked,
        @Nullable Boolean isShowRationale,
        @Nullable Boolean changeAppSettings) {
        if (hasGPSPermission == null || hasGPSPermissionBeenAsked == null) {
            return;
        }

        boolean showRationale = isShowRationale != null;

        boolean changeAppSettingsClicked = changeAppSettings != null && changeAppSettings;

        if (hasGPSPermission) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.CONTINUE_TO_AUTHENTICATION);
        } else if (changeAppSettingsClicked) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.GO_APP_SETTINGS);
        } else if (hasGPSPermissionBeenAsked || showRationale) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.SHOW_RATIONALE);
        } else {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.ASK_GPS_PERMISSION);
        }
    }

    public void onPermissionResult() {
        hasGPSPermissionBeenAskedMutableLiveData.setValue(true);
    }

    public void onAllowClicked(boolean shouldShowRequestPermissionRationale) {
        isShowRationaleMutableLiveData.setValue(shouldShowRequestPermissionRationale);
    }

    public void onChangeAppSettingsClicked() {
        isChangeAppSettingsClickedSingleLiveEvent.setValue(true);
    }
}
