package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import java.util.Objects;
import java.util.Set;

public class UserEntity {

    @NonNull
    private final LoggedUserEntity loggedUserEntity;

    @Nullable
    private final Set<String> favoriteRestaurantSet;

    @Nullable
    private final String attendingRestaurantId;

    public UserEntity(
        @NonNull LoggedUserEntity loggedUserEntity,
        @Nullable Set<String> favoriteRestaurantSet,
        @Nullable String attendingRestaurantId
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.favoriteRestaurantSet = favoriteRestaurantSet;
        this.attendingRestaurantId = attendingRestaurantId;
    }

    @NonNull
    public LoggedUserEntity getLoggedUserEntity() {
        return loggedUserEntity;
    }

    @Nullable
    public Set<String> getFavoriteRestaurantSet() {
        return favoriteRestaurantSet;
    }

    @Nullable
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return loggedUserEntity.equals(user.loggedUserEntity) && Objects.equals(favoriteRestaurantSet, user.favoriteRestaurantSet) && Objects.equals(attendingRestaurantId, user.attendingRestaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(loggedUserEntity, favoriteRestaurantSet, attendingRestaurantId);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserEntity{" +
            "loggedUserEntity=" + loggedUserEntity +
            ", favoriteRestaurantSet=" + favoriteRestaurantSet +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            '}';
    }
}
