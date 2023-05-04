package com.emplk.go4lunch.ui.restaurant_detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class RestaurantDetailViewState {

    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @NonNull
    private final String address;

    @Nullable
    private final String pictureUrl;

    @Nullable
    private final Float rating;

    @Nullable
    private final String phoneNumber;

    @Nullable
    private final String websiteUrl;

    @Nullable
    private final Boolean isLiked;

    @Nullable
    private final Boolean isAttending;
    @Nullable
    private final Boolean isVeganFriendly;

    public RestaurantDetailViewState(@NonNull String id, @NonNull String name, @NonNull String address, @Nullable String pictureUrl, @Nullable Float rating, @Nullable String phoneNumber, @Nullable String websiteUrl, @Nullable Boolean isLiked, @Nullable Boolean isAttending, @Nullable Boolean isVeganFriendly) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.pictureUrl = pictureUrl;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.websiteUrl = websiteUrl;
        this.isLiked = isLiked;
        this.isAttending = isAttending;
        this.isVeganFriendly = isVeganFriendly;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
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
    public Boolean getLiked() {
        return isLiked;
    }

    @Nullable
    public Boolean getAttending() {
        return isAttending;
    }

    @Nullable
    public Boolean isVeganFriendly() {
        return isVeganFriendly;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDetailViewState that = (RestaurantDetailViewState) o;
        return id.equals(that.id) && name.equals(that.name) && address.equals(that.address) && Objects.equals(pictureUrl, that.pictureUrl) && Objects.equals(rating, that.rating) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(websiteUrl, that.websiteUrl) && Objects.equals(isLiked, that.isLiked) && Objects.equals(isAttending, that.isAttending) && Objects.equals(isVeganFriendly, that.isVeganFriendly);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, pictureUrl, rating, phoneNumber, websiteUrl, isLiked, isAttending, isVeganFriendly);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantDetailViewState{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", rating=" + rating +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", websiteUrl='" + websiteUrl + '\'' +
            ", isLiked=" + isLiked +
            ", isAttending=" + isAttending +
            ", isVeganFriendly=" + isVeganFriendly +
            '}';
    }
}
