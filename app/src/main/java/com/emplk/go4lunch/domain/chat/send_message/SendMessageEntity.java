package com.emplk.go4lunch.domain.chat.send_message;

import androidx.annotation.NonNull;

import java.util.Objects;

public class SendMessageEntity {
    @NonNull
    private final String recipientId;

    @NonNull
    private final String recipientName;

    @NonNull
    private final String recipientPhotoUrl;

    @NonNull
    private final String message;

    public SendMessageEntity(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String recipientPhotoUrl,
        @NonNull String message
    ) {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientPhotoUrl = recipientPhotoUrl;
        this.message = message;
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

    @NonNull
    public String getMessage() {
        return message;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageEntity that = (SendMessageEntity) o;
        return recipientId.equals(that.recipientId) && recipientName.equals(that.recipientName) && recipientPhotoUrl.equals(that.recipientPhotoUrl) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientId, recipientName, recipientPhotoUrl, message);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageEntity{" +
            "recipientId='" + recipientId + '\'' +
            ", recipientName='" + recipientName + '\'' +
            ", recipientPhotoUrl='" + recipientPhotoUrl + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
