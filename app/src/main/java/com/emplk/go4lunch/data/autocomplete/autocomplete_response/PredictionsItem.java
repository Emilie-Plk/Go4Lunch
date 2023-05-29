package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class PredictionsItem {
    @Nullable
    @SerializedName("reference")
    private final String reference;
    @Nullable
    @SerializedName("types")
    private final List<String> types;
    @Nullable
    @SerializedName("matched_substrings")
    private final List<MatchedSubstringsItem> matchedSubstrings;
    @Nullable
    @SerializedName("terms")
    private final List<TermsItem> terms;
    @Nullable
    @SerializedName("structured_formatting")
    private final StructuredFormatting structuredFormatting;
    @Nullable
    @SerializedName("description")
    private final String description;
    @Nullable
    @SerializedName("place_id")
    private final String placeId;


    public PredictionsItem(
        @Nullable String reference,
        @Nullable List<String> types,
        @Nullable List<MatchedSubstringsItem> matchedSubstrings,
        @Nullable List<TermsItem> terms,
        @Nullable StructuredFormatting structuredFormatting,
        @Nullable String description,
        @Nullable String placeId
    ) {
        this.reference = reference;
        this.types = types;
        this.matchedSubstrings = matchedSubstrings;
        this.terms = terms;
        this.structuredFormatting = structuredFormatting;
        this.description = description;
        this.placeId = placeId;
    }

    @Nullable
    public String getReference() {
        return reference;
    }

    @Nullable
    public List<String> getTypes() {
        return types;
    }

    @Nullable
    public List<MatchedSubstringsItem> getMatchedSubstrings() {
        return matchedSubstrings;
    }

    @Nullable
    public List<TermsItem> getTerms() {
        return terms;
    }

    @Nullable
    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @Nullable
    public String getPlaceId() {
        return placeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PredictionsItem that = (PredictionsItem) o;
        return Objects.equals(reference, that.reference) && Objects.equals(types, that.types) && Objects.equals(matchedSubstrings, that.matchedSubstrings) && Objects.equals(terms, that.terms) && Objects.equals(structuredFormatting, that.structuredFormatting) && Objects.equals(description, that.description) && Objects.equals(placeId, that.placeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, types, matchedSubstrings, terms, structuredFormatting, description, placeId);
    }

    @NonNull
    @Override
    public String toString() {
        return "PredictionsItem{" +
            "reference='" + reference + '\'' +
            ", types=" + types +
            ", matchedSubstrings=" + matchedSubstrings +
            ", terms=" + terms +
            ", structuredFormatting=" + structuredFormatting +
            ", description='" + description + '\'' +
            ", placeId='" + placeId + '\'' +
            '}';
    }
}