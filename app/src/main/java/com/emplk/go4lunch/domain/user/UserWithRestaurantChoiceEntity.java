package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class UserWithRestaurantChoiceEntity {

    @NonNull
    private final String id;

    @NonNull
    private final Timestamp timestamp;

    @NonNull
    private final String attendingRestaurantId;

    @NonNull
    private final String attendingRestaurantName;

    @NonNull
    private final String attendingRestaurantVicinity;

    @NonNull
    private final String attendingRestaurantPictureUrl;

    public UserWithRestaurantChoiceEntity(
        @NonNull String id,
        @NonNull Timestamp timestamp,
        @NonNull String attendingRestaurantId,
        @NonNull String attendingRestaurantName,
        @NonNull String attendingRestaurantVicinity,
        @NonNull String attendingRestaurantPictureUrl
    ) {
        this.id = id;
        this.timestamp = timestamp;
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
        this.attendingRestaurantVicinity = attendingRestaurantVicinity;
        this.attendingRestaurantPictureUrl = attendingRestaurantPictureUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @NonNull
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
    }

    @NonNull
    public String getAttendingRestaurantName() {
        return attendingRestaurantName;
    }

    @NonNull
    public String getAttendingRestaurantVicinity() {
        return attendingRestaurantVicinity;
    }

    @NonNull
    public String getAttendingRestaurantPictureUrl() {
        return attendingRestaurantPictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWithRestaurantChoiceEntity that = (UserWithRestaurantChoiceEntity) o;
        return id.equals(that.id) && timestamp.equals(that.timestamp) && attendingRestaurantId.equals(that.attendingRestaurantId) && attendingRestaurantName.equals(that.attendingRestaurantName) && attendingRestaurantVicinity.equals(that.attendingRestaurantVicinity) && attendingRestaurantPictureUrl.equals(that.attendingRestaurantPictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, attendingRestaurantId, attendingRestaurantName, attendingRestaurantVicinity, attendingRestaurantPictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserWithRestaurantChoiceEntity{" +
            "id='" + id + '\'' +
            ", timestamp=" + timestamp +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            ", attendingRestaurantVicinity='" + attendingRestaurantVicinity + '\'' +
            ", attendingRestaurantPictureUrl='" + attendingRestaurantPictureUrl + '\'' +
            '}';
    }
}
