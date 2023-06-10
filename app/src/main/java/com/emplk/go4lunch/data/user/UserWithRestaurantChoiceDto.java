package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class UserWithRestaurantChoiceDto {

    @Nullable
    private final LoggedUserDto loggedUser;

    @Nullable
    private final String attendingRestaurantId;

    @Nullable
    private final String attendingRestaurantName;

    @Nullable
    private final String attendingRestaurantVicinity;

    @Nullable
    private final String attendingRestaurantPictureUrl;

    public UserWithRestaurantChoiceDto() {
        loggedUser = null;
        attendingRestaurantId = null;
        attendingRestaurantName = null;
        attendingRestaurantVicinity = null;
        attendingRestaurantPictureUrl = null;
    }

    public UserWithRestaurantChoiceDto(
        @Nullable LoggedUserDto loggedUser,
        @Nullable String attendingRestaurantId,
        @Nullable String attendingRestaurantName,
        @Nullable String attendingRestaurantVicinity,
        @Nullable String attendingRestaurantPictureUrl
    ) {
        this.loggedUser = loggedUser;
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
        this.attendingRestaurantVicinity = attendingRestaurantVicinity;
        this.attendingRestaurantPictureUrl = attendingRestaurantPictureUrl;
    }

    @Nullable
    public LoggedUserDto getLoggedUser() {
        return loggedUser;
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

    @Nullable
    public String getAttendingRestaurantPictureUrl() {
        return attendingRestaurantPictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWithRestaurantChoiceDto that = (UserWithRestaurantChoiceDto) o;
        return Objects.equals(loggedUser, that.loggedUser) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId) && Objects.equals(attendingRestaurantName, that.attendingRestaurantName) && Objects.equals(attendingRestaurantVicinity, that.attendingRestaurantVicinity) && Objects.equals(attendingRestaurantPictureUrl, that.attendingRestaurantPictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUser, attendingRestaurantId, attendingRestaurantName, attendingRestaurantVicinity, attendingRestaurantPictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserWithRestaurantChoiceDto{" +
            "loggedUser=" + loggedUser +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            ", attendingRestaurantVicinity='" + attendingRestaurantVicinity + '\'' +
            ", attendingRestaurantPictureUrl='" + attendingRestaurantPictureUrl + '\'' +
            '}';
    }
}

