package com.emplk.go4lunch.ui.restaurant_map.map__marker;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class UserMarkerViewStateItem {

    @NonNull
    private final LatLng latLng;

    @StringRes
    private final int title;

    public UserMarkerViewStateItem(
        @NonNull LatLng latLng,
        int title
    ) {
        this.latLng = latLng;
        this.title = title;
    }

    @NonNull
    public LatLng getLatLng() {
        return latLng;
    }

    public int getTitle() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserMarkerViewStateItem that = (UserMarkerViewStateItem) o;
        return title == that.title && latLng.equals(that.latLng);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latLng, title);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserMarkerViewStateItem{" +
            "latLng=" + latLng +
            ", title=" + title +
            '}';
    }
}
