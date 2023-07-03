package com.emplk.go4lunch.data.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.List;
import java.util.Objects;

public class ChatConversationDto {

    @Nullable
    private final String userId;

    @Nullable
    private final String userName;

    @Nullable
    private final List<String> messages;

    @Nullable
    @ServerTimestamp
    private final List<String> timeStamps;

    public ChatConversationDto() {
        this(null, null, null, null);
    }

    public ChatConversationDto(
        @Nullable String userId,
        @Nullable String userName,
        @Nullable List<String> messages,
        @Nullable List<String> timeStamps
    ) {
        this.userId = userId;
        this.userName = userName;
        this.messages = messages;
        this.timeStamps = timeStamps;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }

    @Nullable
    public String getUserName() {
        return userName;
    }

    @Nullable
    public List<String> getMessages() {
        return messages;
    }

    @Nullable
    public List<String> getTimeStamps() {
        return timeStamps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatConversationDto that = (ChatConversationDto) o;
        return Objects.equals(userId, that.userId) && Objects.equals(userName, that.userName) && Objects.equals(messages, that.messages) && Objects.equals(timeStamps, that.timeStamps);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, messages, timeStamps);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatConversationDto{" +
            "id='" + userId + '\'' +
            ", name='" + userName + '\'' +
            ", message='" + messages + '\'' +
            ", timeStamp='" + timeStamps + '\'' +
            '}';
    }
}
