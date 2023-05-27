package com.emplk.go4lunch.ui.restaurant_list;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.emplk.go4lunch.R;

public enum RestaurantOpeningState {

    IS_OPEN(R.string.list_restaurant_opening_text_open, "#3D9C41"),
    IS_CLOSED(R.string.list_restaurant_opening_text_closed, "#e63946"),
    IS_NOT_DEFINED(R.string.list_restaurant_opening_text_undefined, "");

    @StringRes
    private final int text;

    @NonNull
    private final String colorString;


    RestaurantOpeningState(int text, @NonNull String colorString) {
        this.colorString = colorString;
        this.text = text;
    }

    @StringRes
    public int getText() {
        return text;
    }

    @NonNull
    public String getColorString() {
        return colorString;
    }


    @NonNull
    @Override
    public String toString() {
        return "RestaurantOpeningState{" +
            "colorString='" + colorString + '\'' +
            ", text='" + text + '\'' +
            '}';
    }
}
