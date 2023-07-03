package com.emplk.go4lunch.data.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class SendMessageDto {

    @Nullable
    private final String receiverId;

    @Nullable
    private final String message;

    @Nullable
    @ServerTimestamp
    private final String timeStamp;

public SendMessageDto() {
        this(null, null, null);
    }

    public SendMessageDto(
        @Nullable String receiverId,
        @Nullable String message,
        @Nullable String timeStamp
    ) {
        this.receiverId = receiverId;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    @Nullable
    public String getReceiverId() {
        return receiverId;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public String getTimeStamp() {
        return timeStamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageDto that = (SendMessageDto) o;
        return  Objects.equals(receiverId, that.receiverId) && Objects.equals(message, that.message) && Objects.equals(timeStamp, that.timeStamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiverId, message, timeStamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageDto{" +
            ", receiverId='" + receiverId + '\'' +
            ", message='" + message + '\'' +
            ", timeStamp='" + timeStamp + '\'' +
            '}';
    }
}
