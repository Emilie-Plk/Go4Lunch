package com.emplk.go4lunch.domain.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.user.ChosenRestaurantEntity;

import java.util.Objects;

public class WorkmateEntity {

    @NonNull
    private final String userId;

    @NonNull
    private final String displayName;

    @Nullable
    private final String pictureUrl;

    private final boolean hasChosenRestaurant;

    @Nullable
    private final ChosenRestaurantEntity chosenRestaurantEntity;

    public WorkmateEntity(
        @NonNull String userId,
        @NonNull String displayName,
        @Nullable String pictureUrl,
        boolean hasChosenRestaurant,
        @Nullable ChosenRestaurantEntity chosenRestaurantEntity
    ) {
        this.userId = userId;
        this.displayName = displayName;
        this.pictureUrl = pictureUrl;
        this.hasChosenRestaurant = hasChosenRestaurant;
        this.chosenRestaurantEntity = chosenRestaurantEntity;
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

    public boolean isHasChosenRestaurant() {
        return hasChosenRestaurant;
    }

    @Nullable
    public ChosenRestaurantEntity getChosenRestaurantEntity() {
        return chosenRestaurantEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmateEntity that = (WorkmateEntity) o;
        return hasChosenRestaurant == that.hasChosenRestaurant && userId.equals(that.userId) && displayName.equals(that.displayName) && Objects.equals(pictureUrl, that.pictureUrl) && Objects.equals(chosenRestaurantEntity, that.chosenRestaurantEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, displayName, pictureUrl, hasChosenRestaurant, chosenRestaurantEntity);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmateEntity{" +
            "userId='" + userId + '\'' +
            ", displayName='" + displayName + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", hasChosenRestaurant=" + hasChosenRestaurant +
            ", chosenRestaurantEntity=" + chosenRestaurantEntity +
            '}';
    }
}
