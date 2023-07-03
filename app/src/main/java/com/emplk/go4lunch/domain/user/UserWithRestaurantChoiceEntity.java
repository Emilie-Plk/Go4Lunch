package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class UserWithRestaurantChoiceEntity {

    @NonNull
    private final String id;

    @NonNull
    @ServerTimestamp
    private final Timestamp timestamp;

    @NonNull
    private final String attendingUsername;

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
        @NonNull String attendingUsername,
        @NonNull String attendingRestaurantId,
        @NonNull String attendingRestaurantName,
        @NonNull String attendingRestaurantVicinity,
        @NonNull String attendingRestaurantPictureUrl
    ) {
        this.id = id;
        this.timestamp = timestamp;
        this.attendingUsername = attendingUsername;
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
    public String getAttendingUsername() {
        return attendingUsername;
    }

    @NonNull
    @ServerTimestamp
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
        return id.equals(that.id) && timestamp.equals(that.timestamp) && attendingUsername.equals(that.attendingUsername) && attendingRestaurantId.equals(that.attendingRestaurantId) && attendingRestaurantName.equals(that.attendingRestaurantName) && attendingRestaurantVicinity.equals(that.attendingRestaurantVicinity) && attendingRestaurantPictureUrl.equals(that.attendingRestaurantPictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, timestamp, attendingUsername, attendingRestaurantId, attendingRestaurantName, attendingRestaurantVicinity, attendingRestaurantPictureUrl);
    }

    @Override
    public String toString() {
        return "UserWithRestaurantChoiceEntity{" +
            "id='" + id + '\'' +
            ", timestamp=" + timestamp +
            ", attendingUsername='" + attendingUsername + '\'' +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            ", attendingRestaurantVicinity='" + attendingRestaurantVicinity + '\'' +
            ", attendingRestaurantPictureUrl='" + attendingRestaurantPictureUrl + '\'' +
            '}';
    }
}
