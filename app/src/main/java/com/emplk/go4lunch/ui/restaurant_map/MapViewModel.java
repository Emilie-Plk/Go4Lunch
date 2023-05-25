package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.gps.VerifyGpsEnabledUseCase;
import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.map.DisplayCurrentMapViewUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {


    @NonNull
    private final VerifyGpsEnabledUseCase verifyGpsEnabledUseCase;

    @NonNull
    private final GetCurrentLocationUseCase getCurrentLocationUseCase;

    @NonNull
    private final StartLocationRequestUseCase startLocationRequestUseCase;

    @NonNull
    private final DisplayCurrentMapViewUseCase displayCurrentMapViewUseCase;

    @Inject
    public MapViewModel(
        @NonNull VerifyGpsEnabledUseCase verifyGpsEnabledUseCase,
        @NonNull GetCurrentLocationUseCase getCurrentLocationUseCase,
        @NonNull StartLocationRequestUseCase startLocationRequestUseCase,
        @NonNull DisplayCurrentMapViewUseCase displayCurrentMapViewUseCase
    ) {
        this.verifyGpsEnabledUseCase = verifyGpsEnabledUseCase;
        this.getCurrentLocationUseCase = getCurrentLocationUseCase;
        this.startLocationRequestUseCase = startLocationRequestUseCase;
        this.displayCurrentMapViewUseCase = displayCurrentMapViewUseCase;
    }


    public LiveData<MapViewState> getMapViewStateLiveData() {
       return displayCurrentMapViewUseCase.invoke();
    }

    public LiveData<Boolean> isGpsEnabled() {
        return verifyGpsEnabledUseCase.invoke();
    }

    public void refreshLocationRequest() {
        startLocationRequestUseCase.invoke();
    }

}
