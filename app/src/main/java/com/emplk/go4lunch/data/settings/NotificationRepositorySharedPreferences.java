package com.emplk.go4lunch.data.settings;

import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.settings.NotificationRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NotificationRepositorySharedPreferences implements NotificationRepository {
    private static final String KEY_NOTIFICATION_ENABLED = "NOTIFICATION_ENABLED";

    @NonNull
    private final SharedPreferences sharedPreferences;

    @Inject
    public NotificationRepositorySharedPreferences(@NonNull SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public boolean isNotificationEnabled() {
        return sharedPreferences.getBoolean(KEY_NOTIFICATION_ENABLED, true);
    }

    @Override
    public void setNotificationEnabled(boolean enabled) {
        sharedPreferences.edit().putBoolean(KEY_NOTIFICATION_ENABLED, enabled).apply();
    }
}
