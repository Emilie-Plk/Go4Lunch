package com.emplk.go4lunch.data.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class WorkmateDto {
    @Nullable
    private String userId;

    @Nullable
    private String displayName;

    @Nullable
    private String pictureUrl;

    @Nullable
    private Boolean hasChosenRestaurant;

    @Nullable
    private String chosenRestaurantId;

    @Nullable
    private String chosenRestaurantName;

    @Nullable
    private String chosenRestaurantVicinity;

    // Empty constructor for Firestore serialization
    public WorkmateDto() {
    }

    public WorkmateDto(
        @Nullable String userId,
        @Nullable String displayName,
        @Nullable String pictureUrl,
        @Nullable Boolean hasChosenRestaurant,
        @Nullable String chosenRestaurantId,
        @Nullable String chosenRestaurantName,
        @Nullable String chosenRestaurantVicinity
    ) {
        this.userId = userId;
        this.displayName = displayName;
        this.pictureUrl = pictureUrl;
        this.hasChosenRestaurant = hasChosenRestaurant;
        this.chosenRestaurantId = chosenRestaurantId;
        this.chosenRestaurantName = chosenRestaurantName;
        this.chosenRestaurantVicinity = chosenRestaurantVicinity;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }

    @Nullable
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
    }

    @Nullable
    public Boolean getHasChosenRestaurant() {
        return hasChosenRestaurant;
    }

    @Nullable
    public String getChosenRestaurantId() {
        return chosenRestaurantId;
    }

    @Nullable
    public String getChosenRestaurantName() {
        return chosenRestaurantName;
    }

    @Nullable
    public String getChosenRestaurantVicinity() {
        return chosenRestaurantVicinity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmateDto that = (WorkmateDto) o;
        return Objects.equals(userId, that.userId) && Objects.equals(displayName, that.displayName) && Objects.equals(pictureUrl, that.pictureUrl) && Objects.equals(hasChosenRestaurant, that.hasChosenRestaurant) && Objects.equals(chosenRestaurantId, that.chosenRestaurantId) && Objects.equals(chosenRestaurantName, that.chosenRestaurantName) && Objects.equals(chosenRestaurantVicinity, that.chosenRestaurantVicinity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, displayName, pictureUrl, hasChosenRestaurant, chosenRestaurantId, chosenRestaurantName, chosenRestaurantVicinity);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmateDto{" +
            "userId='" + userId + '\'' +
            ", displayName='" + displayName + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", hasChosenRestaurant=" + hasChosenRestaurant +
            ", chosenRestaurantId='" + chosenRestaurantId + '\'' +
            ", chosenRestaurantName='" + chosenRestaurantName + '\'' +
            ", chosenRestaurantVicinity='" + chosenRestaurantVicinity + '\'' +
            '}';
    }
}
