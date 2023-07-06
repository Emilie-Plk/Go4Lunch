package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class UserWithRestaurantChoiceDto {

    @Nullable
    @ServerTimestamp
    private final Timestamp timestamp;

    @Nullable
    private final String attendingUsername;

    @Nullable
    private final String attendingRestaurantId;

    @Nullable
    private final String attendingRestaurantName;

    @Nullable
    private final String attendingRestaurantVicinity;

    public UserWithRestaurantChoiceDto() {
        this(null, null, null, null, null);
    }

    public UserWithRestaurantChoiceDto(
        @Nullable Timestamp timestamp,
        @Nullable String attendingUsername,
        @Nullable String attendingRestaurantId,
        @Nullable String attendingRestaurantName,
        @Nullable String attendingRestaurantVicinity
    ) {
        this.timestamp = timestamp;
        this.attendingUsername = attendingUsername;
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
        this.attendingRestaurantVicinity = attendingRestaurantVicinity;
    }

    @Nullable
    @ServerTimestamp
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Nullable
    public String getAttendingUsername() {
        return attendingUsername;
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
        UserWithRestaurantChoiceDto that = (UserWithRestaurantChoiceDto) o;
        return Objects.equals(timestamp, that.timestamp) && Objects.equals(attendingUsername, that.attendingUsername) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId) && Objects.equals(attendingRestaurantName, that.attendingRestaurantName) && Objects.equals(attendingRestaurantVicinity, that.attendingRestaurantVicinity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timestamp, attendingUsername, attendingRestaurantId, attendingRestaurantName, attendingRestaurantVicinity);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserWithRestaurantChoiceDto{" +
            "timestamp=" + timestamp +
            ", attendingUsername='" + attendingUsername + '\'' +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            ", attendingRestaurantVicinity='" + attendingRestaurantVicinity + '\'' +
            '}';
    }
}

