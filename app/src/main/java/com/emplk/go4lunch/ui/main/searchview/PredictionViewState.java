package com.emplk.go4lunch.ui.main.searchview;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class PredictionViewState {

    @NonNull
    private final String placeId;

    @NonNull
    private final String restaurantName;

    @Nullable
    private final String vicinity; // secondary text

    public PredictionViewState(
        @NonNull String placeId,
        @NonNull String restaurantName,
        @Nullable String vicinity
    ) {
        this.placeId = placeId;
        this.restaurantName = restaurantName;
        this.vicinity = vicinity;
    }

    @NonNull
    public String getPlaceId() {
        return placeId;
    }

    @NonNull
    public String getRestaurantName() {
        return restaurantName;
    }

    @Nullable
    public String getVicinity() {
        return vicinity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PredictionViewState that = (PredictionViewState) o;
        return placeId.equals(that.placeId) && restaurantName.equals(that.restaurantName) && Objects.equals(vicinity, that.vicinity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId, restaurantName, vicinity);
    }

    @NonNull
    @Override
    public String toString() {
        return "PredictionViewState{" +
            "placeId='" + placeId + '\'' +
            ", restaurantName='" + restaurantName + '\'' +
            ", vicinity='" + vicinity + '\'' +
            '}';
    }
}