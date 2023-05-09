package com.emplk.go4lunch.data.details.details_restaurant_response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OpeningHours {

    @SerializedName("open_now")
    private final Boolean openNow;

    @SerializedName("periods")
    @Expose
    private final List<PeriodsItem> periods;

    @SerializedName("weekday_text")
    private final List<String> weekdayText;

    public OpeningHours(Boolean openNow, List<PeriodsItem> periods, List<String> weekdayText) {
        this.openNow = openNow;
        this.periods = periods;
        this.weekdayText = weekdayText;
    }

    public final boolean isOpenNow() {
        return openNow;
    }

    public final List<PeriodsItem> getPeriods() {
        return periods;
    }

    public final List<String> getWeekdayText() {
        return weekdayText;
    }

    @NonNull
    @Override
    public String toString() {
        return
            "OpeningHours{" +
                "open_now = '" + openNow + '\'' +
                ",periods = '" + periods + '\'' +
                ",weekday_text = '" + weekdayText + '\'' +
                "}";
    }
}