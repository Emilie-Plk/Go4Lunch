package com.emplk.go4lunch.domain.detail;

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
    private final Float rating;

    @Nullable
    private final String phoneNumber;

    @Nullable
    private final String websiteUrl;

    @Nullable
    private final Boolean isVeganFriendly;

    public DetailsRestaurantEntity(
        @NonNull String placeId,
        @NonNull String restaurantName,
        @NonNull String vicinity,
        @Nullable String photoReferenceUrl,
        @Nullable Float rating,
        @Nullable String phoneNumber,
        @Nullable String websiteUrl,
        @Nullable Boolean isVeganFriendly) {
        this.placeId = placeId;
        this.restaurantName = restaurantName;
        this.vicinity = vicinity;
        this.photoReferenceUrl = photoReferenceUrl;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.websiteUrl = websiteUrl;
        this.isVeganFriendly = isVeganFriendly;
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

    @Nullable
    public Boolean getVeganFriendly() {
        return isVeganFriendly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailsRestaurantEntity that = (DetailsRestaurantEntity) o;
        return placeId.equals(that.placeId) && restaurantName.equals(that.restaurantName) && vicinity.equals(that.vicinity) && Objects.equals(photoReferenceUrl, that.photoReferenceUrl) && Objects.equals(rating, that.rating) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(websiteUrl, that.websiteUrl) && Objects.equals(isVeganFriendly, that.isVeganFriendly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, restaurantName, vicinity, photoReferenceUrl, rating, phoneNumber, websiteUrl, isVeganFriendly);
    }

    @NonNull
    @Override
    public String toString() {
        return "DetailsRestaurantEntity{" +
            "placeId='" + placeId + '\'' +
            ", restaurantName='" + restaurantName + '\'' +
            ", vicinity='" + vicinity + '\'' +
            ", photoReferenceUrl='" + photoReferenceUrl + '\'' +
            ", rating=" + rating +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", websiteUrl='" + websiteUrl + '\'' +
            ", isVeganFriendly=" + isVeganFriendly +
            '}';
    }
}
