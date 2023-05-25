package com.emplk.go4lunch.ui.restaurant_list;

import androidx.annotation.NonNull;

public enum RestaurantOpeningColor {

    IS_OPEN("#3D9C41"),
    IS_CLOSED("#e63946");

    @NonNull
    private final String colorInt;


    RestaurantOpeningColor(@NonNull String colorInt) {
        this.colorInt = colorInt;
    }

    @NonNull
    public String getColorInt() {
        return colorInt;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantOpeningColor{" +
            "colorInt=" + colorInt +
            '}';
    }
}
