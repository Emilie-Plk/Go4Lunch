package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import java.util.List;
import java.util.Objects;

public class UserEntity {

    @NonNull
    private final LoggedUserEntity loggedUserEntity;

    @Nullable
    private final List<RestaurantEntity> favoriteRestaurantList;

    public UserEntity(
        @NonNull LoggedUserEntity loggedUserEntity,
        @Nullable List<RestaurantEntity> favoriteRestaurantList
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.favoriteRestaurantList = favoriteRestaurantList;
    }

    @NonNull
    public LoggedUserEntity getLoggedUserEntity() {
        return loggedUserEntity;
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
        return loggedUserEntity.equals(that.loggedUserEntity) && Objects.equals(favoriteRestaurantList, that.favoriteRestaurantList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUserEntity, favoriteRestaurantList);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserEntity{" +
            "loggedUserEntity=" + loggedUserEntity +
            ", favoriteRestaurantList=" + favoriteRestaurantList +
            '}';
    }
}
