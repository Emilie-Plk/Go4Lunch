package com.emplk.go4lunch.data.autocomplete.autocomplete_response;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class MatchedSubstringsItem {
    @Nullable
	@SerializedName("offset")
    private final Integer offset;
    @Nullable
	@SerializedName("length")
    private final Integer length;

    public MatchedSubstringsItem(
        @Nullable Integer offset,
        @Nullable Integer length
    ) {
        this.offset = offset;
        this.length = length;
    }

    @Nullable
    public Integer getOffset() {
        return offset;
    }

    @Nullable
    public Integer getLength() {
        return length;
    }

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		MatchedSubstringsItem that = (MatchedSubstringsItem) o;
		return Objects.equals(offset, that.offset) && Objects.equals(length, that.length);
	}

	@Override
	public int hashCode() {
		return Objects.hash(offset, length);
	}

	@NonNull
	@Override
	public String toString() {
		return "MatchedSubstringsItem{" +
			"offset=" + offset +
			", length=" + length +
			'}';
	}
}
