package com.emplk.go4lunch.data.GPSlocation;

import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GPSLocationRepository {

    private static final int LOCATION_REQUEST_INTERVAL_MS = 10000;
    private static final int SMALLEST_DISPLACEMENT_THRESHOLD_METER = 5000; // 5000 seems a lot!?..

    @NonNull
    private final FusedLocationProviderClient fusedLocationProviderClient;

    @NonNull
    private final MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>(null);

    private LocationCallback locationCallback;

    @Inject
    public GPSLocationRepository(@NonNull FusedLocationProviderClient fusedLocationProviderClient) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;
    }

    public LiveData<Location> getLocationLiveData() {
        return locationMutableLiveData;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public void startLocationRequest() {
        if (locationCallback == null) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                   // BigDecimal latitude = BigDecimal.valueOf(location.getLatitude()).setScale(5, RoundingMode.HALF_UP);
                  /*  BigDecimal longitude = BigDecimal.valueOf(location.getLongitude()).setScale(5, RoundingMode.HALF_UP);
                    Location roundedLocation = new Location(location);
                    roundedLocation.setLatitude(latitude.doubleValue());
                    roundedLocation.setLongitude(longitude.doubleValue());*/  // TODO: I wanted to limit the API calls if the user just walked for 10m or so...
                    locationMutableLiveData.setValue(location);
                }
            };
        }

        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_REQUEST_INTERVAL_MS)
            .setMinUpdateIntervalMillis(SMALLEST_DISPLACEMENT_THRESHOLD_METER)
            .setMinUpdateDistanceMeters(LOCATION_REQUEST_INTERVAL_MS)
            .build();

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        );
    }


    public void stopLocationRequest() {
        if (locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }
}