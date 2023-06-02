package com.emplk.go4lunch.domain.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class ChosenRestaurantEntity {

    @Nullable
    private final String chosenRestaurantId;

    @Nullable
    private final String chosenRestaurantName;

    @Nullable
    private final String chosenRestaurantVicinity;

    public ChosenRestaurantEntity(
        @Nullable String chosenRestaurantId,
        @Nullable String chosenRestaurantName,
        @Nullable String chosenRestaurantVicinity
    ) {
        this.chosenRestaurantId = chosenRestaurantId;
        this.chosenRestaurantName = chosenRestaurantName;
        this.chosenRestaurantVicinity = chosenRestaurantVicinity;
    }

    @Nullable
    public String getChosenRestaurantId() {
        return chosenRestaurantId;
    }

    @Nullable
    public String getChosenRestaurantName() {
        return chosenRestaurantName;
    }

    @Nullable
    public String getChosenRestaurantVicinity() {
        return chosenRestaurantVicinity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChosenRestaurantEntity that = (ChosenRestaurantEntity) o;
        return Objects.equals(chosenRestaurantId, that.chosenRestaurantId) && Objects.equals(chosenRestaurantName, that.chosenRestaurantName) && Objects.equals(chosenRestaurantVicinity, that.chosenRestaurantVicinity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chosenRestaurantId, chosenRestaurantName, chosenRestaurantVicinity);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChosenRestaurantEntity{" +
            "chosenRestaurantId='" + chosenRestaurantId + '\'' +
            ", chosenRestaurantName='" + chosenRestaurantName + '\'' +
            ", chosenRestaurantVicinity='" + chosenRestaurantVicinity + '\'' +
            '}';
    }
}
