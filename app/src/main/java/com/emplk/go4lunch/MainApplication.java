package com.emplk.go4lunch;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

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
import java.time.LocalDateTime;
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
        long delayUntilNoon = calculateDelayUntilNoon();
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
            NotificationWorker.class,
            24,
            TimeUnit.HOURS)
            .setInitialDelay(delayUntilNoon, TimeUnit.MILLISECONDS)
            .build();

        WorkManager
            .getInstance(this)
            .enqueueUniquePeriodicWork(
                NOTIFICATION_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest);
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
        LocalDateTime currentDateTime = LocalDateTime.now(clock);
        LocalTime notificationTime = LocalTime.of(8, 29);

        LocalDateTime desiredDateTime = currentDateTime.with(notificationTime);

        // If the notification time has already passed today, move to the day after
        if (currentDateTime.isAfter(desiredDateTime)) {
            desiredDateTime = desiredDateTime.plusDays(1);
        }

        // Calculate the duration until the desired time
        return Duration.between(currentDateTime, desiredDateTime).toMillis();
    }
}
