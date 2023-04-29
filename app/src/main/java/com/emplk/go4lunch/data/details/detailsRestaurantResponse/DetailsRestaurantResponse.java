package com.emplk.go4lunch.data.details.detailsRestaurantResponse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailsRestaurantResponse {

    @SerializedName("result")
    private final Result result;

    @SerializedName("html_attributions")
    private final List<Object> htmlAttributions;

    @SerializedName("status")
    private final String status;

	public DetailsRestaurantResponse(Result result, List<Object> htmlAttributions, String status) {
		this.result = result;
		this.htmlAttributions = htmlAttributions;
		this.status = status;
	}

	public Result getResult() {
        return result;
    }

    public List<Object> getHtmlAttributions() {
        return htmlAttributions;
    }

    public String getStatus() {
        return status;
    }

    @NonNull
    @Override
    public String toString() {
        return
            "DetailsRestaurantResponse{" +
                "result = '" + result + '\'' +
                ",html_attributions = '" + htmlAttributions + '\'' +
                ",status = '" + status + '\'' +
                "}";
    }
}