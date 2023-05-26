package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

public class TermsItem{
	private int offset;
	private String value;

	public int getOffset(){
		return offset;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"TermsItem{" + 
			"offset = '" + offset + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}
