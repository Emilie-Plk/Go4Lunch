package com.example.go4lunch.data.nearbySearchRestaurants.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class NearbySearchResponse{

	@Nullable
	@SerializedName("next_page_token")
	private final String nextPageToken;

	@Nullable
	@SerializedName("html_attributions")
	private final List<Object> htmlAttributions;

	@Nullable
	@SerializedName("results")
	private final List<ResultsItem> results;

	@Nullable
	@SerializedName("status")
	private String status;

	@Nullable
	public String getNextPageToken(){
		return nextPageToken;
	}

	@Nullable
	public List<Object> getHtmlAttributions(){
		return htmlAttributions;
	}

	@Nullable
	public List<ResultsItem> getResults(){
		return results;
	}

	@Nullable
	public String getStatus(){
		return status;
	}

	public NearbySearchResponse(@Nullable String nextPageToken, @Nullable List<Object> htmlAttributions, @Nullable List<ResultsItem> results, @Nullable String status) {
		this.nextPageToken = nextPageToken;
		this.htmlAttributions = htmlAttributions;
		this.results = results;
		this.status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NearbySearchResponse that = (NearbySearchResponse) o;
		return Objects.equals(nextPageToken, that.nextPageToken) && Objects.equals(htmlAttributions, that.htmlAttributions) && Objects.equals(results, that.results) && Objects.equals(status, that.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nextPageToken, htmlAttributions, results, status);
	}

	@NonNull
	@Override
	public String toString() {
		return "NearbySearchResponse{" +
			"nextPageToken='" + nextPageToken + '\'' +
			", htmlAttributions=" + htmlAttributions +
			", results=" + results +
			", status='" + status + '\'' +
			'}';
	}
}