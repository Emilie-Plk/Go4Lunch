package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class UserWithRestaurantChoiceDto {

    @Nullable
    private final String id;

    @Nullable
    private final String name;

    @Nullable
    private final String pictureUrl;

    @Nullable
    private final String restaurantId;

    @Nullable
    private final String restaurantName;

    @Nullable
    private final String vicinity;

    @Nullable
    private final String restaurantUrl;

    public UserWithRestaurantChoiceDto() {
        id = null;
        name = null;
        pictureUrl = null;
        restaurantId = null;
        restaurantName = null;
        vicinity = null;
        restaurantUrl = null;
    }

    public UserWithRestaurantChoiceDto(
        @Nullable String id,
        @Nullable String name,
        @Nullable String pictureUrl,
        @Nullable String restaurantId,
        @Nullable String restaurantName,
        @Nullable String vicinity,
        @Nullable String restaurantUrl
    ) {
        this.id = id;
        this.name = name;
        this.pictureUrl = pictureUrl;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.vicinity = vicinity;
        this.restaurantUrl = restaurantUrl;
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
    public String getPictureUrl() {
        return pictureUrl;
    }

    @Nullable
    public String getRestaurantId() {
        return restaurantId;
    }

    @Nullable
    public String getRestaurantName() {
        return restaurantName;
    }

    @Nullable
    public String getVicinity() {
        return vicinity;
    }

    @Nullable
    public String getRestaurantUrl() {
        return restaurantUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserWithRestaurantChoiceDto that = (UserWithRestaurantChoiceDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(pictureUrl, that.pictureUrl) && Objects.equals(restaurantId, that.restaurantId) && Objects.equals(restaurantName, that.restaurantName) && Objects.equals(vicinity, that.vicinity) && Objects.equals(restaurantUrl, that.restaurantUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, pictureUrl, restaurantId, restaurantName, vicinity, restaurantUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserWithRestaurantChoiceDto{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", restaurantId='" + restaurantId + '\'' +
            ", restaurantName='" + restaurantName + '\'' +
            ", vicinity='" + vicinity + '\'' +
            ", restaurantUrl='" + restaurantUrl + '\'' +
            '}';
    }
}

