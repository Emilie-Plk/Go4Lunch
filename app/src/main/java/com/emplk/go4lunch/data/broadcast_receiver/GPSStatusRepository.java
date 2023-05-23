package com.emplk.go4lunch.data.broadcast_receiver;

import android.app.Application;
import android.content.IntentFilter;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GPSStatusRepository {
    @NonNull
    private final GPSStatusReceiver gpsStatusReceiver;
    @NonNull
    private final Application application;

    @Inject
    public GPSStatusRepository(
        @NonNull GPSStatusReceiver gpsStatusReceiver,
        @NonNull Application application) {
        this.gpsStatusReceiver = gpsStatusReceiver;
        this.application = application;

    }

    public LiveData<Boolean> isGPSActivated() {
        return gpsStatusReceiver.getGpsStatusLiveData();
    }

    public void unregisterReceiver() {
        application.unregisterReceiver(gpsStatusReceiver);
    }
}
