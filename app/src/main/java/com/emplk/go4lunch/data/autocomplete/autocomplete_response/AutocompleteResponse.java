package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import java.util.List;

public class AutocompleteResponse{
	private List<PredictionsItem> predictions;
	private String status;

	public List<PredictionsItem> getPredictions(){
		return predictions;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"AutocompleteResponse{" + 
			"predictions = '" + predictions + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}