package com.emplk.go4lunch.domain.chat.conversation;

import androidx.annotation.NonNull;

import java.util.Objects;

public class RecipientEntity {

    @NonNull
    private final String recipientId;

    @NonNull
    private final String recipientName;

    @NonNull
    private final String recipientPhotoUrl;

    public RecipientEntity(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String recipientPhotoUrl
    ) {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientPhotoUrl = recipientPhotoUrl;
    }

    @NonNull
    public String getRecipientId() {
        return recipientId;
    }

    @NonNull
    public String getRecipientName() {
        return recipientName;
    }

    @NonNull
    public String getRecipientPhotoUrl() {
        return recipientPhotoUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecipientEntity that = (RecipientEntity) o;
        return recipientId.equals(that.recipientId) && recipientName.equals(that.recipientName) && recipientPhotoUrl.equals(that.recipientPhotoUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientId, recipientName, recipientPhotoUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "SenderEntity{" +
            "recipientId='" + recipientId + '\'' +
            ", recipientName='" + recipientName + '\'' +
            ", recipientPhotoUrl='" + recipientPhotoUrl + '\'' +
            '}';
    }
}
