package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.gps_location.GPSLocationRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    private final GPSLocationRepository gpsLocationRepository;

    @Inject
    public MapViewModel(
        @NonNull GPSLocationRepository gpsLocationRepository
    ) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    /*   public LiveData<MapViewState> getLocationLiveData() {
     return Transformations.switchMap(gpsLocationRepository.getLocationLiveData(), location -> {

        }
        );
    }*/
}
