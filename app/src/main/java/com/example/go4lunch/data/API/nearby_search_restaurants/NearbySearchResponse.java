package com.example.go4lunch.data.API.nearby_search_restaurants;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

public class NearbySearchResponse {
    private List<Result> results;
    private String status;

    public List<Result> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }

    public static class Result {
        private String name;
        private List<String> types;
        private String vicinity;
        private OpeningHours opening_hours;
        private Geometry geometry;
        private double distance;
        private List<Photo> photos;

        public String getName() {
            return name;
        }

        public List<String> getTypes() {
            return types;
        }

        public String getVicinity() {
            return vicinity;
        }

        public OpeningHours getOpeningHours() {
            return opening_hours;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public double getDistance() {
            return distance;
        }

        public List<Photo> getPhotos() {
            return photos;
        }
    }

    public static class OpeningHours {
        private boolean open_now;

        public boolean isOpenNow() {
            return open_now;
        }
    }

    public static class Geometry {
        private Location location;

        public Location getLocation() {
            return location;
        }
    }

    public static class Location {
        private double lat;
        private double lng;

        public double getLat() {
            return lat;
        }

        public double getLng() {
            return lng;
        }
    }

    public static class Photo {
        private int height;
        private List<String> html_attributions;
        private String photo_reference;
        private int width;

        public int getHeight() {
            return height;
        }

        public List<String> getHtmlAttributions() {
            return html_attributions;
        }

        public String getPhotoReference() {
            return photo_reference;
        }

        public int getWidth() {
            return width;
        }
    }

    @NonNull
    @Override
    public String toString() {
        return "NearbySearchResponse{" +
            "results=" + results +
            ", status='" + status + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NearbySearchResponse that = (NearbySearchResponse) o;
        return Objects.equals(results, that.results) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(results, status);
    }
}

