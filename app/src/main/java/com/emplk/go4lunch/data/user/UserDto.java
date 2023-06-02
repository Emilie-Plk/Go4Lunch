package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.Objects;

public class UserDto {
    @Nullable
    private String userId;

    @Nullable
    private String displayName;
    @Nullable
    private String email;

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

    @Nullable
    private List<String> favoriteRestaurantList;

    public UserDto() {
    }

    public UserDto(
        @Nullable String userId,
        @Nullable String displayName,
        @Nullable String email,
        @Nullable String pictureUrl,
        @Nullable Boolean hasChosenRestaurant,
        @Nullable String chosenRestaurantId,
        @Nullable String chosenRestaurantName,
        @Nullable String chosenRestaurantVicinity,
        @Nullable List<String> favoriteRestaurantList
    ) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.hasChosenRestaurant = hasChosenRestaurant;
        this.chosenRestaurantId = chosenRestaurantId;
        this.chosenRestaurantName = chosenRestaurantName;
        this.chosenRestaurantVicinity = chosenRestaurantVicinity;
        this.favoriteRestaurantList = favoriteRestaurantList;
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
    public String getEmail() {
        return email;
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
    public List<String> getFavoriteRestaurantList() {
        return favoriteRestaurantList;
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
        UserDto userDto = (UserDto) o;
        return Objects.equals(userId, userDto.userId) && Objects.equals(displayName, userDto.displayName) && Objects.equals(email, userDto.email) && Objects.equals(pictureUrl, userDto.pictureUrl) && Objects.equals(hasChosenRestaurant, userDto.hasChosenRestaurant) && Objects.equals(chosenRestaurantId, userDto.chosenRestaurantId) && Objects.equals(chosenRestaurantName, userDto.chosenRestaurantName) && Objects.equals(chosenRestaurantVicinity, userDto.chosenRestaurantVicinity) && Objects.equals(favoriteRestaurantList, userDto.favoriteRestaurantList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, displayName, email, pictureUrl, hasChosenRestaurant, chosenRestaurantId, chosenRestaurantName, chosenRestaurantVicinity, favoriteRestaurantList);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserDto{" +
            "userId='" + userId + '\'' +
            ", displayName='" + displayName + '\'' +
            ", email='" + email + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", hasChosenRestaurant=" + hasChosenRestaurant +
            ", chosenRestaurantId='" + chosenRestaurantId + '\'' +
            ", chosenRestaurantName='" + chosenRestaurantName + '\'' +
            ", chosenRestaurantVicinity='" + chosenRestaurantVicinity + '\'' +
            ", favoriteRestaurantList=" + favoriteRestaurantList +
            '}';
    }
}
