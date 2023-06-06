package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import java.util.Map;
import java.util.Objects;

public class UserEntity {

    @NonNull
    private final LoggedUserEntity loggedUserEntity;

    @NonNull
    private final Map<String, Object> favoriteRestaurantList;

    public UserEntity(
        @NonNull LoggedUserEntity loggedUserEntity,
        @NonNull Map<String, Object> favoriteRestaurantList
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.favoriteRestaurantList = favoriteRestaurantList;
    }

    @NonNull
    public LoggedUserEntity getLoggedUserEntity() {
        return loggedUserEntity;
    }

    @NonNull
    public Map<String, Object> getFavoriteRestaurantIds() {
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
