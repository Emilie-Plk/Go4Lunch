package com.emplk.go4lunch.domain.autocomplete.entity;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public abstract class AutocompleteWrapper {

    public static class Success extends AutocompleteWrapper {
        @NonNull
        private final List<PredictionEntity> predictionEntityList;

        public Success(@NonNull List<PredictionEntity> predictionEntityList) {
            this.predictionEntityList = predictionEntityList;
        }

        @NonNull
        public List<PredictionEntity> getPredictionEntityList() {
            return predictionEntityList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Success success = (Success) o;
            return predictionEntityList.equals(success.predictionEntityList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(predictionEntityList);
        }

        @NonNull
        @Override
        public String toString() {
            return "Success{" +
                "predictionEntityList=" + predictionEntityList +
                '}';
        }
    }

    public static class Error extends AutocompleteWrapper {
        private final Throwable throwable;

        public Error(Throwable throwable) {
            this.throwable = throwable;
        }


        public Throwable getThrowable() {
            return throwable;
        }
    }

    public static class NoResults extends AutocompleteWrapper {
    }
}
