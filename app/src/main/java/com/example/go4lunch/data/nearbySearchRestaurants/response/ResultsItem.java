package com.example.go4lunch.data.nearbySearchRestaurants.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class ResultsItem {

    @Nullable
    @SerializedName("types")
    private final List<String> types;

    @Nullable
    @SerializedName("rating")
    private final Float rating;

    @Nullable
    @SerializedName("photos")
    private final List<PhotosItem> photos;

    @Nullable
    @SerializedName("name")
    private final String name;

    @Nullable
    @SerializedName("opening_hours")
    private final OpeningHours openingHours;

    @Nullable
    @SerializedName("geometry")
    private final Geometry geometry;

    @Nullable
    @SerializedName("vicinity")
    private final String vicinity;

    @Nullable
    @SerializedName("place_id")
    private final String placeId;

    public ResultsItem(
        @Nullable String placeId,
        @Nullable Float rating,
        @Nullable List<String> types,
        @Nullable List<PhotosItem> photos,
        @Nullable String name,
        @Nullable OpeningHours openingHours,
        @Nullable Geometry geometry,
        @Nullable String vicinity
    ) {
        this.types = types;
        this.rating = rating;
        this.photos = photos;
        this.name = name;
        this.openingHours = openingHours;
        this.geometry = geometry;
        this.vicinity = vicinity;
        this.placeId = placeId;
    }

    @Nullable
    public String getPlaceId() {
        return placeId;
    }

    @Nullable
    public List<String> getTypes() {
        return types;
    }

    @Nullable
    public Float getRating() {
        return rating;
    }

    @Nullable
    public List<PhotosItem> getPhotos() {
        return photos;
    }


    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    @Nullable
    public Geometry getGeometry() {
        return geometry;
    }

    @Nullable
    public String getVicinity() {
        return vicinity;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResultsItem that = (ResultsItem) o;
        return Objects.equals(types, that.types) &&
            Objects.equals(rating, that.rating) &&
            Objects.equals(photos, that.photos) &&
            Objects.equals(name, that.name) &&
            Objects.equals(openingHours, that.openingHours)
            && Objects.equals(geometry, that.geometry)
            && Objects.equals(vicinity, that.vicinity)
            && Objects.equals(placeId, that.placeId
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(types, rating, photos, name, openingHours, geometry, vicinity, placeId);
    }

    @NonNull
    @Override
    public String toString() {
        return "ResultsItem{" +
            "types=" + types +
            ", rating=" + rating +
            ", photos=" + photos +
            ", name='" + name + '\'' +
            ", openingHours=" + openingHours +
            ", geometry=" + geometry +
            ", vicinity='" + vicinity + '\'' +
            ", placeId='" + placeId + '\'' +
            '}';
    }
}