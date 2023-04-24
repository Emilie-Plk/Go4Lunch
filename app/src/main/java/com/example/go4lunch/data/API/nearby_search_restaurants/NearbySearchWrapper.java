package com.example.go4lunch.data.API.nearby_search_restaurants;

import java.util.List;

public abstract class NearbySearchWrapper {

    public static class Loading extends NearbySearchWrapper {
// TODO: handle Loading state
    }

    public static class Success extends NearbySearchWrapper {
        private final List<NearbySearchResult> results;

        public Success(List<NearbySearchResult> results) {
            this.results = results;
        }

        public List<NearbySearchResult> getResults() {
            return results;
        }
    }

    public static class Error extends NearbySearchWrapper {
        private final Throwable throwable;

        public Error(Throwable throwable) {
            this.throwable = throwable;
        }


        public Throwable getThrowable() {
            return throwable;
        }
    }
}
