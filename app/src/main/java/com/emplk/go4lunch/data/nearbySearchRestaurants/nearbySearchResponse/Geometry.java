package com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Geometry {

    @SerializedName("location")
    @Nullable
    private final Location location;

    public Geometry(@Nullable Location location) {
        this.location = location;
    }

    @Nullable
    public Location getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geometry geometry = (Geometry) o;
        return Objects.equals(location, geometry.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location);
    }

    @NonNull
    @Override
    public String toString() {
        return "Geometry{" +
            ", location=" + location +
            '}';
    }
}
