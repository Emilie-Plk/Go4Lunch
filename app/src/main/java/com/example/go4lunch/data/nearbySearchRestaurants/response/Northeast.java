package com.example.go4lunch.data.nearbySearchRestaurants.response;

import com.google.gson.annotations.SerializedName;

public class Northeast{

	@SerializedName("lng")
	private Object lng;

	@SerializedName("lat")
	private Object lat;

	public Object getLng(){
		return lng;
	}

	public Object getLat(){
		return lat;
	}
}