package com.emplk.go4lunch.data.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class LoggedUserDto {
    @Nullable
    private final String id;

    @Nullable
    private final String name;

    @Nullable
    private final String email;

    @Nullable
    private final String pictureUrl;

    public LoggedUserDto() {
        this(null, null, null, null);
    }

    public LoggedUserDto(
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
        LoggedUserDto that = (LoggedUserDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(pictureUrl, that.pictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, pictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "LoggedUserDto{" +
            "id='" + id + '\'' +
            ", username='" + name + '\'' +
            ", email='" + email + '\'' +
            ", pictureUrl='" + pictureUrl + '\'' +
            '}';
    }
}
