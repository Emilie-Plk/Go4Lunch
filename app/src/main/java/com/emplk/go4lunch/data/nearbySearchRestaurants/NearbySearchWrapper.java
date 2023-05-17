package com.emplk.go4lunch.data.nearbySearchRestaurants;

import java.util.List;

public abstract class NearbySearchWrapper {

    public static class Loading extends NearbySearchWrapper {
    }

    public static class Success extends NearbySearchWrapper {
        private final List<NearbySearchEntity> results;

        public Success(List<NearbySearchEntity> results) {
            this.results = results;
        }

        public List<NearbySearchEntity> getResults() {
            return results;
        }
    }

    public static class NoResults extends NearbySearchWrapper {
    }

    public static class RequestError extends NearbySearchWrapper {
        private final Throwable throwable;

        public RequestError(Throwable throwable) {
            this.throwable = throwable;
        }


        public Throwable getThrowable() {
            return throwable;
        }
    }
}
