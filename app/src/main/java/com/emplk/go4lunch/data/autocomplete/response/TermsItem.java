package com.emplk.go4lunch.data.autocomplete.response;

import com.google.gson.annotations.SerializedName;

public class TermsItem {

    @SerializedName("offset")
    private int offset;

    @SerializedName("value")
    private String value;

    public int getOffset() {
        return offset;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return
            "TermsItem{" +
                "offset = '" + offset + '\'' +
                ",value = '" + value + '\'' +
                "}";
    }
}