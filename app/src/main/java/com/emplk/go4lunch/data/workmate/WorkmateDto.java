package com.emplk.go4lunch.data.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.data.user.LoggedUserDto;

import java.util.Objects;

public class WorkmateDto {
    @Nullable
    private final LoggedUserDto loggedUserDto;

    @Nullable
    private final String attendingRestaurantId;

    @Nullable
    private final String attendingRestaurantName;


    public WorkmateDto() {
        this(null, null, null);
    }

    public WorkmateDto(
        @Nullable LoggedUserDto loggedUserDto,
        @Nullable String attendingRestaurantId,
        @Nullable String attendingRestaurantName
    ) {
        this.loggedUserDto = loggedUserDto;
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
    }

    @Nullable
    public LoggedUserDto getLoggedUserDto() {
        return loggedUserDto;
    }

    @Nullable
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
    }

    @Nullable
    public String getAttendingRestaurantName() {
        return attendingRestaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmateDto that = (WorkmateDto) o;
        return Objects.equals(loggedUserDto, that.loggedUserDto) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId) && Objects.equals(attendingRestaurantName, that.attendingRestaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUserDto, attendingRestaurantId, attendingRestaurantName);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmateDto{" +
            "loggedUserDto=" + loggedUserDto +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            '}';
    }
}
