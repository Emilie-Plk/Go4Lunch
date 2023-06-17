package com.emplk.go4lunch.ui.restaurant_map.map__marker;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.ui.utils.RestaurantFavoriteState;
import com.google.android.gms.maps.model.LatLng;

import java.util.Objects;

public class RestaurantMarkerViewStateItem {
    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @NonNull
    private final LatLng latLng;

    @NonNull
    private final RestaurantFavoriteState restaurantFavoriteState;


    public RestaurantMarkerViewStateItem(
        @NonNull String id,
        @NonNull String name,
        @NonNull LatLng latLng,
        @NonNull RestaurantFavoriteState restaurantFavoriteState
    ) {
        this.id = id;
        this.name = name;
        this.latLng = latLng;
        this.restaurantFavoriteState = restaurantFavoriteState;
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
    public RestaurantFavoriteState getMarkerState() {
        return restaurantFavoriteState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantMarkerViewStateItem that = (RestaurantMarkerViewStateItem) o;
        return id.equals(that.id) && name.equals(that.name) && latLng.equals(that.latLng) && restaurantFavoriteState == that.restaurantFavoriteState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, latLng, restaurantFavoriteState);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantMarkerViewStateItem{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", latLng=" + latLng +
            ", restaurantFavoriteState=" + restaurantFavoriteState +
            '}';
    }
}
