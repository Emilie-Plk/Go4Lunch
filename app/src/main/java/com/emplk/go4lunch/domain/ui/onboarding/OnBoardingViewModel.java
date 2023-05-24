package com.emplk.go4lunch.domain.ui.onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.permission.GpsPermissionRepository;
import com.emplk.go4lunch.domain.ui.utils.SingleLiveEvent;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OnBoardingViewModel extends ViewModel {

    private final MutableLiveData<Boolean> hasGpsPermissionBeenAskedMutableLiveData = new MutableLiveData<>(false);

    private final MutableLiveData<Boolean> isShowRationaleMutableLiveData = new MutableLiveData<>();

    private final MediatorLiveData<OnBoardingViewAction> onBoardingViewActionMediatorLiveData = new MediatorLiveData<>();

    private final SingleLiveEvent<Boolean> isChangeAppSettingsClickedSingleLiveEvent = new SingleLiveEvent<>();

    @Inject
    public OnBoardingViewModel(
        @NonNull GpsPermissionRepository gpsPermissionRepository
    ) {
        LiveData<Boolean> hasGpsPermissionLiveData = gpsPermissionRepository.hasGpsPermissionLiveData();


        onBoardingViewActionMediatorLiveData.addSource(hasGpsPermissionLiveData, permission -> {
                combine(
                    permission,
                    hasGpsPermissionBeenAskedMutableLiveData.getValue(),
                    isShowRationaleMutableLiveData.getValue(),
                    isChangeAppSettingsClickedSingleLiveEvent.getValue()
                );
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(hasGpsPermissionBeenAskedMutableLiveData, userPermission -> {
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
                    hasGpsPermissionBeenAskedMutableLiveData.getValue(),
                    showRationale,
                    isChangeAppSettingsClickedSingleLiveEvent.getValue()
                );
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(isChangeAppSettingsClickedSingleLiveEvent, changeAppSettings -> {
                combine(
                    hasGpsPermissionLiveData.getValue(),
                    hasGpsPermissionBeenAskedMutableLiveData.getValue(),
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
        @Nullable Boolean hasGpsPermission,
        @Nullable Boolean hasGpsPermissionBeenAsked,
        @Nullable Boolean isShowRationale,
        @Nullable Boolean changeAppSettings) {
        if (hasGpsPermission == null || hasGpsPermissionBeenAsked == null) {
            return;
        }

        boolean showRationale = isShowRationale != null;

        boolean changeAppSettingsClicked = changeAppSettings != null;

        if (hasGpsPermission) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.CONTINUE_TO_AUTHENTICATION);
        } else if (changeAppSettingsClicked) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.GO_APP_SETTINGS);
        } else if (hasGpsPermissionBeenAsked || showRationale) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.SHOW_RATIONALE);
        } else {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.ASK_Gps_PERMISSION);
        }
    }

    public void onPermissionResult() {
        hasGpsPermissionBeenAskedMutableLiveData.setValue(true);
    }

    public void onAllowClicked(boolean shouldShowRequestPermissionRationale) {
        isShowRationaleMutableLiveData.setValue(shouldShowRequestPermissionRationale);
    }

    public void onChangeAppSettingsClicked() {
        isChangeAppSettingsClickedSingleLiveEvent.setValue(true);
    }
}
