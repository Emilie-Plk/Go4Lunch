package com.emplk.go4lunch.data.autocomplete;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.gps.LocationEntity;

import java.util.Objects;

public class UserQuery {

    @NonNull
    private final String userInput;

    @NonNull
    private final LocationEntity locationEntity;


    public UserQuery(
        @NonNull String userInput,
        @NonNull LocationEntity locationEntity
    ) {
        this.userInput = userInput;
        this.locationEntity = locationEntity;
    }

    @NonNull
    public String getUserInput() {
        return userInput;
    }

    @NonNull
    public LocationEntity getLocationEntity() {
        return locationEntity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserQuery userQuery = (UserQuery) o;
        return userInput.equals(userQuery.userInput) && locationEntity.equals(userQuery.locationEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userInput, locationEntity);
    }

    @NonNull
    @Override
    public String toString() {
        return "UserQuery{" +
            "userInput='" + userInput + '\'' +
            ", locationEntity=" + locationEntity +
            '}';
    }
}
