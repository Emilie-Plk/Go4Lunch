package com.emplk.go4lunch.domain.settings.use_case;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.emplk.go4lunch.domain.settings.NotificationRepository;

import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

public class ScheduleWorkManagerForNotificationUseCaseTest {

    private final NotificationRepository notificationRepository = mock(NotificationRepository.class);
    private final static long EPOCH_MILLI = 1680712318000L;
    private final Clock clock = Clock.fixed(Instant.ofEpochMilli(EPOCH_MILLI), ZoneOffset.UTC);

    private ScheduleWorkManagerForNotificationUseCase scheduleWorkManagerForNotificationUseCase;

    @Before
    public void setUp()  {
        scheduleWorkManagerForNotificationUseCase = new ScheduleWorkManagerForNotificationUseCase(notificationRepository, clock);
    }

    @Test
    public void notificationIsEnabled() {
        // Given
        doReturn(true).when(notificationRepository).isNotificationEnabled();

        // When
        scheduleWorkManagerForNotificationUseCase.invoke();

        // Then
        verify(notificationRepository).scheduleNotification(anyLong());
    }

    @Test
    public void notificationIsDisabled() {
        // Given
        doReturn(false).when(notificationRepository).isNotificationEnabled();

        // When
        scheduleWorkManagerForNotificationUseCase.invoke();

        // Then
        verify(notificationRepository).cancelNotification();;
    }
}