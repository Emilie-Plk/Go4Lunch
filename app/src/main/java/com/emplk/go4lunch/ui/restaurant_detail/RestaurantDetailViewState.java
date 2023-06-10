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
    private final String vicinity;

    @Nullable
    private final String pictureUrl;

    @NonNull
    private final Float rating;

    @Nullable
    private final String phoneNumber;

    @Nullable
    private final String websiteUrl;

    @Nullable
    private final Boolean isAttending;
    @Nullable
    private final Boolean isVeganFriendly;

    @NonNull
    private final Boolean isLoading;

    @NonNull
    private final Boolean isPhoneNumberAvailable;

    @NonNull
    private final Boolean isWebsiteAvailable;

    public RestaurantDetailViewState(
        @NonNull String id,
        @NonNull String name,
        @NonNull String vicinity,
        @Nullable String pictureUrl,
        @NonNull Float rating,
        @Nullable String phoneNumber,
        @Nullable String websiteUrl,
        @Nullable Boolean isAttending,
        @Nullable Boolean isVeganFriendly,
        @NonNull Boolean isLoading,
        @NonNull Boolean isPhoneNumberAvailable,
        @NonNull Boolean isWebsiteAvailable
    ) {
        this.id = id;
        this.name = name;
        this.vicinity = vicinity;
        this.pictureUrl = pictureUrl;
        this.rating = rating;
        this.phoneNumber = phoneNumber;
        this.websiteUrl = websiteUrl;
        this.isAttending = isAttending;
        this.isVeganFriendly = isVeganFriendly;
        this.isLoading = isLoading;
        this.isPhoneNumberAvailable = isPhoneNumberAvailable;
        this.isWebsiteAvailable = isWebsiteAvailable;
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
    public String getVicinity() {
        return vicinity;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
    }

    @NonNull
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
    public Boolean isAttending() {
        return isAttending;
    }

    @Nullable
    public Boolean isVeganFriendly() {
        return isVeganFriendly;
    }

    @NonNull
    public Boolean isLoading() {
        return isLoading;
    }

    @NonNull
    public Boolean isPhoneNumberAvailable() {
        return isPhoneNumberAvailable;
    }

    @NonNull
    public Boolean isWebsiteAvailable() {
        return isWebsiteAvailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantDetailViewState that = (RestaurantDetailViewState) o;
        return id.equals(that.id) && name.equals(that.name) && vicinity.equals(that.vicinity) && Objects.equals(pictureUrl, that.pictureUrl) && rating.equals(that.rating) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(websiteUrl, that.websiteUrl) && Objects.equals(isAttending, that.isAttending) && Objects.equals(isVeganFriendly, that.isVeganFriendly) && isLoading.equals(that.isLoading) && isPhoneNumberAvailable.equals(that.isPhoneNumberAvailable) && isWebsiteAvailable.equals(that.isWebsiteAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, vicinity, pictureUrl, rating, phoneNumber, websiteUrl, isAttending, isVeganFriendly, isLoading, isPhoneNumberAvailable, isWebsiteAvailable);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantDetailViewState{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", vicinity='" + vicinity + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", rating=" + rating +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", websiteUrl='" + websiteUrl + '\'' +
            ", isAttending=" + isAttending +
            ", isVeganFriendly=" + isVeganFriendly +
            ", isLoading=" + isLoading +
            ", isPhoneNumberAvailable=" + isPhoneNumberAvailable +
            ", isWebsiteAvailable=" + isWebsiteAvailable +
            '}';
    }
}
