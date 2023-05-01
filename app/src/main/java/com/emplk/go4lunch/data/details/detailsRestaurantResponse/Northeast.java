package com.emplk.go4lunch.data.details.detailsRestaurantResponse;

import com.google.gson.annotations.SerializedName;

public class Northeast {

    @SerializedName("lng")
    private Object lng;

    @SerializedName("lat")
    private Object lat;

    public Object getLng() {
        return lng;
    }

    public Object getLat() {
        return lat;
    }

    @Override
    public String toString() {
        return
            "Northeast{" +
                "lng = '" + lng + '\'' +
                ",lat = '" + lat + '\'' +
                "}";
    }
}