package com.emplk.go4lunch.domain.settings.use_case;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.emplk.go4lunch.domain.settings.NotificationRepository;
import com.emplk.go4lunch.workmanager.NotificationWorker;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

public class UpdateWorkManagerForNotificationUseCase {

    private final static String NOTIFICATION_WORKER = "NOTIFICATION_WORKER";

    @NonNull
    private final WorkManager workManager;

    @NonNull
    private final NotificationRepository notificationRepository;

    @NonNull
    private final Clock clock;

    @Inject
    public UpdateWorkManagerForNotificationUseCase(
        @NonNull WorkManager workManager,
        @NonNull NotificationRepository notificationRepository,
        @NonNull Clock clock
    ) {
        this.workManager = workManager;
        this.notificationRepository = notificationRepository;
        this.clock = clock;
    }

    public void invoke() {
        if (notificationRepository.isNotificationEnabled()) {
            PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                NotificationWorker.class,
                24,
                TimeUnit.HOURS)
                .addTag(NOTIFICATION_WORKER)
                .setInitialDelay(calculateDelayUntilNoon(), TimeUnit.MILLISECONDS)
                .build();

            Log.d("MainApplication", "Scheduled time: " + workRequest.getWorkSpec().initialDelay + " " + workRequest.getWorkSpec().constraints);

            workManager
                .enqueueUniquePeriodicWork(
                    NOTIFICATION_WORKER,
                    ExistingPeriodicWorkPolicy.KEEP,
                    workRequest
                );
        } else {
            workManager.cancelAllWorkByTag(NOTIFICATION_WORKER);
        }
    }

    private long calculateDelayUntilNoon() {
        Duration delay = Duration.between(LocalTime.now(clock), LocalTime.NOON);
        if (delay.isNegative()) {
            delay = delay.plusDays(1);
        }
        return delay.toMillis();
    }
}
