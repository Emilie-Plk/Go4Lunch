package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import androidx.annotation.NonNull;

import java.util.List;

public class AutocompleteSuggestionResponse {
	private List<PredictionsItem> predictions;
	private String status;

	public List<PredictionsItem> getPredictions(){
		return predictions;
	}

	public String getStatus(){
		return status;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"AutocompleteSuggestionResponse{" +
			"predictions = '" + predictions + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}