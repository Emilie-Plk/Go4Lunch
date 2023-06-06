package com.emplk.go4lunch.data.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class WorkmateDto {
    @Nullable
    private final String userId;

    @Nullable
    private final String username;

    @Nullable
    private final String pictureUrl;

    // Empty constructor for Firestore serialization
    public WorkmateDto() {
        userId = null;
        username = null;
        pictureUrl = null;
    }

    public WorkmateDto(
        @Nullable String userId,
        @Nullable String username,
        @Nullable String pictureUrl
    ) {
        this.userId = userId;
        this.username = username;
        this.pictureUrl = pictureUrl;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }

    @Nullable
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmateDto that = (WorkmateDto) o;
        return Objects.equals(userId, that.userId) && Objects.equals(username, that.username) && Objects.equals(pictureUrl, that.pictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, pictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmateDto{" +
            "userId='" + userId + '\'' +
            ", username='" + username + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            '}';
    }
}
