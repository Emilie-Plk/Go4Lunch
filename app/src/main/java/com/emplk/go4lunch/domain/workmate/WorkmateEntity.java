package com.emplk.go4lunch.domain.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import java.util.Objects;

public class WorkmateEntity {

    @NonNull
    private final LoggedUserEntity loggedUserEntity;

    @Nullable
    private final String attendingRestaurantId;

    public WorkmateEntity(
        @NonNull LoggedUserEntity loggedUserEntity,
        @Nullable String attendingRestaurantId
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.attendingRestaurantId = attendingRestaurantId;
    }

    @NonNull
    public LoggedUserEntity getLoggedUserEntity() {
        return loggedUserEntity;
    }

    @Nullable
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmateEntity that = (WorkmateEntity) o;
        return loggedUserEntity.equals(that.loggedUserEntity) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUserEntity, attendingRestaurantId);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmateEntity{" +
            "loggedUserEntity=" + loggedUserEntity +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            '}';
    }
}
