package com.emplk.go4lunch.data.details.detailsRestaurantResponse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Close{

	@SerializedName("time")
	private final String time;

	@SerializedName("day")
	private final int day;

	public Close(String time, int day) {
		this.time = time;
		this.day = day;
	}

	public String getTime(){
		return time;
	}

	public int getDay(){
		return day;
	}

	@NonNull
	@Override
 	public String toString(){
		return 
			"Close{" + 
			"time = '" + time + '\'' + 
			",day = '" + day + '\'' + 
			"}";
		}
}