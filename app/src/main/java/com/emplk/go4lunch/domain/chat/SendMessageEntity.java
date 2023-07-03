package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class SendMessageEntity {
    @NonNull
    private final String receiverId;

    @NonNull
    private final String receiverName;

    @NonNull
    private final String message;

    @NonNull
    @ServerTimestamp
    private final Timestamp timeStamp;

    public SendMessageEntity(
        @NonNull String receiverId,
        @NonNull String receiverName,
        @NonNull String message,
        @NonNull Timestamp timeStamp
    ) {
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    @NonNull
    public String getReceiverId() {
        return receiverId;
    }

    @NonNull
    public String getReceiverName() {
        return receiverName;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    @NonNull
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageEntity that = (SendMessageEntity) o;
        return receiverId.equals(that.receiverId) && receiverName.equals(that.receiverName) && message.equals(that.message) && timeStamp.equals(that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiverId, receiverName, message, timeStamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageEntity{" +
            "receiverId='" + receiverId + '\'' +
            ", receiverName='" + receiverName + '\'' +
            ", message='" + message + '\'' +
            ", timeStamp='" + timeStamp + '\'' +
            '}';
    }
}
