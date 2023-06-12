package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class TermsItem {
    @Nullable
    @SerializedName("offset")
    private final Integer offset;
    @Nullable
    @SerializedName("value")
    private final String value;

    public TermsItem(
        @Nullable Integer offset,
        @Nullable String value
    ) {
        this.offset = offset;
        this.value = value;
    }

    @Nullable
    public Integer getOffset() {
        return offset;
    }

    @Nullable
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermsItem termsItem = (TermsItem) o;
        return Objects.equals(offset, termsItem.offset) && Objects.equals(value, termsItem.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(offset, value);
    }

    @NonNull
    @Override
    public String toString() {
        return "TermsItem{" +
            "offset=" + offset +
            ", value='" + value + '\'' +
            '}';
    }
}
