package com.emplk.go4lunch.data.details;

import com.emplk.go4lunch.data.details.details_restaurant_response.DetailsRestaurantResponse;

public abstract class DetailsRestaurantWrapper {

    public static class Loading extends DetailsRestaurantWrapper {
    }

    public static class Success extends DetailsRestaurantWrapper {
        private final DetailsRestaurantResponse response;

        public Success(DetailsRestaurantResponse response) {
            this.response = response;
        }

        public DetailsRestaurantResponse getResponse() {
            return response;
        }
    }

    public static class Error extends DetailsRestaurantWrapper {
        private final Throwable throwable;

        public Error(Throwable throwable) {
            this.throwable = throwable;
        }


        public Throwable getThrowable() {
            return throwable;
        }
    }
}
