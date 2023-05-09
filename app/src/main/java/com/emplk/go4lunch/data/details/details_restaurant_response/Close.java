package com.emplk.go4lunch.data.details.details_restaurant_response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Close {

    @SerializedName("time")
    private final String time;

    @SerializedName("day")
    private final Integer day;

    public Close(String time, int day) {
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
        Close close = (Close) o;
        return Objects.equals(time, close.time) && Objects.equals(day, close.day);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, day);
    }

    @NonNull
    @Override
    public String toString() {
        return
            "Close{" +
                "time = '" + time + '\'' +
                ",day = '" + day + '\'' +
                "}";
    }
}