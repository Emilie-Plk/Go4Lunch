package com.emplk.go4lunch.domain.authentication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class LoggedUserEntity {

    @NonNull
    private final String userId;

    @NonNull
    private final String email;

    @NonNull
    private final String username;

    @Nullable
    private final String photoUrl;

    public LoggedUserEntity(
        @NonNull String userId,
        @NonNull String displayName,
        @NonNull String email,
        @Nullable String photoUrl
    ) {
        this.userId = userId;
        this.username = displayName;
        this.email = email;
        this.photoUrl = photoUrl;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoggedUserEntity that = (LoggedUserEntity) o;
        return userId.equals(that.userId) && email.equals(that.email) && username.equals(that.username) && Objects.equals(photoUrl, that.photoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, email, username, photoUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "LoggedUserEntity{" +
            "userId='" + userId + '\'' +
            ", email='" + email + '\'' +
            ", username='" + username + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            '}';
    }
}
