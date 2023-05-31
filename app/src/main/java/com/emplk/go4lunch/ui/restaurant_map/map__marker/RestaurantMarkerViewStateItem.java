package com.emplk.go4lunch.ui.restaurant_map.map__marker;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class RestaurantMarkerViewStateItem {
    @NonNull
    private final String id;

    @NonNull
    private final String name; // will need it for autocomplete

    @NonNull
    private final LatLng latLng;

    @NonNull
    private final MarkerState markerState;


    public RestaurantMarkerViewStateItem(
        @NonNull String id,
        @NonNull String name,
        @NonNull LatLng latLng,
        @NonNull MarkerState markerState
    ) {
        this.id = id;
        this.name = name;
        this.latLng = latLng;
        this.markerState = markerState;
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
    public LatLng getLatLng() {
        return latLng;
    }


    @NonNull
    public MarkerState getMarkerState() {
        return markerState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantMarkerViewStateItem that = (RestaurantMarkerViewStateItem) o;
        return id.equals(that.id) && name.equals(that.name) && latLng.equals(that.latLng) && markerState == that.markerState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latLng, markerState);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantMarkerViewStateItem{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", latLng=" + latLng +
            ", markerState=" + markerState +
            '}';
    }
}
