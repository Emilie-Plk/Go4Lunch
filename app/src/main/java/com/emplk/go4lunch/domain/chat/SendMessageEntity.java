package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class SendMessageEntity {
    @NonNull
    private final String recipientId;

    @NonNull
    private final String recipientName;

    @NonNull
    private final String message;

    @NonNull
    @ServerTimestamp
    private final Timestamp timestamp;

    public SendMessageEntity(
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String message,
        @NonNull Timestamp timestamp
    ) {
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.message = message;
        this.timestamp = timestamp;
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

    @NonNull
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageEntity that = (SendMessageEntity) o;
        return recipientId.equals(that.recipientId) && recipientName.equals(that.recipientName) && message.equals(that.message) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recipientId, recipientName, message, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageEntity{" +
            "recipientId='" + recipientId + '\'' +
            ", recipientName='" + recipientName + '\'' +
            ", message='" + message + '\'' +
            ", timestamp='" + timestamp + '\'' +
            '}';
    }
}
