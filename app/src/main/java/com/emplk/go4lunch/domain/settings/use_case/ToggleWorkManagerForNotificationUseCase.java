package com.emplk.go4lunch.domain.settings.use_case;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.settings.NotificationRepository;

import javax.inject.Inject;

public class ToggleWorkManagerForNotificationUseCase {

    @NonNull
    private final NotificationRepository notificationRepository;

    @NonNull
    private final UpdateWorkManagerForNotificationUseCase updateWorkManagerForNotificationUseCase;

    @Inject
    public ToggleWorkManagerForNotificationUseCase(
        @NonNull NotificationRepository notificationRepository,
        @NonNull UpdateWorkManagerForNotificationUseCase updateWorkManagerForNotificationUseCase
    ) {
        this.notificationRepository = notificationRepository;
        this.updateWorkManagerForNotificationUseCase = updateWorkManagerForNotificationUseCase;
    }

    public void invoke() {
        notificationRepository.setNotificationEnabled(!notificationRepository.isNotificationEnabled());
        updateWorkManagerForNotificationUseCase.invoke();
    }
}
