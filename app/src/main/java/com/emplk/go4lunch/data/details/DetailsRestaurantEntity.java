package com.emplk.go4lunch.data.details;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class DetailsRestaurantEntity {
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
/*
    @Nullable
    private final String openingHours;*/

    @Nullable
    private final String phoneNumber;

    @Nullable
    private final String websiteUrl;

    public DetailsRestaurantEntity(
        @NonNull String placeId,
        @NonNull String restaurantName,
        @NonNull String vicinity,
        @Nullable String photoReferenceUrl,
        @Nullable String cuisine,
        @Nullable Float rating,
        @Nullable String phoneNumber,
        @Nullable String websiteUrl
    ) {
        this.placeId = placeId;
        this.restaurantName = restaurantName;
        this.vicinity = vicinity;
        this.photoReferenceUrl = photoReferenceUrl;
        this.cuisine = cuisine;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.websiteUrl = websiteUrl;
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

    @Nullable
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @Nullable
    public String getWebsiteUrl() {
        return websiteUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailsRestaurantEntity that = (DetailsRestaurantEntity) o;
        return placeId.equals(that.placeId) && restaurantName.equals(that.restaurantName) && vicinity.equals(that.vicinity) && Objects.equals(photoReferenceUrl, that.photoReferenceUrl) && Objects.equals(cuisine, that.cuisine) && Objects.equals(rating, that.rating) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(websiteUrl, that.websiteUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, restaurantName, vicinity, photoReferenceUrl, cuisine, rating, phoneNumber, websiteUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "DetailsRestaurantEntity{" +
            "placeId='" + placeId + '\'' +
            ", restaurantName='" + restaurantName + '\'' +
            ", vicinity='" + vicinity + '\'' +
            ", photoReferenceUrl='" + photoReferenceUrl + '\'' +
            ", cuisine='" + cuisine + '\'' +
            ", rating=" + rating +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", websiteUrl='" + websiteUrl + '\'' +
            '}';
    }
}
