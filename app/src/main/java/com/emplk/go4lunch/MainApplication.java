package com.emplk.go4lunch;

import static com.emplk.go4lunch.workmanager.NotificationUtils.calculateDelayUntilNoon;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.emplk.go4lunch.data.location.GpsLocationRepositoryBroadcastReceiver;
import com.emplk.go4lunch.data.permission.GpsPermissionRepositoryImpl;
import com.emplk.go4lunch.workmanager.NotificationWorker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MainApplication extends Application implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "NotificationWorker";
    @Inject
    GpsPermissionRepositoryImpl gpsPermissionRepositoryImpl;
    @Inject
    GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver;

    private int activityCount;

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(this);
        createWorkRequest();

    }

    private void createWorkRequest() {
        long delayUntilNoon = calculateDelayUntilNoon();
        OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(NotificationWorker.class)
            .setInitialDelay(delayUntilNoon, TimeUnit.MILLISECONDS)
            .addTag(TAG)
            .build();

        WorkManager.getInstance(this).enqueueUniqueWork(TAG, ExistingWorkPolicy.REPLACE, workRequest);
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

}
