package com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse;

import com.google.gson.annotations.SerializedName;

public class Viewport {

    @SerializedName("southwest")
    private Southwest southwest;

    @SerializedName("northeast")
    private Northeast northeast;

    public Southwest getSouthwest() {
        return southwest;
    }

    public Northeast getNortheast() {
        return northeast;
    }
}