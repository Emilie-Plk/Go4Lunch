package com.example.go4lunch.data.nearbySearchRestaurants.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Geometry{

	@SerializedName("viewport")
	private final Viewport viewport;

	@SerializedName("location")
	private final Location location;

	public Geometry(Viewport viewport, Location location) {
		this.viewport = viewport;
		this.location = location;
	}

	public Viewport getViewport(){
		return viewport;
	}

	public Location getLocation(){
		return location;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Geometry geometry = (Geometry) o;
		return Objects.equals(viewport, geometry.viewport) && Objects.equals(location, geometry.location);
	}

	@Override
	public int hashCode() {
		return Objects.hash(viewport, location);
	}

	@NonNull
	@Override
	public String toString() {
		return "Geometry{" +
			"viewport=" + viewport +
			", location=" + location +
			'}';
	}
}
