package com.emplk.go4lunch;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.Configuration;

import com.emplk.go4lunch.data.location.GpsLocationRepositoryBroadcastReceiver;
import com.emplk.go4lunch.data.permission.GpsPermissionRepositoryImpl;
import com.emplk.go4lunch.domain.settings.use_case.ScheduleWorkManagerForNotificationUseCase;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application implements Application.ActivityLifecycleCallbacks, Configuration.Provider {

    @Inject
    HiltWorkerFactory hiltWorkerFactory;

    @Inject
    GpsPermissionRepositoryImpl gpsPermissionRepositoryImpl;
    @Inject
    GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver;

    @Inject
    ScheduleWorkManagerForNotificationUseCase scheduleWorkManagerForNotificationUseCase;

    private int activityCount;

    private boolean isGpsReceiverRegistered = false;

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(this);
        registerGpsReceiver();
        scheduleWorkManagerForNotificationUseCase.invoke();
    }

    private void registerGpsReceiver() {
        IntentFilter intentFilter = new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION);
        intentFilter.addAction(Intent.ACTION_PROVIDER_CHANGED);
        registerReceiver(gpsLocationRepositoryBroadcastReceiver, intentFilter);
        isGpsReceiverRegistered = true;
    }


    @Override
    public void onActivityCreated(
        @NonNull Activity activity,
        @Nullable Bundle savedInstanceState
    ) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        activityCount++;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        gpsPermissionRepositoryImpl.refreshGpsPermission();
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activityCount--;
        if (activityCount == 0 && isGpsReceiverRegistered) {
            gpsLocationRepositoryBroadcastReceiver.stopLocationRequest();
            unregisterReceiver(gpsLocationRepositoryBroadcastReceiver);
            isGpsReceiverRegistered = false;
        }
    }

    @Override
    public void onActivitySaveInstanceState(
        @NonNull Activity activity,
        @NonNull Bundle outState
    ) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }

    @NonNull
    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build();
    }
}
