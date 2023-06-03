package com.emplk.go4lunch.domain.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class WorkmateEntity {

    @NonNull
    private final String userId;

    @NonNull
    private final String displayName;

    @Nullable
    private final String pictureUrl;


    public WorkmateEntity(
        @NonNull String userId,
        @NonNull String displayName,
        @Nullable String pictureUrl
    ) {
        this.userId = userId;
        this.displayName = displayName;
        this.pictureUrl = pictureUrl;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmateEntity that = (WorkmateEntity) o;
        return userId.equals(that.userId) && displayName.equals(that.displayName) && Objects.equals(pictureUrl, that.pictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, displayName, pictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmateEntity{" +
            "userId='" + userId + '\'' +
            ", displayName='" + displayName + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            '}';
    }
}
