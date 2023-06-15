package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class ChosenRestaurantEntity {

    @NonNull
    private final String attendingRestaurantId;

    @NonNull
    private final String attendingRestaurantName;

    @NonNull
    private final String attendingRestaurantVicinity;

    @Nullable
    private final String attendingRestaurantPictureUrl;

    public ChosenRestaurantEntity(
        @NonNull String attendingRestaurantId,
        @NonNull String attendingRestaurantName,
        @NonNull String attendingRestaurantVicinity,
        @Nullable String attendingRestaurantPictureUrl
    ) {
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
        this.attendingRestaurantVicinity = attendingRestaurantVicinity;
        this.attendingRestaurantPictureUrl = attendingRestaurantPictureUrl;
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

    @Nullable
    public String getAttendingRestaurantPictureUrl() {
        return attendingRestaurantPictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChosenRestaurantEntity that = (ChosenRestaurantEntity) o;
        return attendingRestaurantId.equals(that.attendingRestaurantId) && attendingRestaurantName.equals(that.attendingRestaurantName) && attendingRestaurantVicinity.equals(that.attendingRestaurantVicinity) && Objects.equals(attendingRestaurantPictureUrl, that.attendingRestaurantPictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendingRestaurantId, attendingRestaurantName, attendingRestaurantVicinity, attendingRestaurantPictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChosenRestaurantEntity{" +
            "attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            ", attendingRestaurantVicinity='" + attendingRestaurantVicinity + '\'' +
            ", attendingRestaurantPictureUrl='" + attendingRestaurantPictureUrl + '\'' +
            '}';
    }
}
