package com.emplk.go4lunch.ui.onboarding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class OnBoardingViewModel extends ViewModel {

    private final MutableLiveData<Boolean> shouldShowRationaleMutableLiveData = new MutableLiveData<>();

    private final MediatorLiveData<OnBoardingViewAction> onBoardingViewActionMediatorLiveData = new MediatorLiveData<>();

    private boolean hasGpsPermissionBeenAsked = false;

    @Inject
    public OnBoardingViewModel(@NonNull HasGpsPermissionUseCase hasGpsPermissionUseCase) {

        LiveData<Boolean> hasGpsPermissionLiveData = hasGpsPermissionUseCase.invoke();

        onBoardingViewActionMediatorLiveData.addSource(hasGpsPermissionLiveData, permission -> {
                combine(
                    permission,
                    shouldShowRationaleMutableLiveData.getValue()
                );
            }
        );

        onBoardingViewActionMediatorLiveData.addSource(shouldShowRationaleMutableLiveData, shouldShowRationale -> {
                combine(
                    hasGpsPermissionLiveData.getValue(),
                    shouldShowRationale
                );
            }
        );
    }

    public LiveData<OnBoardingViewAction> getOnBoardingViewAction() {
        return onBoardingViewActionMediatorLiveData;
    }

    private void combine(
        @Nullable Boolean hasGpsPermission,
        @Nullable Boolean shouldShowRationale
    ) {
        if (hasGpsPermission == null) {
            return;
        }

        boolean showRationale = shouldShowRationale != null && shouldShowRationale;

        if (hasGpsPermission) {
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.CONTINUE_TO_AUTHENTICATION);
        } else if (hasGpsPermissionBeenAsked || showRationale) {
            hasGpsPermissionBeenAsked = false;
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.SHOW_RATIONALE);
        } else {
            hasGpsPermissionBeenAsked = true;
            onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.ASK_GPS_PERMISSION);
        }
    }

    public void onAllowClicked(boolean shouldShowRequestPermissionRationale) {
        shouldShowRationaleMutableLiveData.setValue(shouldShowRequestPermissionRationale);
    }

    public void onChangeAppSettingsClicked() {
        onBoardingViewActionMediatorLiveData.setValue(OnBoardingViewAction.GO_APP_SETTINGS);
    }
}
