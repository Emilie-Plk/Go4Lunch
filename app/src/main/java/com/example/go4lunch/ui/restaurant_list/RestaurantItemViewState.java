package com.example.go4lunch.ui.restaurant_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class RestaurantItemViewState {

    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @NonNull
    private final String cuisine;

    @NonNull
    private final String address;

    @NonNull
    private final String distance;

    @NonNull
    private final String attendants;

    @NonNull
    private final String openingHours;

    @Nullable
    private final String pictureUrl;

    private final Float rating;

    public RestaurantItemViewState(
        @NonNull String id,
        @NonNull String name,
        @NonNull String cuisine,
        @NonNull String address,
        @NonNull String distance,
        @NonNull String attendants,
        @NonNull String openingHours,
        @Nullable String pictureUrl,
        Float rating
    ) {
        this.id = id;
        this.name = name;
        this.cuisine = cuisine;
        this.address = address;
        this.distance = distance;
        this.attendants = attendants;
        this.openingHours = openingHours;
        this.pictureUrl = pictureUrl;
        this.rating = rating;
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
    public String getCuisine() {
        return cuisine;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    @NonNull
    public String getDistance() {
        return distance;
    }

    @NonNull
    public String getAttendants() {
        return attendants;
    }

    @NonNull
    public String getOpeningHours() {
        return openingHours;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
    }

    public Float getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantItemViewState that = (RestaurantItemViewState) o;
        return id.equals(that.id) && name.equals(that.name) && cuisine.equals(that.cuisine) && address.equals(that.address) && distance.equals(that.distance) && attendants.equals(that.attendants) && openingHours.equals(that.openingHours) && Objects.equals(pictureUrl, that.pictureUrl) && Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, cuisine, address, distance, attendants, openingHours, pictureUrl, rating);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantItemViewState{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", cuisine='" + cuisine + '\'' +
            ", address='" + address + '\'' +
            ", distance='" + distance + '\'' +
            ", attendants='" + attendants + '\'' +
            ", openingHours='" + openingHours + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            ", rating=" + rating +
            '}';
    }
}
