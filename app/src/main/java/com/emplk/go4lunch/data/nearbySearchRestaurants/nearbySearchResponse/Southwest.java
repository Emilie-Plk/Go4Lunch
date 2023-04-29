package com.emplk.go4lunch.data.nearbySearchRestaurants.nearbySearchResponse;

import com.google.gson.annotations.SerializedName;

public class Southwest{

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