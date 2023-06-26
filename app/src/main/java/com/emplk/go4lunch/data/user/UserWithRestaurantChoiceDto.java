package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class UserWithRestaurantChoiceDto {

    @Nullable
    private final Timestamp timestamp;

    @Nullable
    private final String attendingRestaurantId;

    @Nullable
    private final String attendingRestaurantName;

    @Nullable
    private final String attendingRestaurantVicinity;

    @Nullable
    private final String attendingRestaurantPictureUrl;

    public UserWithRestaurantChoiceDto() {
        this(null, null, null, null, null);
    }

    public UserWithRestaurantChoiceDto(
        @Nullable Timestamp timestamp,
        @Nullable String attendingRestaurantId,
        @Nullable String attendingRestaurantName,
        @Nullable String attendingRestaurantVicinity,
        @Nullable String attendingRestaurantPictureUrl
    ) {
        this.timestamp = timestamp;
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
        this.attendingRestaurantVicinity = attendingRestaurantVicinity;
        this.attendingRestaurantPictureUrl = attendingRestaurantPictureUrl;
    }

    @Nullable
    public Timestamp getTimestamp() {
        return timestamp;
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
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId) && Objects.equals(attendingRestaurantName, that.attendingRestaurantName) && Objects.equals(attendingRestaurantVicinity, that.attendingRestaurantVicinity) && Objects.equals(attendingRestaurantPictureUrl, that.attendingRestaurantPictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, attendingRestaurantId, attendingRestaurantName, attendingRestaurantVicinity, attendingRestaurantPictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserWithRestaurantChoiceDto{" +
            "timestamp=" + timestamp +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            ", attendingRestaurantVicinity='" + attendingRestaurantVicinity + '\'' +
            ", attendingRestaurantPictureUrl='" + attendingRestaurantPictureUrl + '\'' +
            '}';
    }
}

