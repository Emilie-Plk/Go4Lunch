package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import java.util.List;
import java.util.Objects;

public class UserEntity {

    @NonNull
    private final LoggedUserEntity loggedUserEntity;

    private final boolean hasChosenRestaurant;

    @Nullable
    private final ChosenRestaurantEntity chosenRestaurantId;

    @Nullable
    private final List<RestaurantEntity> favoriteRestaurantList;

    public UserEntity(
        @NonNull LoggedUserEntity loggedUserEntity,
        boolean hasChosenRestaurant,
        @Nullable ChosenRestaurantEntity chosenRestaurantId,
        @Nullable List<RestaurantEntity> favoriteRestaurantList
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.hasChosenRestaurant = hasChosenRestaurant;
        this.chosenRestaurantId = chosenRestaurantId;
        this.favoriteRestaurantList = favoriteRestaurantList;
    }

    @NonNull
    public LoggedUserEntity getLoggedUserEntity() {
        return loggedUserEntity;
    }

    public boolean isHasChosenRestaurant() {
        return hasChosenRestaurant;
    }

    @Nullable
    public ChosenRestaurantEntity getChosenRestaurantId() {
        return chosenRestaurantId;
    }

    @Nullable
    public List<RestaurantEntity> getFavoriteRestaurantList() {
        return favoriteRestaurantList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return hasChosenRestaurant == that.hasChosenRestaurant && loggedUserEntity.equals(that.loggedUserEntity) && Objects.equals(chosenRestaurantId, that.chosenRestaurantId) && Objects.equals(favoriteRestaurantList, that.favoriteRestaurantList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUserEntity, hasChosenRestaurant, chosenRestaurantId, favoriteRestaurantList);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserEntity{" +
            "loggedUserEntity=" + loggedUserEntity +
            ", hasChosenRestaurant=" + hasChosenRestaurant +
            ", chosenRestaurantId=" + chosenRestaurantId +
            ", favoriteRestaurantList=" + favoriteRestaurantList +
            '}';
    }
}
