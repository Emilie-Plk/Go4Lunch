package com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse;

import com.google.gson.annotations.SerializedName;

public class OpeningHours {

    @SerializedName("open_now")
    private boolean openNow;

    public Boolean isOpenNow() {
        return openNow;
    }
}