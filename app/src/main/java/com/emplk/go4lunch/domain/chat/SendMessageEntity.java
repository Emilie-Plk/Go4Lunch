package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import java.util.Objects;

public class SendMessageEntity {
    @NonNull
    private final String recipientId;

    @NonNull
    private final String recipientName;

    @NonNull
    private final String message;

    public SendMessageEntity(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String message
    ) {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
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
    public String getMessage() {
        return message;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageEntity that = (SendMessageEntity) o;
        return recipientId.equals(that.recipientId) && recipientName.equals(that.recipientName) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientId, recipientName, message);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageEntity{" +
            "recipientId='" + recipientId + '\'' +
            ", recipientName='" + recipientName + '\'' +
            ", message='" + message + '\'' +
            '}';
    }
}
