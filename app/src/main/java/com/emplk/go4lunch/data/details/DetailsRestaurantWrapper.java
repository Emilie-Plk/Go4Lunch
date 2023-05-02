package com.emplk.go4lunch.data.details;

import java.util.List;

public abstract class DetailsRestaurantWrapper {
    public static class Loading extends DetailsRestaurantWrapper {
// TODO: handle Loading state
    }

    public static class Success extends DetailsRestaurantWrapper {
        private final DetailsRestaurantEntity result;

        public Success(DetailsRestaurantEntity result) {
            this.result = result;
        }

        public DetailsRestaurantEntity getResult() {
            return result;
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
