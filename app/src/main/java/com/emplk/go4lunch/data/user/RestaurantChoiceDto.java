package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class RestaurantChoiceDto {

    @Nullable
    private final String attendingRestaurantId;

    @Nullable
    private final String attendingRestaurantName;

    @Nullable
    private final String attendingRestaurantVicinity;

    @Nullable
    private final String attendingRestaurantPictureUrl;

    public RestaurantChoiceDto() {
        attendingRestaurantId = null;
        attendingRestaurantName = null;
        attendingRestaurantVicinity = null;
        attendingRestaurantPictureUrl = null;
    }

    public RestaurantChoiceDto(
        @Nullable String attendingRestaurantId,
        @Nullable String attendingRestaurantName,
        @Nullable String attendingRestaurantVicinity,
        @Nullable String attendingRestaurantPictureUrl
    ) {
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
        this.attendingRestaurantVicinity = attendingRestaurantVicinity;
        this.attendingRestaurantPictureUrl = attendingRestaurantPictureUrl;
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
        RestaurantChoiceDto that = (RestaurantChoiceDto) o;
        return Objects.equals(attendingRestaurantId, that.attendingRestaurantId) && Objects.equals(attendingRestaurantName, that.attendingRestaurantName) && Objects.equals(attendingRestaurantVicinity, that.attendingRestaurantVicinity) && Objects.equals(attendingRestaurantPictureUrl, that.attendingRestaurantPictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attendingRestaurantId, attendingRestaurantName, attendingRestaurantVicinity, attendingRestaurantPictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantChoiceDto{" +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            ", attendingRestaurantVicinity='" + attendingRestaurantVicinity + '\'' +
            ", attendingRestaurantPictureUrl='" + attendingRestaurantPictureUrl + '\'' +
            '}';
    }
}
