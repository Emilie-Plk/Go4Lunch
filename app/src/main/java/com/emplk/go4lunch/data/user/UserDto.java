package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;
import java.util.Objects;

public class UserDto {
    @Nullable
    private final String id;

    @Nullable
    private final String name;

    @Nullable
    private final String email;

    @Nullable
    private final String pictureUrl;

    @Nullable
    private final Map<String, Object> favoriteRestaurantIds;

    public UserDto() {
        id = null;
        name = null;
        email = null;
        pictureUrl = null;
        favoriteRestaurantIds = null;
    }

    public UserDto(
        @Nullable String id,
        @Nullable String name,
        @Nullable String email,
        @Nullable String pictureUrl,
        @Nullable Map<String, Object> favoriteRestaurantIds
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
        this.favoriteRestaurantIds = favoriteRestaurantIds;
    }

    @Nullable
    public String getId() {
        return id;
    }

    @Nullable
    public String getName() {
        return name;
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
    public Map<String, Object> getFavoriteRestaurantIds() {
        return favoriteRestaurantIds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(pictureUrl, userDto.pictureUrl) && Objects.equals(favoriteRestaurantIds, userDto.favoriteRestaurantIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, pictureUrl, favoriteRestaurantIds);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserDto{" +
            "id='" + id + '\'' +
            ", username='" + name + '\'' +
            ", email='" + email + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", favoriteRestaurantIdMap=" + favoriteRestaurantIds +
            '}';
    }
}
