package com.emplk.go4lunch.data.workmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class WorkmateDto {
    @Nullable
    private final String id;
    @Nullable
    private final String name;

    @Nullable
    private final String email;

    @Nullable
    private final String pictureUrl;

    // Empty constructor for Firestore serialization
    public WorkmateDto() {
        id = null;
        name = null;
        email = null;
        pictureUrl = null;
    }

    public WorkmateDto(
        @Nullable String id,
        @Nullable String name,
        @Nullable String email,
        @Nullable String pictureUrl
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.pictureUrl = pictureUrl;
    }

    @Nullable
    public String getId() {
        return id;
    }


    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public String getEmail() {
        return email;
    }

    @Nullable
    public String getPictureUrl() {
        return pictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkmateDto that = (WorkmateDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(pictureUrl, that.pictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, pictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "WorkmateDto{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            '}';
    }
}
