package com.emplk.go4lunch.data.nearbySearchRestaurants;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.gps.LocationEntity;

import java.util.Objects;

public class LocationKey {

    @NonNull
    private final LocationEntity locationEntity;


    public LocationKey(
        @NonNull LocationEntity locationEntity
    ) {
        this.locationEntity = locationEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationKey that = (LocationKey) o;
        return locationEntity.equals(that.locationEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationEntity);
    }

    @NonNull
    @Override
    public String toString() {
        return "LocationKey{" +
            "locationEntity=" + locationEntity +
            '}';
    }
}
