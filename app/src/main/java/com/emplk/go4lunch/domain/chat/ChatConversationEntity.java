package com.emplk.go4lunch.domain.chat;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.List;
import java.util.Objects;

public class ChatConversationEntity {

    @NonNull
    private final String userId;

    @NonNull
    private final String userName;

    @NonNull
    private final List<String> messages;

    @NonNull
    @ServerTimestamp
    private final List<String> timeStamps;

    public ChatConversationEntity(
        @NonNull String userId,
        @NonNull String userName,
        @NonNull List<String> messages,
        @NonNull List<String> timeStamps
    ) {
        this.userId = userId;
        this.userName = userName;
        this.messages = messages;
        this.timeStamps = timeStamps;
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
    public List<String> getMessages() {
        return messages;
    }

    @NonNull
    public List<String> getTimeStamps() {
        return timeStamps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatConversationEntity that = (ChatConversationEntity) o;
        return userId.equals(that.userId) && userName.equals(that.userName) && messages.equals(that.messages) && timeStamps.equals(that.timeStamps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, messages, timeStamps);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatConversationEntity{" +
            "userId='" + userId + '\'' +
            ", userName='" + userName + '\'' +
            ", messages=" + messages +
            ", timeStamps='" + timeStamps + '\'' +
            '}';
    }
}
