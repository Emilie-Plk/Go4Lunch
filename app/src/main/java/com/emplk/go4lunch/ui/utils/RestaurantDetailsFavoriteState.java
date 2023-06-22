package com.emplk.go4lunch.ui.utils;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.emplk.go4lunch.R;

public enum RestaurantDetailsFavoriteState {

    IS_FAVORITE(R.drawable.baseline_favorite_24, R.color.sweet_pink),
    IS_NOT_FAVORITE(R.drawable.baseline_favorite_border_24, R.color.light_gray);

    @DrawableRes
    private final int drawableRes;

    @ColorRes
    private final int iconColorRes;

    RestaurantDetailsFavoriteState(
        int drawableRes,
        int iconColorRes
    ) {
        this.drawableRes = drawableRes;
        this.iconColorRes = iconColorRes;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public int getIconColorRes() {
        return iconColorRes;
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantDetailsFavoriteState{" +
            "drawableRes=" + drawableRes +
            ", iconColorRes=" + iconColorRes +
            '}';
    }
}
