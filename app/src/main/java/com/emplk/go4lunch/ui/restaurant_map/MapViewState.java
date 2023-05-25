package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class MapViewState {

    private final LatLng latLng;

    public MapViewState(LatLng latLng) {
        this.latLng = latLng;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapViewState that = (MapViewState) o;
        return Objects.equals(latLng, that.latLng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latLng);
    }

    @NonNull
    @Override
    public String toString() {
        return "MapViewState{" +
            "latLng=" + latLng +
            '}';
    }
}
