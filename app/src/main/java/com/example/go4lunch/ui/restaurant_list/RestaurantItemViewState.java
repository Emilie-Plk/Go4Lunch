package com.example.go4lunch.ui.restaurant_list;

import androidx.annotation.NonNull;

import java.util.Objects;

public class RestaurantItemViewState {

    private final int id;

    @NonNull
    private final String name;

/*    @NonNull
    private final String cuisine;*/

    @NonNull
    private final String address;

/*    @NonNull
    private final String distance;

    @NonNull
    private final String attendants;

    @NonNull
    private final String openingHours;

    @NonNull
    private final String pictureUrl;

    private final float rating;*/

    public RestaurantItemViewState(
        int id,
        @NonNull String name,
//        @NonNull String cuisine,
        @NonNull String address
      /*  @NonNull String distance,
        @NonNull String attendants,
        @NonNull String openingHours,
        @NonNull String pictureUrl,
        float rating*/
    ) {
        this.id = id;
        this.name = name;
//        this.cuisine = cuisine;
        this.address = address;
     /*   this.distance = distance;
        this.attendants = attendants;
        this.openingHours = openingHours;
        this.pictureUrl = pictureUrl;
        this.rating = rating;*/
    }

    public int getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

/*    @NonNull
    public String getCuisine() {
        return cuisine;
    }*/

    @NonNull
    public String getAddress() {
        return address;
    }

 /*   @NonNull
    public String getDistance() {
        return distance;
    }

    @NonNull
    public String getAttendants() {
        return attendants;
    }

    @NonNull
    public String getOpeningHours() {
        return openingHours;
    }

    @NonNull
    public String getPictureUrl() {
        return pictureUrl;
    }

    public float getRating() {
        return rating;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantItemViewState that = (RestaurantItemViewState) o;
        return id == that.id  && name.equals(that.name) && address.equals(that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address);
    }

    @NonNull
    @Override
    public String toString() {
        return "RestaurantItemViewState{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", address='" + address + '\'' +
            '}';
    }
}
