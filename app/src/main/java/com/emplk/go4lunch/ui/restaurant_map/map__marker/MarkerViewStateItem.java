package com.emplk.go4lunch.ui.restaurant_map.map__marker;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class MarkerViewStateItem {
    @NonNull
    private final String id;

    @NonNull
    private final String name; // will need it for autocomplete

    @NonNull
    private final LatLng latLng;

    @NonNull
    @ColorRes
    private final int color;

    @Nullable
    private final Boolean isSelected;

    public MarkerViewStateItem(
        @NonNull String id,
        @NonNull String name,
        @NonNull LatLng latLng,
        int color,
        @Nullable Boolean isSelected
    ) {
        this.id = id;
        this.name = name;
        this.latLng = latLng;
        this.color = color;
        this.isSelected = isSelected;
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

    public int getColor() {
        return color;
    }

    @Nullable
    public Boolean getSelected() {
        return isSelected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MarkerViewStateItem that = (MarkerViewStateItem) o;
        return color == that.color && id.equals(that.id) && name.equals(that.name) && latLng.equals(that.latLng) && Objects.equals(isSelected, that.isSelected);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latLng, color, isSelected);
    }

    @NonNull
    @Override
    public String toString() {
        return "MarkerViewStateItem{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", latLng=" + latLng +
            ", color=" + color +
            ", isSelected=" + isSelected +
            '}';
    }
}
