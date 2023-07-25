package com.emplk.go4lunch.data.settings;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import android.content.SharedPreferences;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import org.junit.Before;
import org.junit.Test;

public class NotificationRepositoryImplementationTest {

    private static final String KEY_NOTIFICATION_ENABLED = "NOTIFICATION_ENABLED";

    private final static String NOTIFICATION_WORKER = "NOTIFICATION_WORKER";
    private final SharedPreferences sharedPreferences = mock(SharedPreferences.class);

    private final SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);


    private final WorkManager workManager = mock(WorkManager.class);

    private NotificationRepositoryImplementation notificationRepositoryImplementation;

    @Before
    public void setUp() {
        doReturn(editor).when(sharedPreferences).edit();
        doReturn(editor).when(editor).putBoolean(eq(KEY_NOTIFICATION_ENABLED), anyBoolean());

        notificationRepositoryImplementation = new NotificationRepositoryImplementation(sharedPreferences, workManager);
    }

    @Test
    public void notificationsIsEnabled() {
        // Given
        given(sharedPreferences.getBoolean(KEY_NOTIFICATION_ENABLED, true)).willReturn(true);

        // When
        boolean result = notificationRepositoryImplementation.isNotificationEnabled();

        assertTrue(result);
    }

    @Test
    public void notificationsIsDisabled() {
        // Given
        given(sharedPreferences.getBoolean(KEY_NOTIFICATION_ENABLED, true)).willReturn(false);

        // When
        boolean result = notificationRepositoryImplementation.isNotificationEnabled();
        assertFalse(result);
    }

    @Test
    public void setNotificationEnabled() {
        // Given
        given(sharedPreferences.getBoolean(KEY_NOTIFICATION_ENABLED, true)).willReturn(false);

        // When
        notificationRepositoryImplementation.setNotificationEnabled(true);

        // Then
        verify(editor).putBoolean(KEY_NOTIFICATION_ENABLED, true);
    }

    @Test
    public void setNotificationDisabled() {
        // Given
        given(sharedPreferences.getBoolean(KEY_NOTIFICATION_ENABLED, true)).willReturn(false);

        // When
        notificationRepositoryImplementation.setNotificationEnabled(false);

        // Then
        verify(editor).putBoolean(KEY_NOTIFICATION_ENABLED, false);
    }

    @Test
    public void onScheduleNotification() {
        // When
        long delay = 7200000L;
        notificationRepositoryImplementation.scheduleNotification(delay);

        // Then
        verify(workManager).enqueueUniquePeriodicWork(
            eq(NOTIFICATION_WORKER),
            eq(ExistingPeriodicWorkPolicy.KEEP),
            any(PeriodicWorkRequest.class)
        );
        verifyNoMoreInteractions(workManager);
    }

    @Test
    public void onCancelNotification_shouldCancelAllWorkByTag() {
        // When
        notificationRepositoryImplementation.cancelNotification();

        // Then
        verify(workManager).cancelAllWorkByTag(NOTIFICATION_WORKER);
    }
}