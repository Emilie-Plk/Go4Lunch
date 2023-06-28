package com.emplk.go4lunch;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.hilt.work.HiltWorkerFactory;
import androidx.work.Configuration;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.emplk.go4lunch.data.location.GpsLocationRepositoryBroadcastReceiver;
import com.emplk.go4lunch.data.permission.GpsPermissionRepositoryImpl;
import com.emplk.go4lunch.workmanager.NotificationWorker;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application implements Application.ActivityLifecycleCallbacks, Configuration.Provider {

    @Inject
    HiltWorkerFactory hiltWorkerFactory;

    @Inject
    WorkManager workManager;

    @Inject
    Clock clock;

    @Inject
    GpsPermissionRepositoryImpl gpsPermissionRepositoryImpl;
    @Inject
    GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver;

    private int activityCount;

    private final static String NOTIFICATION_WORKER = "NOTIFICATION_WORKER";

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        createWorkRequest();
    }

    private void createWorkRequest() {
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
            NotificationWorker.class,
            24,
            TimeUnit.HOURS)
            .setInitialDelay(calculateDelayUntilNoon(), TimeUnit.MILLISECONDS)
            .build();

        Log.d("MainApplication", "Scheduled time: " + workRequest.getWorkSpec().initialDelay + " " + workRequest.getWorkSpec().constraints);

        workManager
            .enqueueUniquePeriodicWork(
                NOTIFICATION_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            );
    }

    @Override
    public void onActivityCreated(
        @NonNull Activity activity,
        @Nullable Bundle savedInstanceState
    ) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        activityCount++;
        gpsPermissionRepositoryImpl.refreshGpsPermission();
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        activityCount--;
        if (activityCount == 0) {
            gpsLocationRepositoryBroadcastReceiver.stopLocationRequest();
            unregisterReceiver(gpsLocationRepositoryBroadcastReceiver);
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

    private long calculateDelayUntilNoon() {
        Duration delay = Duration.between(LocalTime.now(clock), LocalTime.NOON);
        if (delay.isNegative()) {
            delay = delay.plusDays(1);
        }
        return delay.toMillis();
    }
}
