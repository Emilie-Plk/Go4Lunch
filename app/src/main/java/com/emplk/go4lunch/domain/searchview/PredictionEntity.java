package com.emplk.go4lunch.domain.searchview;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;

public class PredictionEntity {

    @NonNull
    private final String placeId;

    @NonNull
    private final String restaurantName;

    public PredictionEntity(
        @NonNull String placeId,
        @NonNull String restaurantName
    ) {
        this.placeId = placeId;
        this.restaurantName = restaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PredictionEntity that = (PredictionEntity) o;
        return placeId.equals(that.placeId) && restaurantName.equals(that.restaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, restaurantName);
    }

    @NonNull
    @Override
    public String toString() {
        return "PredictionEntity{" +
            "placeId='" + placeId + '\'' +
            ", restaurantName='" + restaurantName + '\'' +
            '}';
    }
}
