package com.emplk.go4lunch.data.details.details_restaurant_response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class PeriodsItem {

    @SerializedName("close")
    private final Close close;

    @SerializedName("open")
    private final Open open;

    public PeriodsItem(
        Close close,
        Open open
    ) {
        this.close = close;
        this.open = open;
    }

    public Close getClose() {
        return close;
    }

    public Open getOpen() {
        return open;
    }

    @NonNull
    @Override
    public String toString() {
        return
            "PeriodsItem{" +
                "close = '" + close + '\'' +
                ",open = '" + open + '\'' +
                "}";
    }
}