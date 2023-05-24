package com.emplk.go4lunch;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.data.gps_location.GpsLocationRepositoryBroadcastReceiver;
import com.emplk.go4lunch.data.permission.GpsPermissionRepository;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application implements Application.ActivityLifecycleCallbacks {

    @Inject
    GpsPermissionRepository gpsPermissionRepository;

    @Inject
    GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver;

    int activityCount;


    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        activityCount++;
        gpsPermissionRepository.refreshGpsPermission();
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activityCount--;
        if (activityCount == 0) {
            gpsLocationRepositoryBroadcastReceiver.stopLocationRequest();
        }
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {

    }
}
