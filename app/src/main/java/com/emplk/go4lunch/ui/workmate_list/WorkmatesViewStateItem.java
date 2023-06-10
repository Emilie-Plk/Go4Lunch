package com.emplk.go4lunch.ui.workmate_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class WorkmatesViewStateItem {

    @NonNull
    private final String id;

    @NonNull
    private final String name;

    @Nullable
    private final String photoUrl;

    @Nullable
    private final String attendingRestaurantId;

    @Nullable
    private final String attendingRestaurantName;

    public WorkmatesViewStateItem(
        @NonNull String id,
        @NonNull String name,
        @Nullable String photoUrl,
        @Nullable String attendingRestaurantId,
        @Nullable String attendingRestaurantName
    ) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.attendingRestaurantId = attendingRestaurantId;
        this.attendingRestaurantName = attendingRestaurantName;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @Nullable
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Nullable
    public String getAttendingRestaurantId() {
        return attendingRestaurantId;
    }

    @Nullable
    public String getAttendingRestaurantName() {
        return attendingRestaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmatesViewStateItem that = (WorkmatesViewStateItem) o;
        return id.equals(that.id) && name.equals(that.name) && Objects.equals(photoUrl, that.photoUrl) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId) && Objects.equals(attendingRestaurantName, that.attendingRestaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, photoUrl, attendingRestaurantId, attendingRestaurantName);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmatesViewStateItem{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
            ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
            '}';
    }
}
