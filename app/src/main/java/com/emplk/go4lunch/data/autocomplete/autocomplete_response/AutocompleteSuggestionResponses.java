package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class AutocompleteSuggestionResponses {
    @Nullable
    @SerializedName("predictions")
    private final List<PredictionsItem> predictions;
    @Nullable
    @SerializedName("status")
    private final String status;

    public AutocompleteSuggestionResponses(
        @Nullable List<PredictionsItem> predictions,
        @Nullable String status
    ) {
        this.predictions = predictions;
        this.status = status;
    }

    @Nullable
    public List<PredictionsItem> getPredictions() {
        return predictions;
    }

    @Nullable
    public String getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AutocompleteSuggestionResponses that = (AutocompleteSuggestionResponses) o;
        return Objects.equals(predictions, that.predictions) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(predictions, status);
    }

    @NonNull
    @Override
    public String toString() {
        return "AutocompleteSuggestionResponses{" +
            "predictions=" + predictions +
            ", status='" + status + '\'' +
            '}';
    }
}