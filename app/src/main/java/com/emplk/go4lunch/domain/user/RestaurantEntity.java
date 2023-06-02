package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class RestaurantEntity {

    @NonNull
    private final String placeId;

    @NonNull
    private final String name;

    @NonNull
    private final String vicinity;

    @Nullable
    private final String photoUrl;

    public RestaurantEntity(
        @NonNull String placeId,
        @NonNull String name,
        @NonNull String vicinity,
        @Nullable String photoUrl
    ) {
        this.placeId = placeId;
        this.name = name;
        this.vicinity = vicinity;
        this.photoUrl = photoUrl;
    }

    @NonNull
    public String getPlaceId() {
        return placeId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getVicinity() {
        return vicinity;
    }

    @Nullable
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantEntity that = (RestaurantEntity) o;
        return placeId.equals(that.placeId) && name.equals(that.name) && vicinity.equals(that.vicinity) && Objects.equals(photoUrl, that.photoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, name, vicinity, photoUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantEntity{" +
            "placeId='" + placeId + '\'' +
            ", name='" + name + '\'' +
            ", vicinity='" + vicinity + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            '}';
    }
}
