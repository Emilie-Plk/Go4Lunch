package com.emplk.go4lunch.ui.restaurant_list;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.emplk.go4lunch.R;

public enum RestaurantOpeningState {

    IS_OPEN(R.string.list_restaurant_opening_text_open, 0 /*"#3D9C41"*/), // TODO
    IS_CLOSED(R.string.list_restaurant_opening_text_closed, 0 /*"#e63946"*/),
    IS_NOT_DEFINED(R.string.list_restaurant_opening_text_undefined, 0 /*""*/);

    @StringRes
    private final int text;

    @ColorRes
    private final int colorRes;

    RestaurantOpeningState(@StringRes int text, @ColorRes int colorRes) {
        this.text = text;
        this.colorRes = colorRes;
    }

    @StringRes
    public int getText() {
        return text;
    }

    @ColorRes
    public int getColorRes() {
        return colorRes;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantOpeningState{" +
            "text=" + text +
            ", colorRes=" + colorRes +
            '}';
    }
}
