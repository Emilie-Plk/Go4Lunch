package com.emplk.go4lunch.data.details.detailsRestaurantResponse;

import com.google.gson.annotations.SerializedName;

public class Open {

    @SerializedName("time")
    private String time;

    @SerializedName("day")
    private int day;

    public String getTime() {
        return time;
    }

    public int getDay() {
        return day;
    }

    @Override
    public String toString() {
        return
            "Open{" +
                "time = '" + time + '\'' +
                ",day = '" + day + '\'' +
                "}";
    }
}