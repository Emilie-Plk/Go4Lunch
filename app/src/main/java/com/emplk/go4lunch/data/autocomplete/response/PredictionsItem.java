package com.emplk.go4lunch.data.autocomplete.response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PredictionsItem {
    @Nullable
    @SerializedName("reference")
    private String reference;
    @Nullable
    @SerializedName("types")
    private List<String> types;
    @Nullable
    @SerializedName("matched_substrings")
    private List<MatchedSubstringsItem> matchedSubstrings;
    @Nullable
    @SerializedName("terms")
    private List<TermsItem> terms;
    @Nullable
    @SerializedName("structured_formatting")
    private StructuredFormatting structuredFormatting;
    @Nullable
    @SerializedName("description")
    private String description;
    @Nullable
    @SerializedName("place_id")
    private String placeId;

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

    @NonNull
    @Override
    public String toString() {
        return
            "PredictionsItem{" +
                "reference = '" + reference + '\'' +
                ",types = '" + types + '\'' +
                ",matched_substrings = '" + matchedSubstrings + '\'' +
                ",terms = '" + terms + '\'' +
                ",structured_formatting = '" + structuredFormatting + '\'' +
                ",description = '" + description + '\'' +
                ",place_id = '" + placeId + '\'' +
                "}";
    }
}