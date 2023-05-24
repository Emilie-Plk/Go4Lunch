package com.emplk.go4lunch.domain.gps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class GpsLocationEntity {

    @Nullable
    private final Double latitude;
    @Nullable
    private final Double longitude;

    public GpsLocationEntity(
        @Nullable Double latitude,
        @Nullable Double longitude
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Nullable
    public Double getLatitude() {
        return latitude;
    }

    @Nullable
    public Double getLongitude() {
        return longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GpsLocationEntity that = (GpsLocationEntity) o;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

    @NonNull
    @Override
    public String toString() {
        return "GpsLocationEntity{" +
            "latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}
