package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.gps_location.GpsLocationRepositoryBroadcastReceiver;
import com.google.android.gms.maps.model.LatLng;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MapViewModel extends ViewModel {

    private final GpsLocationRepositoryBroadcastReceiver locationRepositoryBroadcastReceiver;

    @Inject
    public MapViewModel(
        @NonNull GpsLocationRepositoryBroadcastReceiver locationRepositoryBroadcastReceiver
    ) {
        this.locationRepositoryBroadcastReceiver = locationRepositoryBroadcastReceiver;
    }


    public LiveData<MapViewState> getMapViewStateLiveData() {
        return Transformations.switchMap(locationRepositoryBroadcastReceiver.getLocationLiveData(), location -> {
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
        });
    }

}
