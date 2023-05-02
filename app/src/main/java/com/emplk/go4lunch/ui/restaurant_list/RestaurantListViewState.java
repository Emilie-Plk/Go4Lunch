package com.emplk.go4lunch.ui.restaurant_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class RestaurantListViewState {

    public enum Type {
        LOADING,
        DISPLAY_RESTAURANT_LIST,
        NO_GPS_CONNEXION,
        NO_RESTAURANT_FOUND,
        DATABASE_ERROR
    }

    @NonNull
    protected final Type type;

    public RestaurantListViewState(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    @Override
    public abstract boolean equals(@Nullable Object obj);

    public static class Loading extends RestaurantListViewState {

        public Loading(@NonNull String loadingText) {
            super(Type.LOADING);
        }

        @NonNull
        @Override
        public String toString() {
            return "Loading{}";
        }
        @Override
        public boolean equals(@Nullable Object obj) {
            if (this == obj) return true;
            return obj != null && getClass() == obj.getClass();
        }
    }

    public static class RestaurantList extends RestaurantListViewState {
        @NonNull
        private final String id;

        @NonNull
        private final String name;

        @NonNull
        private final String cuisine;

        @NonNull
        private final String address;

        @NonNull
        private final String distance;

        @NonNull
        private final String attendants;

        @NonNull
        private final String openingHours;

        @NonNull
        private final Boolean isOpen;

        @NonNull
        private final String pictureUrl;

        @NonNull
        private final Float rating;

        public RestaurantList(
            @NonNull String id,
            @NonNull String name,
            @NonNull String cuisine,
            @NonNull String address,
            @NonNull String distance,
            @NonNull String attendants,
            @NonNull String openingHours,
            @NonNull Boolean isOpen,
            @NonNull String pictureUrl,
            @NonNull Float rating
        ) {
            super(Type.DISPLAY_RESTAURANT_LIST);
            this.id = id;
            this.name = name;
            this.cuisine = cuisine;
            this.address = address;
            this.distance = distance;
            this.attendants = attendants;
            this.openingHours = openingHours;
            this.isOpen = isOpen;
            this.pictureUrl = pictureUrl;
            this.rating = rating;
        }

        @NonNull
        public String getId() {
            return id;
        }

        @NonNull
        public String getName() {
            return name;
        }

        @NonNull
        public String getCuisine() {
            return cuisine;
        }

        @NonNull
        public String getAddress() {
            return address;
        }

        @NonNull
        public String getDistance() {
            return distance;
        }

        @NonNull
        public String getAttendants() {
            return attendants;
        }

        @NonNull
        public String getOpeningHours() {
            return openingHours;
        }

        @NonNull
        public String getPictureUrl() {
            return pictureUrl;
        }

        @NonNull
        public Float getRating() {
            return rating;
        }

        @NonNull
        public Boolean getOpen() {
            return isOpen;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RestaurantList that = (RestaurantList) o;
            return id.equals(that.id) && name.equals(that.name) && cuisine.equals(that.cuisine) && address.equals(that.address) && distance.equals(that.distance) && attendants.equals(that.attendants) && openingHours.equals(that.openingHours) && isOpen.equals(that.isOpen) && pictureUrl.equals(that.pictureUrl) && rating.equals(that.rating);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, cuisine, address, distance, attendants, openingHours, isOpen, pictureUrl, rating);
        }

        @NonNull
        @Override
        public String toString() {
            return "RestaurantListViewState{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cuisine='" + cuisine + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", attendants='" + attendants + '\'' +
                ", openingHours='" + openingHours + '\'' +
                ", isOpen=" + isOpen +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", rating=" + rating +
                '}';
        }
    }

    public static class NoGpsConnexion extends RestaurantListViewState {

        @NonNull
        private final String noGpsText;

        public NoGpsConnexion(@NonNull String noGpsText) {
            super(Type.NO_GPS_CONNEXION);
            this.noGpsText = noGpsText;
        }

        @NonNull
        public String getNoGpsText() {
            return noGpsText;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NoGpsConnexion that = (NoGpsConnexion) o;
            return noGpsText.equals(that.noGpsText);
        }

        @Override
        public int hashCode() {
            return Objects.hash(noGpsText);
        }

        @NonNull
        @Override
        public String toString() {
            return "NoGpsConnexion{" +
                "noGpsText='" + noGpsText + '\'' +
                '}';
        }
    }

    public static class NoRestaurantFound extends RestaurantListViewState {
        @NonNull
        private final String noRestaurantFoundText;

        public NoRestaurantFound(@NonNull String noRestaurantFoundText) {
            super(Type.NO_RESTAURANT_FOUND);
            this.noRestaurantFoundText = noRestaurantFoundText;
        }

        @NonNull
        public String getNoRestaurantFoundText() {
            return noRestaurantFoundText;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NoRestaurantFound that = (NoRestaurantFound) o;
            return noRestaurantFoundText.equals(that.noRestaurantFoundText);
        }

        @Override
        public int hashCode() {
            return Objects.hash(noRestaurantFoundText);
        }

        @NonNull
        @Override
        public String toString() {
            return "NoRestaurantFound{" +
                "noRestaurantFoundText='" + noRestaurantFoundText + '\'' +
                '}';
        }
    }

    public static class DatabaseError extends RestaurantListViewState {
        @NonNull
        private final String databaseErrorText;

        public DatabaseError(@NonNull String databaseErrorText) {
            super(Type.DATABASE_ERROR);
            this.databaseErrorText = databaseErrorText;
        }

        @NonNull
        public String getDatabaseErrorText() {
            return databaseErrorText;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DatabaseError that = (DatabaseError) o;
            return databaseErrorText.equals(that.databaseErrorText);
        }

        @Override
        public int hashCode() {
            return Objects.hash(databaseErrorText);
        }

        @NonNull
        @Override
        public String toString() {
            return "DatabaseError{" +
                "databaseErrorText='" + databaseErrorText + '\'' +
                '}';
        }
    }

}
