package com.emplk.go4lunch.domain.settings;

public interface NotificationRepository {

    public boolean isNotificationEnabled();

    public void setNotificationEnabled(boolean enabled);
}
