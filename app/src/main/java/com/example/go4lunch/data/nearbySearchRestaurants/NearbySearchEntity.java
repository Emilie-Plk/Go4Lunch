package com.example.go4lunch.data.nearbySearchRestaurants;

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

    @NonNull
    private final String photoReferenceUrl;

    @NonNull
    private final String cuisine;

    @NonNull
    private final Float rating;

    @NonNull
    private final Float latitude;

    @NonNull
    private final Float longitude;

    @NonNull
    private final Boolean openingHours;


    public NearbySearchEntity(
        @NonNull String placeId,
        @NonNull String restaurantName,
        @NonNull String vicinity,
        @NonNull String photoReferenceUrl,
        @NonNull String cuisine,
        @NonNull Float rating,
        @NonNull Float latitude,
        @NonNull Float longitude,
        @NonNull Boolean openingHours
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

    @NonNull
    public String getPhotoReferenceUrl() {
        return photoReferenceUrl;
    }

    @NonNull
    public String getCuisine() {
        return cuisine;
    }

    @NonNull
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

    @NonNull
    public Boolean getOpeningHours() {
        return openingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NearbySearchEntity that = (NearbySearchEntity) o;
        if (!placeId.equals(that.placeId)) return false;
        return restaurantName.equals(that.restaurantName) && vicinity.equals(that.vicinity) && photoReferenceUrl.equals(that.photoReferenceUrl) && cuisine.equals(that.cuisine) && rating.equals(that.rating) && latitude.equals(that.latitude) && longitude.equals(that.longitude) && openingHours.equals(that.openingHours);
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

