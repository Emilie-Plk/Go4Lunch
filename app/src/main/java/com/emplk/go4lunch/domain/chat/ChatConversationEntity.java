package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Objects;

public class ChatConversationEntity {

    @NonNull
    private final String userId;

    @NonNull
    private final String userName;

    @NonNull
    private final String message;

    @NonNull
    @ServerTimestamp
    private final Timestamp timestamp;

    public ChatConversationEntity(
        @NonNull String userId,
        @NonNull String userName,
        @NonNull String message,
        @NonNull Timestamp timestamp
    ) {
        this.userId = userId;
        this.userName = userName;
        this.message = message;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getUserName() {
        return userName;
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
        ChatConversationEntity that = (ChatConversationEntity) o;
        return userId.equals(that.userId) && userName.equals(that.userName) && message.equals(that.message) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, message, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatConversationEntity{" +
            "userId='" + userId + '\'' +
            ", userName='" + userName + '\'' +
            ", message=" + message +
            ", timestamp='" + timestamp + '\'' +
            '}';
    }
}
