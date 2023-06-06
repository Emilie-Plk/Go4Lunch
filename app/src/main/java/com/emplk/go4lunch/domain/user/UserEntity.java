package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UserEntity {

    @NonNull
    private final LoggedUserEntity loggedUserEntity;

    @NonNull
    private final List<String> favoriteRestaurantList;

    @Nullable
    private final String attendingRestaurantId;

    public UserEntity(
        @NonNull LoggedUserEntity loggedUserEntity,
        @NonNull List<String> favoriteRestaurantList,
        @Nullable String attendingRestaurantId
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.favoriteRestaurantList = favoriteRestaurantList;
        this.attendingRestaurantId = attendingRestaurantId;
    }

    @NonNull
    public LoggedUserEntity getLoggedUserEntity() {
        return loggedUserEntity;
    }

    @NonNull
    public List<String> getFavoriteRestaurantList() {
        return favoriteRestaurantList;
    }

    @Nullable
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return loggedUserEntity.equals(that.loggedUserEntity) && favoriteRestaurantList.equals(that.favoriteRestaurantList) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUserEntity, favoriteRestaurantList, attendingRestaurantId);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserEntity{" +
            "loggedUserEntity=" + loggedUserEntity +
            ", favoriteRestaurantList=" + favoriteRestaurantList +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            '}';
    }
}
