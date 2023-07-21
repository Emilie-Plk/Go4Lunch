package com.emplk.go4lunch.data.autocomplete.response;

import com.google.gson.annotations.SerializedName;

public class MatchedSubstringsItem{

    @SerializedName("offset")
    private int offset;

    @SerializedName("length")
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
            "MatchedSubstringsItem{" + 
            "offset = '" + offset + '\'' + 
            ",length = '" + length + '\'' + 
            "}";
        }
}