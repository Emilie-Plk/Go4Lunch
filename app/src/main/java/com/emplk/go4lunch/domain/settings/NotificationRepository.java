package com.emplk.go4lunch.domain.settings;

public interface NotificationRepository {

    boolean isNotificationEnabled();

    void setNotificationEnabled(boolean enabled);

    void scheduleNotification(long delay);

    void cancelNotification();
}
