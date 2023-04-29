package com.emplk.go4lunch.data.details.detailsRestaurantResponse;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {
    // TODO: cleanup a bit my Responses fields (some are useless)
    @SerializedName("utc_offset")
    private final Integer utcOffset;

    @SerializedName("formatted_address")
    private final String formattedAddress;

    @SerializedName("types")
    private final List<String> types;

    @SerializedName("website")
    private final String website;

    @SerializedName("business_status")
    private final String businessStatus;

    @SerializedName("icon")
    private final String icon;

    @SerializedName("rating")
    private final Integer rating;

    @SerializedName("icon_background_color")
    private final String iconBackgroundColor;

    @SerializedName("address_components")
    private final List<AddressComponentsItem> addressComponents;

    @SerializedName("photos")
    private final List<PhotosItem> photos;

    @SerializedName("url")
    private final String url;

    @SerializedName("reference")
    private final String reference;

    @SerializedName("user_ratings_total")
    private final Integer userRatingsTotal;

    @SerializedName("reviews")
    private final List<ReviewsItem> reviews;

    @SerializedName("name")
    private final String name;

    @SerializedName("opening_hours")
    private final OpeningHours openingHours;

    @SerializedName("geometry")
    private final Geometry geometry;

    @SerializedName("icon_mask_base_uri")
    private final String iconMaskBaseUri;

    @SerializedName("vicinity")
    private final String vicinity;

    @SerializedName("adr_address")
    private final String adrAddress;

    @SerializedName("plus_code")
    private final PlusCode plusCode;

    @SerializedName("formatted_phone_number")
    private final String formattedPhoneNumber;

    @SerializedName("international_phone_number")
    private final String internationalPhoneNumber;

    @SerializedName("place_id")
    private final String placeId;

    public Result(
        Integer utcOffset,
        String formattedAddress,
        List<String> types,
        String website,
        String businessStatus,
        String icon,
        Integer rating,
        String iconBackgroundColor,
        List<AddressComponentsItem> addressComponents,
        List<PhotosItem> photos,
        String url,
        String reference,
        Integer userRatingsTotal,
        List<ReviewsItem> reviews,
        String name,
        OpeningHours openingHours,
        Geometry geometry,
        String iconMaskBaseUri,
        String vicinity,
        String adrAddress,
        PlusCode plusCode,
        String formattedPhoneNumber,
        String internationalPhoneNumber,
        String placeId
    ) {
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
    }

    public Integer getUtcOffset() {
        return utcOffset;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public List<String> getTypes() {
        return types;
    }

    public String getWebsite() {
        return website;
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public String getIcon() {
        return icon;
    }

    public Integer getRating() {
        return rating;
    }

    public String getIconBackgroundColor() {
        return iconBackgroundColor;
    }

    public List<AddressComponentsItem> getAddressComponents() {
        return addressComponents;
    }

    public List<PhotosItem> getPhotos() {
        return photos;
    }

    public String getUrl() {
        return url;
    }

    public String getReference() {
        return reference;
    }

    public Integer getUserRatingsTotal() {
        return userRatingsTotal;
    }

    public List<ReviewsItem> getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public String getIconMaskBaseUri() {
        return iconMaskBaseUri;
    }

    public String getVicinity() {
        return vicinity;
    }

    public String getAdrAddress() {
        return adrAddress;
    }

    public PlusCode getPlusCode() {
        return plusCode;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public String getPlaceId() {
        return placeId;
    }

    @NonNull
    @Override
    public String toString() {
        return
            "Result{" +
                "utc_offset = '" + utcOffset + '\'' +
                ",formatted_address = '" + formattedAddress + '\'' +
                ",types = '" + types + '\'' +
                ",website = '" + website + '\'' +
                ",business_status = '" + businessStatus + '\'' +
                ",icon = '" + icon + '\'' +
                ",rating = '" + rating + '\'' +
                ",icon_background_color = '" + iconBackgroundColor + '\'' +
                ",address_components = '" + addressComponents + '\'' +
                ",photos = '" + photos + '\'' +
                ",url = '" + url + '\'' +
                ",reference = '" + reference + '\'' +
                ",user_ratings_total = '" + userRatingsTotal + '\'' +
                ",reviews = '" + reviews + '\'' +
                ",name = '" + name + '\'' +
                ",opening_hours = '" + openingHours + '\'' +
                ",geometry = '" + geometry + '\'' +
                ",icon_mask_base_uri = '" + iconMaskBaseUri + '\'' +
                ",vicinity = '" + vicinity + '\'' +
                ",adr_address = '" + adrAddress + '\'' +
                ",plus_code = '" + plusCode + '\'' +
                ",formatted_phone_number = '" + formattedPhoneNumber + '\'' +
                ",international_phone_number = '" + internationalPhoneNumber + '\'' +
                ",place_id = '" + placeId + '\'' +
                "}";
    }
}