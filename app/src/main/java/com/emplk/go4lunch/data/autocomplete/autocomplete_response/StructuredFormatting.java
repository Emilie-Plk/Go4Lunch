package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class StructuredFormatting {
    @Nullable
    @SerializedName("main_text_matched_substrings")
    private final List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings;
    @Nullable
    @SerializedName("secondary_text")
    private final String secondaryText;
    @Nullable
    @SerializedName("main_text")
    private final String mainText;

    public StructuredFormatting(
        @Nullable List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings,
        @Nullable String secondaryText,
        @Nullable String mainText
    ) {
        this.mainTextMatchedSubstrings = mainTextMatchedSubstrings;
        this.secondaryText = secondaryText;
        this.mainText = mainText;
    }

    @Nullable
    public List<MainTextMatchedSubstringsItem> getMainTextMatchedSubstrings() {
        return mainTextMatchedSubstrings;
    }

    @Nullable
    public String getSecondaryText() {
        return secondaryText;
    }

    @Nullable
    public String getMainText() {
        return mainText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructuredFormatting that = (StructuredFormatting) o;
        return Objects.equals(mainTextMatchedSubstrings, that.mainTextMatchedSubstrings) && Objects.equals(secondaryText, that.secondaryText) && Objects.equals(mainText, that.mainText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mainTextMatchedSubstrings, secondaryText, mainText);
    }

    @NonNull
    @Override
    public String toString() {
        return
            "StructuredFormatting{" +
                "main_text_matched_substrings = '" + mainTextMatchedSubstrings + '\'' +
                ",secondary_text = '" + secondaryText + '\'' +
                ",main_text = '" + mainText + '\'' +
                "}";
    }
}