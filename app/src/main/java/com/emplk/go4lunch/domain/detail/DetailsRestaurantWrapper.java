package com.emplk.go4lunch.domain.detail;

public abstract class DetailsRestaurantWrapper {

    public static class Loading extends DetailsRestaurantWrapper {
    }

    public static class Success extends DetailsRestaurantWrapper {
        private final DetailsRestaurantEntity response;

        public Success(DetailsRestaurantEntity response) {
            this.response = response;
        }

        public DetailsRestaurantEntity getResponse() {
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
