package com.emplk.go4lunch.data.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class SendMessageDto {

    @Nullable
    private final String recipientId;

    @Nullable
    private final String recipientName;

    @Nullable
    private final String message;

    @ServerTimestamp
    private final Timestamp timestamp;

    public SendMessageDto() {
        this(null, null, null, null);
    }

    public SendMessageDto(
        @Nullable String recipientId,
        @Nullable String recipientName,
        @Nullable String message,
        @Nullable Timestamp timestamp
    ) {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.message = message;
        this.timestamp = timestamp;
    }

    @Nullable
    public String getRecipientId() {
        return recipientId;
    }

    @Nullable
    public String getRecipientName() {
        return recipientName;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @ServerTimestamp
    public Timestamp getTimestamp() {
        return timestamp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageDto that = (SendMessageDto) o;
        return Objects.equals(recipientId, that.recipientId) && Objects.equals(recipientName, that.recipientName) && Objects.equals(message, that.message) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientId, recipientName, message, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageDto{" +
            "recipientId='" + recipientId + '\'' +
            ", recipientName='" + recipientName + '\'' +
            ", message='" + message + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
