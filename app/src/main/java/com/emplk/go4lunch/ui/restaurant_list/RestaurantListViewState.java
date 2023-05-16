package com.emplk.go4lunch.ui.restaurant_list;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class RestaurantListViewState {

    public enum Type {
        LOADING_STATE,
        DISPLAY_RESTAURANT_LIST,
        ERROR_STATE
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

        public Loading() {
            super(Type.LOADING_STATE);
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
        private final Boolean isRatingBarVisible;

        @NonNull
        private final Float rating;

        public RestaurantList(
            @NonNull String id,
            @NonNull String name,
            @NonNull String address,
            @NonNull String distance,
            @NonNull String attendants,
            @NonNull String openingHours,
            @NonNull Boolean isOpen,
            @NonNull String pictureUrl,
            @NonNull Boolean isRatingBarVisible,
            @NonNull Float rating
        ) {
            super(Type.DISPLAY_RESTAURANT_LIST);
            this.id = id;
            this.name = name;
            this.address = address;
            this.distance = distance;
            this.attendants = attendants;
            this.openingHours = openingHours;
            this.isOpen = isOpen;
            this.pictureUrl = pictureUrl;
            this.isRatingBarVisible = isRatingBarVisible;
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
        public Boolean getIsRatingBarVisible() {
            return isRatingBarVisible;
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
            return id.equals(that.id) && name.equals(that.name) && address.equals(that.address) && distance.equals(that.distance) && attendants.equals(that.attendants) && openingHours.equals(that.openingHours) && isOpen.equals(that.isOpen) && pictureUrl.equals(that.pictureUrl) && isRatingBarVisible.equals(that.isRatingBarVisible) && rating.equals(that.rating);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, address, distance, attendants, openingHours, isOpen, pictureUrl, isRatingBarVisible, rating);
        }

        @Override
        public String toString() {
            return "RestaurantList{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", attendants='" + attendants + '\'' +
                ", openingHours='" + openingHours + '\'' +
                ", isOpen=" + isOpen +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", isRatingBarVisible=" + isRatingBarVisible +
                ", rating=" + rating +
                '}';
        }
    }

    public static class RestaurantListError extends RestaurantListViewState {
        @NonNull
        private final String errorMessage;

        @Nullable
        private final Drawable errorDrawable;  // int or Drawable?

        public RestaurantListError(@NonNull String errorMessage, @Nullable Drawable errorDrawable) {
            super(Type.ERROR_STATE);
            this.errorMessage = errorMessage;
            this.errorDrawable = errorDrawable;
        }

        @NonNull
        public String getErrorMessage() {
            return errorMessage;
        }

        @Nullable
        public Drawable getErrorDrawable() {
            return errorDrawable;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RestaurantListError that = (RestaurantListError) o;
            return errorMessage.equals(that.errorMessage) && Objects.equals(errorDrawable, that.errorDrawable);
        }

        @Override
        public int hashCode() {
            return Objects.hash(errorMessage, errorDrawable);
        }

        @NonNull
        @Override
        public String toString() {
            return "RestaurantListError{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errorDrawable=" + errorDrawable +
                '}';
        }
    }

}
