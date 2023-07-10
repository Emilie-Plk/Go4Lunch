package com.emplk.go4lunch.data.settings;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.emplk.go4lunch.domain.settings.NotificationRepository;
import com.emplk.go4lunch.workmanager.NotificationWorker;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationRepositoryImplementation implements NotificationRepository {
    private static final String KEY_NOTIFICATION_ENABLED = "NOTIFICATION_ENABLED";

    private final static String NOTIFICATION_WORKER = "NOTIFICATION_WORKER";

    @NonNull
    private final SharedPreferences sharedPreferences;

    @NonNull
    private final WorkManager workManager;

    @Inject
    public NotificationRepositoryImplementation(
        @NonNull SharedPreferences sharedPreferences,
        @NonNull WorkManager workManager
    ) {
        this.sharedPreferences = sharedPreferences;
        this.workManager = workManager;
    }

    @Override
    public boolean isNotificationEnabled() {
        return sharedPreferences.getBoolean(KEY_NOTIFICATION_ENABLED, true);
    }

    @Override
    public void setNotificationEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_NOTIFICATION_ENABLED, enabled).apply();
    }

    @Override
    @NonNull
    public PeriodicWorkRequest scheduleNotification(long delay) {
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
            NotificationWorker.class,
            24,
            TimeUnit.HOURS)
            .addTag(NOTIFICATION_WORKER)
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .build();

        Log.d("MainApplication", "Scheduled time: " + workRequest.getWorkSpec().initialDelay + " " + workRequest.getWorkSpec().constraints);

        workManager
            .enqueueUniquePeriodicWork(
                NOTIFICATION_WORKER,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            );

        return workRequest;
    }

    @Override
    public void cancelNotification() {
        workManager.cancelAllWorkByTag(NOTIFICATION_WORKER);
    }
}
