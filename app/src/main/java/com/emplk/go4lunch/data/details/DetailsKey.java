package com.emplk.go4lunch.data.details;

import java.util.Objects;

public class DetailsKey {

    private final String placeId;

    public DetailsKey(String placeId) {
        this.placeId = placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailsKey that = (DetailsKey) o;
        return Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeId);
    }
}
