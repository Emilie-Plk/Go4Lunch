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
    private final MarkerStatusState markerStatusState;


    public RestaurantMarkerViewStateItem(
        @NonNull String id,
        @NonNull String name,
        @NonNull LatLng latLng,
        @NonNull MarkerStatusState markerStatusState
    ) {
        this.id = id;
        this.name = name;
        this.latLng = latLng;
        this.markerStatusState = markerStatusState;
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
    public MarkerStatusState getMarkerState() {
        return markerStatusState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantMarkerViewStateItem that = (RestaurantMarkerViewStateItem) o;
        return id.equals(that.id) && name.equals(that.name) && latLng.equals(that.latLng) && markerStatusState == that.markerStatusState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latLng, markerStatusState);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantMarkerViewStateItem{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", latLng=" + latLng +
            ", markerStatusState=" + markerStatusState +
            '}';
    }
}
