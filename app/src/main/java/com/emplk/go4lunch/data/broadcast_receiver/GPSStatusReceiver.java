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

@Singleton
public class GPSStatusReceiver extends BroadcastReceiver {

    private final MutableLiveData<Boolean> gpsStatusMutableLiveData = new MutableLiveData<>();



    @Inject
    public GPSStatusReceiver() {
    }

    @Override
    public void onReceive(@NonNull Context context, @NonNull Intent intent) {
        if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                gpsStatusMutableLiveData.postValue(isGpsEnabled);
            }
        }
    }

    public LiveData<Boolean> getGpsStatusLiveData() {
        return gpsStatusMutableLiveData;
    }
}

