package com.emplk.go4lunch.data.auth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public class FirebaseUserEntity {

    @NonNull
    private final String uid;

    @NonNull
    private final String email;

    @NonNull
    private final String displayName;

    @Nullable
    private final String photoUrl;

    public FirebaseUserEntity(
        @NonNull String uid,
        @NonNull String email,
        @NonNull String displayName,
        @Nullable String photoUrl
    ) {
        this.uid = uid;
        this.email = email;
        this.displayName = displayName;
        this.photoUrl = photoUrl;
    }

    @NonNull
    public String getUid() {
        return uid;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    @NonNull
    public String getDisplayName() {
        return displayName;
    }

    @Nullable
    public String getPhotoUrl() {
        return photoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirebaseUserEntity that = (FirebaseUserEntity) o;
        return uid.equals(that.uid) && email.equals(that.email) && displayName.equals(that.displayName) && Objects.equals(photoUrl, that.photoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, email, displayName, photoUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "FirebaseUserEntity{" +
            "uid='" + uid + '\'' +
            ", email='" + email + '\'' +
            ", displayName='" + displayName + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            '}';
    }
}
