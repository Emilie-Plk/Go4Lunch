package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.gps_location.GPSLocationRepositoryImpl;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    private final GPSLocationRepositoryImpl gpsLocationRepository;

    @Inject
    public MapViewModel(
        @NonNull GPSLocationRepositoryImpl gpsLocationRepository
    ) {
        this.gpsLocationRepository = gpsLocationRepository;
    }

    /*   public LiveData<MapViewState> getLocationLiveData() {
     return Transformations.switchMap(gpsLocationRepository.getLocationLiveData(), location -> {

        }
        );
    }*/
}
