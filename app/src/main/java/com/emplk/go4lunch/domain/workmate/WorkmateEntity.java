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

    @Nullable
    private final String attendingRestaurantName;

    @Nullable
    private final String attendingRestaurantVicinity;

    public WorkmateEntity(
        @NonNull LoggedUserEntity loggedUserEntity,
        @Nullable String attendingRestaurantId,
        @Nullable String attendingRestaurantName,
        @Nullable String attendingRestaurantVicinity
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
        this.attendingRestaurantVicinity = attendingRestaurantVicinity;
    }

    @NonNull
    public LoggedUserEntity getLoggedUserEntity() {
        return loggedUserEntity;
    }

    @Nullable
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
    }

    @Nullable
    public String getAttendingRestaurantName() {
        return attendingRestaurantName;
    }

    @Nullable
    public String getAttendingRestaurantVicinity() {
        return attendingRestaurantVicinity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmateEntity that = (WorkmateEntity) o;
        return loggedUserEntity.equals(that.loggedUserEntity) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId) && Objects.equals(attendingRestaurantName, that.attendingRestaurantName) && Objects.equals(attendingRestaurantVicinity, that.attendingRestaurantVicinity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUserEntity, attendingRestaurantId, attendingRestaurantName, attendingRestaurantVicinity);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmateEntity{" +
            "loggedUserEntity=" + loggedUserEntity +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            ", attendingRestaurantVicinity='" + attendingRestaurantVicinity + '\'' +
            '}';
    }
}
