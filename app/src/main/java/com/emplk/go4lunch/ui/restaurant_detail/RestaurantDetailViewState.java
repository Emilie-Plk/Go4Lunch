package com.emplk.go4lunch.ui.restaurant_detail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.emplk.go4lunch.ui.utils.RestaurantFavoriteState;

import java.util.Objects;

public abstract class RestaurantDetailViewState {

    public enum Type {
        LOADING_STATE,
        DISPLAY_RESTAURANT_DETAIL,
        ERROR_STATE
    }

    @NonNull
    protected final Type type;

    public RestaurantDetailViewState(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    @Override
    public abstract boolean equals(@Nullable Object obj);

    public static class Loading extends RestaurantDetailViewState {

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

    public static class RestaurantDetail extends RestaurantDetailViewState {

        @NonNull
        private final String id;

        @NonNull
        private final String name;
        @NonNull
        private final String vicinity;

        @Nullable
        private final String pictureUrl;

        @NonNull
        private final Float rating;

        @Nullable
        private final String phoneNumber;

        @Nullable
        private final String websiteUrl;

        @Nullable
        private final AttendanceState attendanceState;

        @Nullable
        private final RestaurantFavoriteState restaurantFavoriteState;
        @Nullable
        private final Boolean isVeganFriendly;
        @NonNull
        private final Boolean isPhoneNumberAvailable;

        @NonNull
        private final Boolean isWebsiteAvailable;

        public RestaurantDetail(
            @NonNull String id,
            @NonNull String name,
            @NonNull String vicinity,
            @Nullable String pictureUrl,
            @NonNull Float rating,
            @Nullable String phoneNumber,
            @Nullable String websiteUrl,
            @Nullable AttendanceState attendanceState,
            @Nullable RestaurantFavoriteState restaurantFavoriteState,
            @Nullable Boolean isVeganFriendly,
            @NonNull Boolean isPhoneNumberAvailable,
            @NonNull Boolean isWebsiteAvailable
        ) {
            super(Type.DISPLAY_RESTAURANT_DETAIL);
            this.id = id;
            this.name = name;
            this.vicinity = vicinity;
            this.pictureUrl = pictureUrl;
            this.rating = rating;
            this.phoneNumber = phoneNumber;
            this.websiteUrl = websiteUrl;
            this.attendanceState = attendanceState;
            this.restaurantFavoriteState = restaurantFavoriteState;
            this.isVeganFriendly = isVeganFriendly;
            this.isPhoneNumberAvailable = isPhoneNumberAvailable;
            this.isWebsiteAvailable = isWebsiteAvailable;
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
        public String getVicinity() {
            return vicinity;
        }

        @Nullable
        public String getPictureUrl() {
            return pictureUrl;
        }

        @NonNull
        public Float getRating() {
            return rating;
        }

        @Nullable
        public String getPhoneNumber() {
            return phoneNumber;
        }

        @Nullable
        public String getWebsiteUrl() {
            return websiteUrl;
        }

        @Nullable
        public AttendanceState getAttendanceState() {
            return attendanceState;
        }

        @Nullable
        public RestaurantFavoriteState getRestaurantFavoriteState() {
            return restaurantFavoriteState;
        }

        @Nullable
        public Boolean isVeganFriendly() {
            return isVeganFriendly;
        }

        @NonNull
        public Boolean isPhoneNumberAvailable() {
            return isPhoneNumberAvailable;
        }

        @NonNull
        public Boolean isWebsiteAvailable() {
            return isWebsiteAvailable;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RestaurantDetail that = (RestaurantDetail) o;
            return id.equals(that.id) && name.equals(that.name) && vicinity.equals(that.vicinity) && Objects.equals(pictureUrl, that.pictureUrl) && rating.equals(that.rating) && Objects.equals(phoneNumber, that.phoneNumber) && Objects.equals(websiteUrl, that.websiteUrl) && attendanceState == that.attendanceState && restaurantFavoriteState == that.restaurantFavoriteState && Objects.equals(isVeganFriendly, that.isVeganFriendly) && isPhoneNumberAvailable.equals(that.isPhoneNumberAvailable) && isWebsiteAvailable.equals(that.isWebsiteAvailable);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, vicinity, pictureUrl, rating, phoneNumber, websiteUrl, attendanceState, restaurantFavoriteState, isVeganFriendly, isPhoneNumberAvailable, isWebsiteAvailable);
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantDetailViewState{" +
            "type=" + type +
            '}';
    }

    public static class Error extends RestaurantDetailViewState {

        @NonNull
        private final String errorMessage;

        public Error(@NonNull String errorMessage) {
            super(Type.ERROR_STATE);
            this.errorMessage = errorMessage;
        }

        @NonNull
        public String getErrorMessage() {
            return errorMessage;
        }

        @NonNull
        @Override
        public String toString() {
            return "Error{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Error)) return false;
            Error error = (Error) obj;
            return errorMessage.equals(error.errorMessage);
        }
    }
}
