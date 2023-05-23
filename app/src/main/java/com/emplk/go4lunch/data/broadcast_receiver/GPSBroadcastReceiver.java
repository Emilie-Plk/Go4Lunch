package com.emplk.go4lunch.data.broadcast_receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.hilt.android.qualifiers.ApplicationContext;

@Singleton
public class GPSBroadcastReceiver extends BroadcastReceiver {

    private final MutableLiveData<Boolean> gpsStatusMutableLiveData = new MutableLiveData<>();


    @Inject
    public GPSBroadcastReceiver(@NonNull @ApplicationContext Context context) {
        IntentFilter intentFilter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        intentFilter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        context.registerReceiver(this, intentFilter);
    }

    public LiveData<Boolean> getGpsStatusLiveData() {
        return gpsStatusMutableLiveData;
    }


    // should I unregister the receiver?

    @Override
    public void onReceive(
        @NonNull Context context,
        @NonNull Intent intent
    ) {
        if (Intent.ACTION_PROVIDER_CHANGED.equals(intent.getAction())) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                gpsStatusMutableLiveData.setValue(isGpsEnabled);
            }
        }
    }
}

