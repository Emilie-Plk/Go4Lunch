package com.emplk.go4lunch.domain.settings.use_case;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.settings.NotificationRepository;

import javax.inject.Inject;

public class IsNotificationEnabledUseCase {
    @NonNull
    private final NotificationRepository notificationRepository;

    @Inject
    public IsNotificationEnabledUseCase(@NonNull NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public LiveData<Boolean> invoke() {
        return new MutableLiveData<>(notificationRepository.isNotificationEnabled());
    }
}

