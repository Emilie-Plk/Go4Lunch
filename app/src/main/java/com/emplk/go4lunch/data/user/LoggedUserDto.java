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
    private final String picture_url;

    public LoggedUserDto() {
        this(null, null, null, null);
    }

    public LoggedUserDto(
        @Nullable String id,
        @Nullable String name,
        @Nullable String email,
        @Nullable String picture_url
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.picture_url = picture_url;
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
    public String getPicture_url() {
        return picture_url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoggedUserDto that = (LoggedUserDto) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(email, that.email) && Objects.equals(picture_url, that.picture_url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, picture_url);
    }

    @NonNull
    @Override
    public String toString() {
        return "LoggedUserDto{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", picture_url='" + picture_url + '\'' +
            '}';
    }
}
