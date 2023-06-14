package com.emplk.go4lunch.ui.utils;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.emplk.go4lunch.R;

public enum RestaurantFavoriteState {

    IS_FAVORITE(R.drawable.baseline_star_24),
    IS_NOT_FAVORITE(R.drawable.baseline_star_border_24);

    @DrawableRes
    private final int drawableRes;

    RestaurantFavoriteState(
        @DrawableRes int drawableRes
    ) {
        this.drawableRes = drawableRes;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantFavoriteState{" +
            "drawableRes=" + drawableRes +
            '}';
    }
}
