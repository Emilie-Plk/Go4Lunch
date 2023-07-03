package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class SendMessageEntity {
    @NonNull
    private final String receiverId;

    @NonNull
    private final String message;

    @NonNull
    @ServerTimestamp
    private final String timeStamp;

    public SendMessageEntity(
        @NonNull String receiverId,
        @NonNull String message,
        @NonNull String timeStamp
    ) {
        this.receiverId = receiverId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    @NonNull
    public String getReceiverId() {
        return receiverId;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageEntity that = (SendMessageEntity) o;
        return receiverId.equals(that.receiverId) && message.equals(that.message) && timeStamp.equals(that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiverId, message, timeStamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageEntity{" +
            "receiverId='" + receiverId + '\'' +
            ", message='" + message + '\'' +
            ", timeStamp='" + timeStamp + '\'' +
            '}';
    }
}
