package com.emplk.go4lunch.data.details.details_restaurant_response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotosItem {

    @SerializedName("photo_reference")
    private String photoReference;

    @SerializedName("width")
    private int width;

    @SerializedName("html_attributions")
    private List<String> htmlAttributions;

    @SerializedName("height")
    private int height;

    public String getPhotoReference() {
        return photoReference;
    }

    public int getWidth() {
        return width;
    }

    public List<String> getHtmlAttributions() {
        return htmlAttributions;
    }

    public int getHeight() {
        return height;
    }

    @NonNull
    @Override
    public String toString() {
        return
            "PhotosItem{" +
                "photo_reference = '" + photoReference + '\'' +
                ",width = '" + width + '\'' +
                ",html_attributions = '" + htmlAttributions + '\'' +
                ",height = '" + height + '\'' +
                "}";
    }
}