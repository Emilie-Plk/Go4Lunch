package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

<<<<<<< HEAD
=======
import java.util.List;
import java.util.Map;
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
import java.util.Objects;
import java.util.Set;

public class UserEntity {

    @NonNull
    private final LoggedUserEntity loggedUserEntity;

    @NonNull
<<<<<<< HEAD
    private final Set<String> favoriteRestaurantSet;
=======
    private final List<String> favoriteRestaurantList;
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283

    @Nullable
    private final String attendingRestaurantId;

    public UserEntity(
        @NonNull LoggedUserEntity loggedUserEntity,
<<<<<<< HEAD
        @NonNull Set<String> favoriteRestaurantSet,
        @Nullable String attendingRestaurantId
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.favoriteRestaurantSet = favoriteRestaurantSet;
=======
        @NonNull List<String> favoriteRestaurantList,
        @Nullable String attendingRestaurantId
    ) {
        this.loggedUserEntity = loggedUserEntity;
        this.favoriteRestaurantList = favoriteRestaurantList;
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
        this.attendingRestaurantId = attendingRestaurantId;
    }

    @NonNull
    public LoggedUserEntity getLoggedUserEntity() {
        return loggedUserEntity;
    }

    @NonNull
<<<<<<< HEAD
    public Set<String> getFavoriteRestaurantSet() {
        return favoriteRestaurantSet;
    }

    @Nullable
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
=======
    public List<String> getFavoriteRestaurantList() {
        return favoriteRestaurantList;
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
    }

    @Nullable
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
<<<<<<< HEAD
        UserEntity user = (UserEntity) o;
        return loggedUserEntity.equals(user.loggedUserEntity) && favoriteRestaurantSet.equals(user.favoriteRestaurantSet) && Objects.equals(attendingRestaurantId, user.attendingRestaurantId);
=======
        UserEntity that = (UserEntity) o;
        return loggedUserEntity.equals(that.loggedUserEntity) && favoriteRestaurantList.equals(that.favoriteRestaurantList) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId);
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
    }

    @Override
    public int hashCode() {
<<<<<<< HEAD
        return Objects.hash(loggedUserEntity, favoriteRestaurantSet, attendingRestaurantId);
=======
        return Objects.hash(loggedUserEntity, favoriteRestaurantList, attendingRestaurantId);
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
    }

    @NonNull
    @Override
    public String toString() {
        return "UserEntity{" +
            "loggedUserEntity=" + loggedUserEntity +
<<<<<<< HEAD
            ", favoriteRestaurantSet=" + favoriteRestaurantSet +
=======
            ", favoriteRestaurantList=" + favoriteRestaurantList +
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            '}';
    }
}
