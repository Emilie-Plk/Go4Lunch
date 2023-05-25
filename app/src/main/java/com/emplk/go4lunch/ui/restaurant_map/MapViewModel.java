package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.gps.VerifyGpsEnabledUseCase;
import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    @NonNull
    private final VerifyGpsEnabledUseCase verifyGpsEnabledUseCase;

    @NonNull
    private final GetCurrentLocationUseCase getCurrentLocationUseCase;

    private final MutableLiveData<MapViewState> mapViewStateMutableLiveData = new MutableLiveData<>();

    @Inject
    public MapViewModel(
        @NonNull VerifyGpsEnabledUseCase verifyGpsEnabledUseCase,
        @NonNull GetCurrentLocationUseCase getCurrentLocationUseCase
    ) {
        this.verifyGpsEnabledUseCase = verifyGpsEnabledUseCase;
        this.getCurrentLocationUseCase = getCurrentLocationUseCase;
    }

    public LiveData<MapViewState> getMapViewStateLiveData() {
        return Transformations.switchMap(getCurrentLocationUseCase.invoke(), location -> {
                MapViewState mapViewState = new MapViewState(
                    new LatLng(location.getLatitude(), location.getLongitude()
                    )
                );
                mapViewStateMutableLiveData.setValue(mapViewState);
                return mapViewStateMutableLiveData;
            }
        );
    }

    public LiveData<Boolean> isGpsEnabled() {
        return verifyGpsEnabledUseCase.invoke();
    }
}
