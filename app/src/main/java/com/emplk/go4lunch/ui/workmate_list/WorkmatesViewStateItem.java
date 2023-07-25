package com.emplk.go4lunch.ui.workmate_list;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Objects;

public abstract class WorkmatesViewStateItem {

    public enum Type {
        WORKMATES_GOING_TO_SAME_RESTAURANT,
        ALL_WORKMATES,
        NO_WORKMATES
    }

    @NonNull
    protected final Type type;

    public WorkmatesViewStateItem(@NonNull Type type) {
        this.type = type;
    }

    @NonNull
    public Type getType() {
        return type;
    }

    @Override
    public abstract boolean equals(@Nullable Object obj);

    public static class AllWorkmates extends WorkmatesViewStateItem {

        @NonNull
        private final String id;

        @NonNull
        private final String name;

        @Nullable
        private final String pictureUrl;

        @Nullable
        private final String attendingRestaurantId;

        @Nullable
        private final String attendingRestaurantName;

        public AllWorkmates(
            @NonNull String id,
            @NonNull String name,
            @Nullable String pictureUrl,
            @Nullable String attendingRestaurantId,
            @Nullable String attendingRestaurantName
        ) {
            super(Type.ALL_WORKMATES);
            this.id = id;
            this.name = name;
            this.pictureUrl = pictureUrl;
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
        public String getPictureUrl() {
            return pictureUrl;
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
            AllWorkmates that = (AllWorkmates) o;
            return id.equals(that.id) && name.equals(that.name) && Objects.equals(pictureUrl, that.pictureUrl) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId) && Objects.equals(attendingRestaurantName, that.attendingRestaurantName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, pictureUrl, attendingRestaurantId, attendingRestaurantName);
        }

        @NonNull
        @Override
        public String toString() {
            return "AllWorkmates{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
                ", attendingRestaurantName='" + attendingRestaurantName + '\'' +
                '}';
        }
    }

    public static class WorkmatesGoingToSameRestaurant extends WorkmatesViewStateItem {
        @NonNull
        private final String id;

        @NonNull
        private final String name;

        @Nullable
        private final String pictureUrl;

        @Nullable
        private final String attendingRestaurantId;

        public WorkmatesGoingToSameRestaurant(
            @NonNull String id,
            @NonNull String name,
            @Nullable String pictureUrl,
            @Nullable String attendingRestaurantId
        ) {
            super(Type.WORKMATES_GOING_TO_SAME_RESTAURANT);
            this.id = id;
            this.name = name;
            this.pictureUrl = pictureUrl;
            this.attendingRestaurantId = attendingRestaurantId;
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
        public String getPictureUrl() {
            return pictureUrl;
        }

        @Nullable
        public String getAttendingRestaurantId() {
            return attendingRestaurantId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WorkmatesGoingToSameRestaurant that = (WorkmatesGoingToSameRestaurant) o;
            return id.equals(that.id) && name.equals(that.name) && Objects.equals(pictureUrl, that.pictureUrl) && Objects.equals(attendingRestaurantId, that.attendingRestaurantId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, pictureUrl, attendingRestaurantId);
        }

        @NonNull
        @Override
        public String toString() {
            return "WorkmatesGoingToSameRestaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", attendingRestaurantId='" + attendingRestaurantId + '\'' +
                '}';
        }
    }
}

