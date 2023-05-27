package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import androidx.annotation.NonNull;

import java.util.List;

public class PredictionsItem{
	private String reference;
	private List<String> types;
	private List<MatchedSubstringsItem> matchedSubstrings;
	private List<TermsItem> terms;
	private StructuredFormatting structuredFormatting;
	private String description;
	private String placeId;

	public String getReference(){
		return reference;
	}

	public List<String> getTypes(){
		return types;
	}

	public List<MatchedSubstringsItem> getMatchedSubstrings(){
		return matchedSubstrings;
	}

	public List<TermsItem> getTerms(){
		return terms;
	}

	public StructuredFormatting getStructuredFormatting(){
		return structuredFormatting;
	}

	public String getDescription(){
		return description;
	}

	public String getPlaceId(){
		return placeId;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"PredictionsItem{" + 
			"reference = '" + reference + '\'' + 
			",types = '" + types + '\'' + 
			",matched_substrings = '" + matchedSubstrings + '\'' + 
			",terms = '" + terms + '\'' + 
			",structured_formatting = '" + structuredFormatting + '\'' + 
			",description = '" + description + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}