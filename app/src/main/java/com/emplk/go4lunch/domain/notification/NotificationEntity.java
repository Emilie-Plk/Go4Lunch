package com.emplk.go4lunch.domain.notification;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class NotificationEntity {

    private final String restaurantName;

    private final String restaurantId;

    private final String restaurantVicinity;

    private final List<String> workmates;

    public NotificationEntity(
        String restaurantName,
        String restaurantId,
        String restaurantVicinity,
        List<String> workmates
    ) {
        this.restaurantName = restaurantName;
        this.restaurantId = restaurantId;
        this.restaurantVicinity = restaurantVicinity;
        this.workmates = workmates;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getRestaurantVicinity() {
        return restaurantVicinity;
    }

    public List<String> getWorkmates() {
        return workmates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationEntity that = (NotificationEntity) o;
        return Objects.equals(restaurantName, that.restaurantName) && Objects.equals(restaurantId, that.restaurantId) && Objects.equals(restaurantVicinity, that.restaurantVicinity) && Objects.equals(workmates, that.workmates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantName, restaurantId, restaurantVicinity, workmates);
    }

    @NonNull
    @Override
    public String toString() {
        return "NotificationEntity{" +
            "restaurantName='" + restaurantName + '\'' +
            ", restaurantId='" + restaurantId + '\'' +
            ", restaurantVicinity='" + restaurantVicinity + '\'' +
            ", workmates=" + workmates +
            '}';
    }
}
