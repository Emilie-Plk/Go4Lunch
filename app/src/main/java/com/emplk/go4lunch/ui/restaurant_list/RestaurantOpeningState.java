package com.emplk.go4lunch.ui.restaurant_list;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.emplk.go4lunch.R;

public enum RestaurantOpeningState {

    IS_OPEN(R.string.list_restaurant_opening_text_open, R.color.ok_green),
    IS_CLOSED(R.string.list_restaurant_opening_text_closed, R.color.error_red),
    IS_NOT_DEFINED(R.string.list_restaurant_opening_text_undefined, R.color.transparent);

    @StringRes
    private final int text;

    @ColorRes
    private final int colorRes;


    RestaurantOpeningState(@StringRes int text, @ColorRes int colorRes) {
        this.text = text;
        this.colorRes = colorRes;

    }

    public int getText() {
        return text;
    }

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
