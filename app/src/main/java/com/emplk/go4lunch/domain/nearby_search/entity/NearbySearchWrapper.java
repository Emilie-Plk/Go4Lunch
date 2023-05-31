package com.emplk.go4lunch.domain.nearby_search.entity;

import androidx.annotation.NonNull;

import java.util.List;

public abstract class NearbySearchWrapper {

    public static class Loading extends NearbySearchWrapper {
    }

    public static class Success extends NearbySearchWrapper {
        @NonNull
        private final List<NearbySearchEntity> results;

        public Success(@NonNull List<NearbySearchEntity> results) {
            this.results = results;
        }

        @NonNull
        public List<NearbySearchEntity> getNearbySearchEntityList() {
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
