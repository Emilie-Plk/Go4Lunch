package com.example.go4lunch.data.API.nearby_search_restaurants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NearbySearchModel {
    @NonNull
    private final String name;
    @NonNull
    private final String address;

    private final double latitude;

    private final double longitude;

    public NearbySearchModel(
        @NonNull String name,
        @NonNull String address,
        double latitude,
        double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public static List<NearbySearchModel> fromNearbySearchResponse(NearbySearchResponse response) {
        List<NearbySearchModel> results = new ArrayList<>();
        for (NearbySearchResponse.Result result : response.getResults()) {
            String name = result.getName();
            String address = result.getVicinity();
            double latitude = result.getGeometry().getLocation().getLat();
            double longitude = result.getGeometry().getLocation().getLng();
            NearbySearchModel searchResult = new NearbySearchModel(name, address, latitude, longitude);
            results.add(searchResult);
        }
        return results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NearbySearchModel that = (NearbySearchModel) o;
        return Double.compare(that.latitude, latitude) == 0 && Double.compare(that.longitude, longitude) == 0 && Objects.equals(name, that.name) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, latitude, longitude);
    }

    @NonNull
    @Override
    public String toString() {
        return "NearbySearchModel{" +
            "name='" + name + '\'' +
            ", address='" + address + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}

