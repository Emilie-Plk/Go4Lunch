package com.emplk.go4lunch.ui.restaurant_detail;


import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

import com.emplk.go4lunch.R;

public enum AttendanceState {
    IS_ATTENDING(R.string.detail_restaurant_chosen_fab, R.color.ok_green, R.color.ok_green_pale),
    IS_NOT_ATTENDING(R.string.detail_restaurant_unchosen_fab, R.color.grey_text, R.color.dirty_white);

    @StringRes
    private final int text;

    @ColorRes
    private final int iconColorRes;

    @ColorRes
    private final int backgroundColorRes;

    AttendanceState(
        int text,
        int iconColorRes,
        int backgroundColorRes
    ) {
        this.text = text;
        this.iconColorRes = iconColorRes;
        this.backgroundColorRes = backgroundColorRes;
    }

    @StringRes
    public int getText() {
        return text;
    }

    public int getIconColorRes() {
        return iconColorRes;
    }

    public int getBackgroundColorRes() {
        return backgroundColorRes;
    }

    @NonNull
    @Override
    public String toString() {
        return "AttendanceState{" +
            "text='" + text + '\'' +
            ", iconColorRes=" + iconColorRes +
            ", backgroundColorRes=" + backgroundColorRes +
            '}';
    }
}
