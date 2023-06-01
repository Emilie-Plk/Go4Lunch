package com.emplk.go4lunch.domain.gps.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class LocationStateEntity {

    public static class Success extends LocationStateEntity {
        @Nullable
        public final LocationEntity locationEntity;

        public Success(@Nullable LocationEntity locationEntity) {
            this.locationEntity = locationEntity;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Success success = (Success) o;
            return Objects.equals(locationEntity, success.locationEntity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(locationEntity);
        }

        @NonNull
        @Override
        public String toString() {
            return "Success{" +
                "locationEntity=" + locationEntity +
                '}';
        }
    }

    public static class GpsProviderDisabled extends LocationStateEntity {

    }
}