package com.emplk.go4lunch.domain.chat.last_message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class LastChatMessageDto {

    @Nullable
    private final String lastMessage;

    @Nullable
    private final Timestamp timestamp;

    public LastChatMessageDto() {
        this(null, null);
    }

    public LastChatMessageDto(
        @Nullable String lastMessage,
        @Nullable Timestamp timestamp
    ) {
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    @Nullable
    public String getLastMessage() {
        return lastMessage;
    }

    @Nullable
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastChatMessageDto that = (LastChatMessageDto) o;
        return Objects.equals(lastMessage, that.lastMessage) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastMessage, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "LastChatMessageDto{" +
            ", lastMessage='" + lastMessage + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
