package com.emplk.go4lunch.ui.restaurant_detail;

import androidx.annotation.NonNull;

public enum AttendanceState {

    IS_ATTENDING("Go"),
    IS_NOT_ATTENDING("Go?");

    @NonNull
    private final String text;

    AttendanceState(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getText() {
        return text;
    }

    @NonNull
    @Override
    public String toString() {
        return "AttendanceState{" +
            "text='" + text + '\'' +
            '}';
    }
}
