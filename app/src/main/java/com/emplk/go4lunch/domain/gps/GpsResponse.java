package com.emplk.go4lunch.domain.gps;

import android.location.Location;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class GpsResponse {
    public static class Success extends GpsResponse {
        @Nullable
        public final Location location;

        public Success(@Nullable Location location) {
            this.location = location;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Success success = (Success) o;
            return Objects.equals(location, success.location);
        }

        @Override
        public int hashCode() {
            return Objects.hash(location);
        }

        @NonNull
        @Override
        public String toString() {
            return "Success{" +
                "location=" + location +
                '}';
        }
    }

    public static class GpsProviderDisabled extends GpsResponse {
    }
}
