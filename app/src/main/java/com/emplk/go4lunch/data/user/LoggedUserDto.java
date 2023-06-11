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

<<<<<<< HEAD:app/src/main/java/com/emplk/go4lunch/data/user/LoggedUserDto.java

    public LoggedUserDto() {
=======
    public UserDto() {
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283:app/src/main/java/com/emplk/go4lunch/data/user/UserDto.java
        id = null;
        name = null;
        email = null;
        pictureUrl = null;
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

<<<<<<< HEAD:app/src/main/java/com/emplk/go4lunch/data/user/LoggedUserDto.java

=======
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283:app/src/main/java/com/emplk/go4lunch/data/user/UserDto.java
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
<<<<<<< HEAD:app/src/main/java/com/emplk/go4lunch/data/user/LoggedUserDto.java
        LoggedUserDto loggedUserDto = (LoggedUserDto) o;
        return Objects.equals(id, loggedUserDto.id) && Objects.equals(name, loggedUserDto.name) && Objects.equals(email, loggedUserDto.email) && Objects.equals(pictureUrl, loggedUserDto.pictureUrl);
=======
        UserDto userDto = (UserDto) o;
        return Objects.equals(id, userDto.id) && Objects.equals(name, userDto.name) && Objects.equals(email, userDto.email) && Objects.equals(pictureUrl, userDto.pictureUrl);
>>>>>>> 05ad6ff11891ef69d3653037b199421a96f94283:app/src/main/java/com/emplk/go4lunch/data/user/UserDto.java
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
