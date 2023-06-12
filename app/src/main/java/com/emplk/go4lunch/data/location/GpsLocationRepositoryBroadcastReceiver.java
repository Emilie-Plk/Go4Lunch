package com.emplk.go4lunch.data.location;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.emplk.go4lunch.domain.gps.GpsLocationRepository;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.Priority;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class GpsLocationRepositoryBroadcastReceiver extends BroadcastReceiver implements GpsLocationRepository {

    private static final int LOCATION_REQUEST_INTERVAL_MS = 8_000;
    private static final int SMALLEST_DISPLACEMENT_THRESHOLD_METER = 100;

    @NonNull
    private final FusedLocationProviderClient fusedLocationProviderClient;

    @NonNull
    private final MutableLiveData<LocationEntity> gpsLocationEntityMutableLiveData = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<Boolean> isGpsEnabledMutableLiveData = new MutableLiveData<>();

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            Log.d(TAG, "onLocationResult() called with: locationResult " + locationResult.getLastLocation());
            Location location = locationResult.getLastLocation();
            if (location != null) {
                LocationEntity locationEntity = new LocationEntity(location.getLatitude(), location.getLongitude());
                gpsLocationEntityMutableLiveData.setValue(locationEntity);
            }
        }
    };

    private final LocationManager locationManager;

    @Inject
    public GpsLocationRepositoryBroadcastReceiver(
        @NonNull @ApplicationContext Context context,
        @NonNull FusedLocationProviderClient fusedLocationProviderClient
    ) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;

        IntentFilter intentFilter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        intentFilter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        context.registerReceiver(this, intentFilter);

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public LiveData<LocationStateEntity> getLocationStateLiveData() {
        MediatorLiveData<LocationStateEntity> gpsResponseMediatorLiveData = new MediatorLiveData<>();
        Observer<Object> observer = new Observer<Object>() {
            @Override
            public void onChanged(Object object) {
                LocationStateEntity locationStateEntity;

                Boolean isGpsEnabled = isGpsEnabledMutableLiveData.getValue();
                LocationEntity locationEntity = gpsLocationEntityMutableLiveData.getValue();

                if (isGpsEnabled != null && !isGpsEnabled) {
                    locationStateEntity = new LocationStateEntity.GpsProviderDisabled();
                } else if (locationEntity == null) {
                    return;
                } else {
                    locationStateEntity = new LocationStateEntity.Success(locationEntity);
                }

                gpsResponseMediatorLiveData.setValue(locationStateEntity);
            }
        };
        gpsResponseMediatorLiveData.addSource(gpsLocationEntityMutableLiveData, observer);
        gpsResponseMediatorLiveData.addSource(isGpsEnabledMutableLiveData, observer);

        return gpsResponseMediatorLiveData;
    }

    @Override
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public void startLocationRequest() {
        LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, LOCATION_REQUEST_INTERVAL_MS)
            .setMinUpdateIntervalMillis(LOCATION_REQUEST_INTERVAL_MS)
            .setMinUpdateDistanceMeters(SMALLEST_DISPLACEMENT_THRESHOLD_METER)
            .build();

        fusedLocationProviderClient.removeLocationUpdates(locationCallback);

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        );
    }

    @Override
    public void onReceive(
        @NonNull Context context,
        @NonNull Intent intent
    ) {
        String action = intent.getAction();
        if (action != null && action.equals(LocationManager.PROVIDERS_CHANGED_ACTION)) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isGpsEnabledMutableLiveData.setValue(isGpsEnabled);
            }
        }
    }

    @Override
    public void stopLocationRequest() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }
}
