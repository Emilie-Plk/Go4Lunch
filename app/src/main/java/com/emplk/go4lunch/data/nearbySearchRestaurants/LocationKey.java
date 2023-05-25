package com.emplk.go4lunch.data.nearbySearchRestaurants;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.gps.LocationEntity;

import java.util.Objects;

public class LocationKey {

    @NonNull
    private final LocationEntity locationEntity;
    @NonNull
    private final String rankBy;

    public LocationKey(
        @NonNull LocationEntity locationEntity,
        @NonNull String rankBy
    ) {
        this.locationEntity = locationEntity;
        this.rankBy = rankBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationKey that = (LocationKey) o;
        return locationEntity.equals(that.locationEntity) && rankBy.equals(that.rankBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationEntity, rankBy);
    }

    @NonNull
    @Override
    public String toString() {
        return "LocationKey{" +
            "locationEntity=" + locationEntity +
            ", rankBy='" + rankBy + '\'' +
            '}';
    }
}
