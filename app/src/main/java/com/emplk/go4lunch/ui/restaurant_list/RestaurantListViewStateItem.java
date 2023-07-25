package com.emplk.go4lunch.ui.restaurant_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class RestaurantListViewStateItem {

    public enum Type {
        LOADING_STATE,
        DISPLAY_RESTAURANT_LIST,
        ERROR_STATE
    }

    @NonNull
    protected final Type type;

    public RestaurantListViewStateItem(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    @Override
    public abstract boolean equals(@Nullable Object obj);

    public static class Loading extends RestaurantListViewStateItem {

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

    public static class RestaurantItemItem extends RestaurantListViewStateItem {
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
        private final RestaurantOpeningState restaurantOpeningState;

        @Nullable
        private final String pictureUrl;

        @NonNull
        private final Boolean isRatingBarVisible;

        @NonNull
        private final Float rating;

        public RestaurantItemItem(
            @NonNull String id,
            @NonNull String name,
            @NonNull String address,
            @NonNull String distance,
            @NonNull String attendants,
            @NonNull RestaurantOpeningState restaurantOpeningState,
            @Nullable String pictureUrl,
            @NonNull Boolean isRatingBarVisible,
            @NonNull Float rating
        ) {
            super(Type.DISPLAY_RESTAURANT_LIST);
            this.id = id;
            this.name = name;
            this.address = address;
            this.distance = distance;
            this.attendants = attendants;
            this.restaurantOpeningState = restaurantOpeningState;
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


        @Nullable
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
        public RestaurantOpeningState getRestaurantOpeningState() {
            return restaurantOpeningState;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RestaurantItemItem that = (RestaurantItemItem) o;
            return id.equals(that.id) && name.equals(that.name) && address.equals(that.address) && distance.equals(that.distance) && attendants.equals(that.attendants) && restaurantOpeningState == that.restaurantOpeningState && Objects.equals(pictureUrl, that.pictureUrl) && isRatingBarVisible.equals(that.isRatingBarVisible) && rating.equals(that.rating);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, address, distance, attendants, restaurantOpeningState, pictureUrl, isRatingBarVisible, rating);
        }

        @NonNull
        @Override
        public String toString() {
            return "RestaurantItemItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", attendants='" + attendants + '\'' +
                ", restaurantOpeningState=" + restaurantOpeningState +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", isRatingBarVisible=" + isRatingBarVisible +
                ", rating=" + rating +
                '}';
        }
    }

    public static class RestaurantListErrorItem extends RestaurantListViewStateItem {
        @NonNull
        private final String errorMessage;


        @NonNull
        private final ErrorDrawable errorDrawable;

        public RestaurantListErrorItem(
            @NonNull String errorMessage,
            @NonNull ErrorDrawable errorDrawable
        ) {
            super(Type.ERROR_STATE);
            this.errorMessage = errorMessage;
            this.errorDrawable = errorDrawable;
        }

        @NonNull
        public String getErrorMessage() {
            return errorMessage;
        }

        @NonNull
        public ErrorDrawable getErrorDrawable() {
            return errorDrawable;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RestaurantListErrorItem that = (RestaurantListErrorItem) o;
            return errorDrawable == that.errorDrawable && errorMessage.equals(that.errorMessage);
        }

        @Override
        public int hashCode() {
            return Objects.hash(errorMessage, errorDrawable);
        }

        @NonNull
        @Override
        public String toString() {
            return "RestaurantListErrorItem{" +
                "errorMessage='" + errorMessage + '\'' +
                ", errorDrawable=" + errorDrawable +
                '}';
        }
    }
}
