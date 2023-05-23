package com.emplk.go4lunch.data.gps;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.emplk.go4lunch.domain.gps.GpsLocationRepository;
import com.emplk.go4lunch.domain.gps.GpsResponse;
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

    private static final int LOCATION_REQUEST_INTERVAL_MS = 20_000;
    private static final int SMALLEST_DISPLACEMENT_THRESHOLD_METER = 100;

    @NonNull
    private final FusedLocationProviderClient fusedLocationProviderClient;

    @NonNull
    private final MutableLiveData<Location> locationMutableLiveData = new MutableLiveData<>(null);

    @NonNull
    private final MutableLiveData<Boolean> isGpsEnabledMutableLiveData = new MutableLiveData<>();

    @Nullable
    private final LocationManager locationManager;

    private final LocationCallback locationCallback;

    @Inject
    public GpsLocationRepositoryBroadcastReceiver(
        @NonNull FusedLocationProviderClient fusedLocationProviderClient,
        @NonNull @ApplicationContext Context context
    ) {
        this.fusedLocationProviderClient = fusedLocationProviderClient;

        IntentFilter intentFilter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        intentFilter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        context.registerReceiver(this, intentFilter);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                Log.d("Emilie", "onLocationResult() called with: locationResult = [" + locationResult + "]");
                Location location = locationResult.getLastLocation();
                locationMutableLiveData.setValue(location);
            }
        };

        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public LiveData<Location> getLocationLiveData() {
        Location location = fusedLocationProviderClient.getLastLocation().getResult();

        return locationMutableLiveData;
    }

    @Override
    public LiveData<Boolean> isGpsEnabled() {
        return isGpsEnabledMutableLiveData;
    }

    public LiveData<GpsResponse> getGpsResponseLiveData() {
        MediatorLiveData<GpsResponse> gpsResponseMediatorLiveData = new MediatorLiveData<>();
        Observer<Object> observer = object -> {
            GpsResponse gpsResponse;

            if (isGpsEnabledMutableLiveData.getValue() != null && !isGpsEnabledMutableLiveData.getValue()) {
                gpsResponse = new GpsResponse.GpsProviderDisabled();
            } else {
                gpsResponse = new GpsResponse.Success(locationMutableLiveData.getValue());
            }

            gpsResponseMediatorLiveData.setValue(gpsResponse);
        };

        gpsResponseMediatorLiveData.addSource(locationMutableLiveData, observer);
        gpsResponseMediatorLiveData.addSource(isGpsEnabledMutableLiveData, observer);

        return gpsResponseMediatorLiveData;
    }

    @Override
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public void startLocationRequest() {
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

    @Override
    public void stopLocationRequest() {
        if (locationCallback != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
    }

    @Override
    public void onReceive(
        @NonNull Context context,
        @NonNull Intent intent
    ) {
        if (Intent.ACTION_PROVIDER_CHANGED.equals(intent.getAction())) {
            if (locationManager != null) {
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                isGpsEnabledMutableLiveData.setValue(isGpsEnabled);
            }
        }
    }
}
