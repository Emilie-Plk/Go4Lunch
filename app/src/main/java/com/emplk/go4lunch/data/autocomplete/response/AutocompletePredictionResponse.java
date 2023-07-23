package com.emplk.go4lunch.data.autocomplete.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AutocompletePredictionResponse {

    @SerializedName("predictions")
    private List<PredictionsItem> predictions;

    @SerializedName("status")
    private String status;

    public List<PredictionsItem> getPredictions() {
        return predictions;
    }

    public String getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String toString() {
        return
            "AutocompletePredictionResponse{" +
                "predictions = '" + predictions + '\'' +
                ",status = '" + status + '\'' +
                "}";
    }
}