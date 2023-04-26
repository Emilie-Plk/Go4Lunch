package com.example.go4lunch.data.nearbySearchRestaurants.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class PhotosItem{

	@Nullable
	@SerializedName("photo_reference")
	private final String photoReference;


	@SerializedName("width")
	private final int width;

	@Nullable
	@SerializedName("html_attributions")
	private final List<String> htmlAttributions;

	@SerializedName("height")
	private final int height;

	public PhotosItem(
		@Nullable String photoReference,
		int width,
		@Nullable List<String> htmlAttributions,
		int height) {
		this.photoReference = photoReference;
		this.width = width;
		this.htmlAttributions = htmlAttributions;
		this.height = height;
	}

	@Nullable
	public String getPhotoReference(){
		return photoReference;
	}

	public int getWidth(){
		return width;
	}

	@Nullable
	public List<String> getHtmlAttributions(){
		return htmlAttributions;
	}

	public int getHeight(){
		return height;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		PhotosItem that = (PhotosItem) o;
		return width == that.width && height == that.height && Objects.equals(photoReference, that.photoReference) && Objects.equals(htmlAttributions, that.htmlAttributions);
	}

	@Override
	public int hashCode() {
		return Objects.hash(photoReference, width, htmlAttributions, height);
	}

	@NonNull
	@Override
	public String toString() {
		return "PhotosItem{" +
			"photoReference='" + photoReference + '\'' +
			", width=" + width +
			", htmlAttributions=" + htmlAttributions +
			", height=" + height +
			'}';
	}
}