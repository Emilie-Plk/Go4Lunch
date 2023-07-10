package com.emplk.go4lunch.domain.settings;

import androidx.annotation.NonNull;
import androidx.work.PeriodicWorkRequest;

public interface NotificationRepository {

    boolean isNotificationEnabled();

    void setNotificationEnabled(boolean enabled);

    @NonNull
    PeriodicWorkRequest scheduleNotification(long delay);

    void cancelNotification();
}
