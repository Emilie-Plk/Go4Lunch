package com.emplk.go4lunch.domain.settings.use_case;

import android.util.Log;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.settings.NotificationRepository;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalTime;

import javax.inject.Inject;

public class ScheduleWorkManagerForNotificationUseCase {

    @NonNull
    private final NotificationRepository notificationRepository;

    @NonNull
    private final Clock clock;

    @Inject
    public ScheduleWorkManagerForNotificationUseCase(
        @NonNull NotificationRepository notificationRepository,
        @NonNull Clock clock
    ) {
        this.notificationRepository = notificationRepository;
        this.clock = clock;
    }

    public void invoke() {
        if (notificationRepository.isNotificationEnabled()) {
            notificationRepository.scheduleNotification(calculateDelayUntilNoon());
        } else {
            notificationRepository.cancelNotification();
        }
    }

    private long calculateDelayUntilNoon() {
        Duration delay = Duration.between(LocalTime.now(clock), LocalTime.of(15, 35));
        if (delay.isNegative()) {
            delay = delay.plusDays(1);
        }
        Log.d("TAG", "Notification scheduled in " + delay.toMinutes() + " minutes");
        return delay.toMillis();
    }
}
