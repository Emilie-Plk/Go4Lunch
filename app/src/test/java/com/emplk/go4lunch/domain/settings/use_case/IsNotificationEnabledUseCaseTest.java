package com.emplk.go4lunch.domain.settings.use_case;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.settings.NotificationRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IsNotificationEnabledUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final NotificationRepository notificationRepository = mock(NotificationRepository.class);

    private IsNotificationEnabledUseCase isNotificationEnabledUseCase;

    @Before
    public void setUp() {
        isNotificationEnabledUseCase = new IsNotificationEnabledUseCase(notificationRepository);
    }

@Test
    public void notificationIsEnabled() {
        // Given
    doReturn(true).when(notificationRepository).isNotificationEnabled();
        // When
        Boolean result = getValueForTesting(isNotificationEnabledUseCase.invoke());
        // Then
    assertEquals(true, result);
    }

    @Test
    public void notificationIsDisabled() {
        // Given
        doReturn(false).when(notificationRepository).isNotificationEnabled();
        // When
        Boolean result = getValueForTesting(isNotificationEnabledUseCase.invoke());
        // Then
        assertEquals(false, result);
    }
}