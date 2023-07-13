package com.emplk.go4lunch.domain.settings.use_case;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.settings.NotificationRepository;

import org.junit.Before;
import org.junit.Test;

public class ToggleWorkManagerForNotificationUseCaseTest {

    private final NotificationRepository notificationRepository = mock(NotificationRepository.class);

    private final ScheduleWorkManagerForNotificationUseCase scheduleWorkManagerForNotificationUseCase = mock(ScheduleWorkManagerForNotificationUseCase.class);

    private ToggleWorkManagerForNotificationUseCase toggleWorkManagerForNotificationUseCase;

    @Before
    public void setUp() {
        toggleWorkManagerForNotificationUseCase = new ToggleWorkManagerForNotificationUseCase(notificationRepository, scheduleWorkManagerForNotificationUseCase);
    }

    @Test
    public void enableNotification() {
        // Given
        doReturn(false).when(notificationRepository).isNotificationEnabled();

        // When
        toggleWorkManagerForNotificationUseCase.invoke();

        // Then
        verify(notificationRepository).setNotificationEnabled(true);
        verify(notificationRepository).isNotificationEnabled();
        verify(scheduleWorkManagerForNotificationUseCase).invoke();
        verifyNoMoreInteractions(notificationRepository, scheduleWorkManagerForNotificationUseCase);
    }

    @Test
    public void disableNotification() {
        // Given
        doReturn(true).when(notificationRepository).isNotificationEnabled();

        // When
        toggleWorkManagerForNotificationUseCase.invoke();

        // Then
        verify(notificationRepository).setNotificationEnabled(false);
        verify(notificationRepository).isNotificationEnabled();
        verify(scheduleWorkManagerForNotificationUseCase).invoke();
        verifyNoMoreInteractions(notificationRepository, scheduleWorkManagerForNotificationUseCase);
    }
}