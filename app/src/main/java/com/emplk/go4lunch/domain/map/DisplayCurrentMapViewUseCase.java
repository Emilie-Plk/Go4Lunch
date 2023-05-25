package com.emplk.go4lunch.domain.map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.emplk.go4lunch.domain.location.GetCurrentLocationUseCase;
import com.emplk.go4lunch.ui.restaurant_map.MapViewState;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

public class DisplayCurrentMapViewUseCase {

    @NonNull
    private final GetCurrentLocationUseCase getCurrentLocationUseCase;

    @Inject
    public DisplayCurrentMapViewUseCase(@NonNull GetCurrentLocationUseCase getCurrentLocationUseCase) {
        this.getCurrentLocationUseCase = getCurrentLocationUseCase;
    }

    public LiveData<MapViewState> invoke() {
        return Transformations.switchMap(getCurrentLocationUseCase.invoke(), location -> {
                MutableLiveData<MapViewState> mapViewStateMutableLiveData = new MutableLiveData<>();
                if (location != null &&
                    location.getLatitude() != null &&
                    location.getLongitude() != null
                ) {
                    MapViewState mapViewState = new MapViewState(
                        new LatLng(location.getLatitude(), location.getLongitude()
                        )
                    );
                    mapViewStateMutableLiveData.setValue(mapViewState);
                }
                return mapViewStateMutableLiveData;
            }
        );
    }
}
