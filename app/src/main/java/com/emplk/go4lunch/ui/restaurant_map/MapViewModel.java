package com.emplk.go4lunch.ui.restaurant_map;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.gps_location.GPSLocationRepositoryImpl;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    private final GPSLocationRepositoryImpl gpsLocationRepository;

    private final MediatorLiveData<MapViewState> mapViewStateMediatorLiveData = new MediatorLiveData<>();

    @Inject
    public MapViewModel(
        @NonNull GPSLocationRepositoryImpl gpsLocationRepository
    ) {
        this.gpsLocationRepository = gpsLocationRepository;

        mapViewStateMediatorLiveData.addSource(gpsLocationRepository.getLocationLiveData(), location -> {
                combine(location);
            }
        );
    }

    private void combine(@Nullable Location location) {
        if (location == null) {
            return;
        }

        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MapViewState mapViewState = new MapViewState(latLng);
        mapViewStateMediatorLiveData.setValue(mapViewState);
    }

    public LiveData<MapViewState> getLocationLiveData() {
        return mapViewStateMediatorLiveData;
    }
}
