package com.emplk.go4lunch.data.nearbySearchRestaurants;

import java.util.Objects;

public class LocationKey {
    private final Double latitude;
    private final Double longitude;
    private final String rankBy;

    public LocationKey(Double latitude, Double longitude, String rankBy) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.rankBy = rankBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LocationKey that = (LocationKey) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude) && Objects.equals(rankBy, that.rankBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, rankBy);
    }
}
