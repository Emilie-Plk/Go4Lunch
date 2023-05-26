package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

public class MainTextMatchedSubstringsItem{
	private int offset;
	private int length;

	public int getOffset(){
		return offset;
	}

	public int getLength(){
		return length;
	}

	@Override
 	public String toString(){
		return 
			"MainTextMatchedSubstringsItem{" + 
			"offset = '" + offset + '\'' + 
			",length = '" + length + '\'' + 
			"}";
		}
}
