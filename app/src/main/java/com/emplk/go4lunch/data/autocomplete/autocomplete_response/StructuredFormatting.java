package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import java.util.List;

public class StructuredFormatting{
	private List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings;
	private String secondaryText;
	private String mainText;

	public List<MainTextMatchedSubstringsItem> getMainTextMatchedSubstrings(){
		return mainTextMatchedSubstrings;
	}

	public String getSecondaryText(){
		return secondaryText;
	}

	public String getMainText(){
		return mainText;
	}

	@Override
 	public String toString(){
		return 
			"StructuredFormatting{" + 
			"main_text_matched_substrings = '" + mainTextMatchedSubstrings + '\'' + 
			",secondary_text = '" + secondaryText + '\'' + 
			",main_text = '" + mainText + '\'' + 
			"}";
		}
}