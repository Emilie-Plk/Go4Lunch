package com.emplk.go4lunch.data.details.details_restaurant_response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class OpeningHours {

    @SerializedName("open_now")
    @Nullable
    private final Boolean openNow;

    @SerializedName("periods")
    @Nullable
    private final List<PeriodsItem> periods;

    @SerializedName("weekday_text")
    @Nullable
    private final List<String> weekdayText;

    public OpeningHours(@Nullable Boolean openNow, @Nullable List<PeriodsItem> periods, @Nullable List<String> weekdayText) {
        this.openNow = openNow;
        this.periods = periods;
        this.weekdayText = weekdayText;
    }

    @Nullable
    public final Boolean isOpenNow() {
        return openNow;
    }

    @Nullable
    public final List<PeriodsItem> getPeriods() {
        return periods;
    }

    @Nullable
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpeningHours that = (OpeningHours) o;
        return Objects.equals(openNow, that.openNow) && Objects.equals(periods, that.periods) && Objects.equals(weekdayText, that.weekdayText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openNow, periods, weekdayText);
    }
}