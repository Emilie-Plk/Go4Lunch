package com.emplk.go4lunch.domain.gps;

import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class GpsResponse {

    public static class Success extends GpsResponse {
        @Nullable
        public final GpsLocationEntity gpsLocationEntity;

        public Success(@Nullable GpsLocationEntity gpsLocationEntity) {
            this.gpsLocationEntity = gpsLocationEntity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Success success = (Success) o;
            return Objects.equals(gpsLocationEntity, success.gpsLocationEntity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(gpsLocationEntity);
        }

        @Override
        public String toString() {
            return "Success{" +
                "gpsLocationEntity=" + gpsLocationEntity +
                '}';
        }
    }

    public static class GpsProviderDisabled extends GpsResponse {

    }
}
