package com.emplk.go4lunch.data.nearbySearchRestaurants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class NearbySearchEntity {

    @NonNull
    private final String placeId;
    @NonNull
    private final String restaurantName;
    @NonNull
    private final String vicinity;

    @Nullable
    private final String photoReferenceUrl;

    @Nullable
    private final String cuisine;

    @Nullable
    private final Float rating;

    @NonNull
    private final Float latitude;

    @NonNull
    private final Float longitude;

    @Nullable
    private final Boolean openingHours;


    public NearbySearchEntity(
        @NonNull String placeId,
        @NonNull String restaurantName,
        @NonNull String vicinity,
        @Nullable String photoReferenceUrl,
        @Nullable String cuisine,
        @Nullable Float rating,
        @NonNull Float latitude,
        @NonNull Float longitude,
        @Nullable Boolean openingHours
    ) {
        this.placeId = placeId;
        this.restaurantName = restaurantName;
        this.vicinity = vicinity;
        this.photoReferenceUrl = photoReferenceUrl;
        this.cuisine = cuisine;
        this.rating = rating;
        this.latitude = latitude;
        this.longitude = longitude;
        this.openingHours = openingHours;
    }

    @NonNull
    public String getPlaceId() {
        return placeId;
    }

    @NonNull
    public String getRestaurantName() {
        return restaurantName;
    }

    @NonNull
    public String getVicinity() {
        return vicinity;
    }

    @Nullable
    public String getPhotoReferenceUrl() {
        return photoReferenceUrl;
    }

    @Nullable
    public String getCuisine() {
        return cuisine;
    }

    @Nullable
    public Float getRating() {
        return rating;
    }

    @NonNull
    public Float getLatitude() {
        return latitude;
    }

    @NonNull
    public Float getLongitude() {
        return longitude;
    }

    @Nullable
    public Boolean getOpeningHours() {
        return openingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NearbySearchEntity that = (NearbySearchEntity) o;
        return placeId.equals(that.placeId) && restaurantName.equals(that.restaurantName) && vicinity.equals(that.vicinity) && Objects.equals(photoReferenceUrl, that.photoReferenceUrl) && Objects.equals(cuisine, that.cuisine) && Objects.equals(rating, that.rating) && latitude.equals(that.latitude) && longitude.equals(that.longitude) && Objects.equals(openingHours, that.openingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, restaurantName, vicinity, photoReferenceUrl, cuisine, rating, latitude, longitude, openingHours);
    }

    @NonNull
    @Override
    public String toString() {
        return "NearbySearchEntity{" +
            "placeId='" + placeId + '\'' +
            ", restaurantName='" + restaurantName + '\'' +
            ", vicinity='" + vicinity + '\'' +
            ", photoReferenceUrl='" + photoReferenceUrl + '\'' +
            ", cuisine='" + cuisine + '\'' +
            ", rating=" + rating +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            ", openingHours=" + openingHours +
            '}';
    }
}

