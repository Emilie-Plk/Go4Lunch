package com.emplk.go4lunch.ui.restaurant_map;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.ui.restaurant_map.map__marker.RestaurantMarkerViewStateItem;

import java.util.List;
import java.util.Objects;

public class MapViewState {
/*
    @NonNull
    private final UserMarkerViewStateItem userMarkerViewStateItem;*/

    @NonNull
    private final List<RestaurantMarkerViewStateItem> restaurantMarkerListViewStateItem;

    public MapViewState(
        @NonNull List<RestaurantMarkerViewStateItem> restaurantMarkerListViewStateItem
    ) {
        this.restaurantMarkerListViewStateItem = restaurantMarkerListViewStateItem;
    }

    @NonNull
    public List<RestaurantMarkerViewStateItem> getRestaurantMarkerListViewStateItem() {
        return restaurantMarkerListViewStateItem;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MapViewState that = (MapViewState) o;
        return restaurantMarkerListViewStateItem.equals(that.restaurantMarkerListViewStateItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantMarkerListViewStateItem);
    }

    @NonNull
    @Override
    public String toString() {
        return "MapViewState{" +
            "restaurantMarkerListViewStateItem=" + restaurantMarkerListViewStateItem +
            '}';
    }
}

