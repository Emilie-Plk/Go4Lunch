package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.domain.user.RestaurantEntity;

import java.util.List;
import java.util.Objects;

public class UserDto {
    @Nullable
    private final String userId;

    @Nullable
    private final String username;

    @Nullable
    private final String email;

    @Nullable
    private final String pictureUrl;

    @Nullable
    private final List<RestaurantEntity> favorite_restaurant_list;

    public UserDto() {
        this(null, null, null, null, null);
    }

    public UserDto(
        @Nullable String userId,
        @Nullable String username,
        @Nullable String email,
        @Nullable String pictureUrl,
        @Nullable List<RestaurantEntity> favorite_restaurant_list
    ) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.favorite_restaurant_list = favorite_restaurant_list;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }

    @Nullable
    public String getUsername() {
        return username;
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
    public List<RestaurantEntity> getFavorite_restaurant_list() {
        return favorite_restaurant_list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(userId, userDto.userId) && Objects.equals(username, userDto.username) && Objects.equals(email, userDto.email) && Objects.equals(pictureUrl, userDto.pictureUrl) && Objects.equals(favorite_restaurant_list, userDto.favorite_restaurant_list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, username, email, pictureUrl, favorite_restaurant_list);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserDto{" +
            "userId='" + userId + '\'' +
            ", username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", favoriteRestaurantList=" + favorite_restaurant_list +
            '}';
    }
}
