package com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Location {

    @SerializedName("lng")
    @NonNull
    private final Double lng;

    @SerializedName("lat")
    @NonNull
    private final Double lat;

    public Location(@NonNull Double lng, @NonNull Double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    @NonNull
    public Double getLng() {
        return lng;
    }

    @NonNull
    public Double getLat() {
        return lat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(lng, location.lng) && Objects.equals(lat, location.lat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lng, lat);
    }

    @NonNull
    @Override
    public String toString() {
        return "Location{" +
            "lng=" + lng +
            ", lat=" + lat +
            '}';
    }
}