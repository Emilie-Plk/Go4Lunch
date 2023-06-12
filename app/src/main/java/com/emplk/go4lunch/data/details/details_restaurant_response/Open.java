package com.emplk.go4lunch.data.details.details_restaurant_response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Open {

    @SerializedName("time")
    private final String time;

    @SerializedName("day")
    private final Integer day;

    public Open(
        String time,
        Integer day
    ) {
        this.time = time;
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public Integer getDay() {
        return day;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Open open = (Open) o;
        return Objects.equals(time, open.time) && Objects.equals(day, open.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, day);
    }

    @NonNull
    @Override
    public String toString() {
        return
            "Open{" +
                "time = '" + time + '\'' +
                ",day = '" + day + '\'' +
                "}";
    }
}