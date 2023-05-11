package com.emplk.go4lunch.data.details.details_restaurant_response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class Result {
    // TODO: cleanup a bit my Responses fields (some are useless)
    @SerializedName("utc_offset")
    @Nullable
    private final Integer utcOffset;

    @SerializedName("formatted_address")
    @Nullable
    private final String formattedAddress;

    @SerializedName("types")
    @Nullable
    private final List<String> types;

    @SerializedName("website")
    @Nullable
    private final String website;

    @SerializedName("business_status")
    @Nullable
    private final String businessStatus;

    @SerializedName("icon")
    @Nullable
    private final String icon;

    @SerializedName("rating")
    @Nullable
    private final Float rating;

    @SerializedName("icon_background_color")
    @Nullable
    private final String iconBackgroundColor;

    @SerializedName("address_components")
    @Nullable
    private final List<AddressComponentsItem> addressComponents;

    @SerializedName("photos")
    @Nullable
    private final List<PhotosItem> photos;

    @SerializedName("url")
    @Nullable
    private final String url;

    @SerializedName("reference")
    @Nullable
    private final String reference;

    @SerializedName("user_ratings_total")
    @Nullable
    private final Integer userRatingsTotal;

    @SerializedName("reviews")
    @Nullable
    private final List<ReviewsItem> reviews;

    @SerializedName("name")
    @Nullable
    private final String name;

    @SerializedName("opening_hours")
    @Nullable
    private final OpeningHours openingHours;

    @SerializedName("geometry")
    @Nullable
    private final Geometry geometry;

    @SerializedName("icon_mask_base_uri")
    @Nullable
    private final String iconMaskBaseUri;

    @SerializedName("vicinity")
    @Nullable
    private final String vicinity;

    @SerializedName("adr_address")
    @Nullable
    private final String adrAddress;

    @SerializedName("plus_code")
    @Nullable
    private final PlusCode plusCode;

    @SerializedName("formatted_phone_number")
    @Nullable
    private final String formattedPhoneNumber;

    @SerializedName("international_phone_number")
    @Nullable
    private final String internationalPhoneNumber;

    @SerializedName("place_id")
    @Nullable
    private final String placeId;
    @SerializedName("serves_vegetarian_food")
    @Nullable
    private final Boolean servesVegetarianFood;

    public Result(
        @Nullable Integer utcOffset,
        @Nullable String formattedAddress,
        @Nullable List<String> types,
        @Nullable String website,
        @Nullable String businessStatus,
        @Nullable String icon,
        @Nullable Float rating,
        @Nullable String iconBackgroundColor,
        @Nullable List<AddressComponentsItem> addressComponents,
        @Nullable List<PhotosItem> photos,
        @Nullable String url,
        @Nullable String reference,
        @Nullable Integer userRatingsTotal,
        @Nullable List<ReviewsItem> reviews,
        @Nullable String name,
        @Nullable OpeningHours openingHours,
        @Nullable Geometry geometry,
        @Nullable String iconMaskBaseUri,
        @Nullable String vicinity,
        @Nullable String adrAddress,
        @Nullable PlusCode plusCode,
        @Nullable String formattedPhoneNumber,
        @Nullable String internationalPhoneNumber,
        @Nullable String placeId,
        @Nullable Boolean servesVegetarianFood) {
        this.utcOffset = utcOffset;
        this.formattedAddress = formattedAddress;
        this.types = types;
        this.website = website;
        this.businessStatus = businessStatus;
        this.icon = icon;
        this.rating = rating;
        this.iconBackgroundColor = iconBackgroundColor;
        this.addressComponents = addressComponents;
        this.photos = photos;
        this.url = url;
        this.reference = reference;
        this.userRatingsTotal = userRatingsTotal;
        this.reviews = reviews;
        this.name = name;
        this.openingHours = openingHours;
        this.geometry = geometry;
        this.iconMaskBaseUri = iconMaskBaseUri;
        this.vicinity = vicinity;
        this.adrAddress = adrAddress;
        this.plusCode = plusCode;
        this.formattedPhoneNumber = formattedPhoneNumber;
        this.internationalPhoneNumber = internationalPhoneNumber;
        this.placeId = placeId;
        this.servesVegetarianFood = servesVegetarianFood;
    }

    @Nullable
    public Integer getUtcOffset() {
        return utcOffset;
    }

    @Nullable
    public String getFormattedAddress() {
        return formattedAddress;
    }

    @Nullable
    public List<String> getTypes() {
        return types;
    }

    @Nullable
    public String getWebsite() {
        return website;
    }

    @Nullable
    public String getBusinessStatus() {
        return businessStatus;
    }

    @Nullable
    public String getIcon() {
        return icon;
    }

    @Nullable
    public Float getRating() {
        return rating;
    }

    @Nullable
    public String getIconBackgroundColor() {
        return iconBackgroundColor;
    }

    @Nullable
    public List<AddressComponentsItem> getAddressComponents() {
        return addressComponents;
    }

    @Nullable
    public List<PhotosItem> getPhotos() {
        return photos;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    @Nullable
    public String getReference() {
        return reference;
    }

    @Nullable
    public Integer getUserRatingsTotal() {
        return userRatingsTotal;
    }

    @Nullable
    public List<ReviewsItem> getReviews() {
        return reviews;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @Nullable
    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    @Nullable
    public Geometry getGeometry() {
        return geometry;
    }

    @Nullable
    public String getIconMaskBaseUri() {
        return iconMaskBaseUri;
    }

    @Nullable
    public String getVicinity() {
        return vicinity;
    }

    @Nullable
    public String getAdrAddress() {
        return adrAddress;
    }

    @Nullable
    public PlusCode getPlusCode() {
        return plusCode;
    }

    @Nullable
    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    @Nullable
    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    @Nullable
    public String getPlaceId() {
        return placeId;
    }

    @Nullable
    public Boolean isServesVegetarianFood() {
        return servesVegetarianFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Result result = (Result) o;
        return Objects.equals(utcOffset, result.utcOffset) && Objects.equals(formattedAddress, result.formattedAddress) && Objects.equals(types, result.types) && Objects.equals(website, result.website) && Objects.equals(businessStatus, result.businessStatus) && Objects.equals(icon, result.icon) && Objects.equals(rating, result.rating) && Objects.equals(iconBackgroundColor, result.iconBackgroundColor) && Objects.equals(addressComponents, result.addressComponents) && Objects.equals(photos, result.photos) && Objects.equals(url, result.url) && Objects.equals(reference, result.reference) && Objects.equals(userRatingsTotal, result.userRatingsTotal) && Objects.equals(reviews, result.reviews) && Objects.equals(name, result.name) && Objects.equals(openingHours, result.openingHours) && Objects.equals(geometry, result.geometry) && Objects.equals(iconMaskBaseUri, result.iconMaskBaseUri) && Objects.equals(vicinity, result.vicinity) && Objects.equals(adrAddress, result.adrAddress) && Objects.equals(plusCode, result.plusCode) && Objects.equals(formattedPhoneNumber, result.formattedPhoneNumber) && Objects.equals(internationalPhoneNumber, result.internationalPhoneNumber) && Objects.equals(placeId, result.placeId) && Objects.equals(servesVegetarianFood, result.servesVegetarianFood);
    }

    @Override
    public int hashCode() {
        return Objects.hash(utcOffset, formattedAddress, types, website, businessStatus, icon, rating, iconBackgroundColor, addressComponents, photos, url, reference, userRatingsTotal, reviews, name, openingHours, geometry, iconMaskBaseUri, vicinity, adrAddress, plusCode, formattedPhoneNumber, internationalPhoneNumber, placeId, servesVegetarianFood);
    }

    @NonNull
    @Override
    public String toString() {
        return "Result{" +
            "utcOffset=" + utcOffset +
            ", formattedAddress='" + formattedAddress + '\'' +
            ", types=" + types +
            ", website='" + website + '\'' +
            ", businessStatus='" + businessStatus + '\'' +
            ", icon='" + icon + '\'' +
            ", rating=" + rating +
            ", iconBackgroundColor='" + iconBackgroundColor + '\'' +
            ", addressComponents=" + addressComponents +
            ", photos=" + photos +
            ", url='" + url + '\'' +
            ", reference='" + reference + '\'' +
            ", userRatingsTotal=" + userRatingsTotal +
            ", reviews=" + reviews +
            ", name='" + name + '\'' +
            ", openingHours=" + openingHours +
            ", geometry=" + geometry +
            ", iconMaskBaseUri='" + iconMaskBaseUri + '\'' +
            ", vicinity='" + vicinity + '\'' +
            ", adrAddress='" + adrAddress + '\'' +
            ", plusCode=" + plusCode +
            ", formattedPhoneNumber='" + formattedPhoneNumber + '\'' +
            ", internationalPhoneNumber='" + internationalPhoneNumber + '\'' +
            ", placeId='" + placeId + '\'' +
            ", servesVegetarianFood=" + servesVegetarianFood +
            '}';
    }
}