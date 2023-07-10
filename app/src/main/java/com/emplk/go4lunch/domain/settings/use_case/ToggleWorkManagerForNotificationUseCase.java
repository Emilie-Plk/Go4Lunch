package com.emplk.go4lunch.domain.settings.use_case;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.settings.NotificationRepository;

import javax.inject.Inject;

public class ToggleWorkManagerForNotificationUseCase {

    @NonNull
    private final NotificationRepository notificationRepository;

    @NonNull
    private final ScheduleWorkManagerForNotificationUseCase scheduleWorkManagerForNotificationUseCase;

    @Inject
    public ToggleWorkManagerForNotificationUseCase(
        @NonNull NotificationRepository notificationRepository,
        @NonNull ScheduleWorkManagerForNotificationUseCase scheduleWorkManagerForNotificationUseCase
    ) {
        this.notificationRepository = notificationRepository;
        this.scheduleWorkManagerForNotificationUseCase = scheduleWorkManagerForNotificationUseCase;
    }

    public void invoke() {
        notificationRepository.setNotificationEnabled(!notificationRepository.isNotificationEnabled());
        scheduleWorkManagerForNotificationUseCase.invoke();
    }
}
