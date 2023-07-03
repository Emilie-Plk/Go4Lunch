package com.emplk.go4lunch.data.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class SendMessageDto {

    @Nullable
    private final String receiverId;

    @Nullable
    private final String receiverName;

    @Nullable
    private final String message;

    @Nullable
    @ServerTimestamp
    private final Timestamp timeStamp;

    public SendMessageDto() {
        this(null, null, null, null);
    }

    public SendMessageDto(
        @Nullable String receiverId,
        @Nullable String receiverName,
        @Nullable String message,
        @Nullable Timestamp timeStamp
    ) {
        this.receiverId = receiverId;
        this.receiverName = receiverName;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    @Nullable
    public String getReceiverId() {
        return receiverId;
    }

    @Nullable
    public String getReceiverName() {
        return receiverName;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageDto that = (SendMessageDto) o;
        return Objects.equals(receiverId, that.receiverId) && Objects.equals(receiverName, that.receiverName) && Objects.equals(message, that.message) && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiverId, receiverName, message, timeStamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageDto{" +
            "receiverId='" + receiverId + '\'' +
            ", receiverName='" + receiverName + '\'' +
            ", message='" + message + '\'' +
            ", timeStamp=" + timeStamp +
            '}';
    }
}
