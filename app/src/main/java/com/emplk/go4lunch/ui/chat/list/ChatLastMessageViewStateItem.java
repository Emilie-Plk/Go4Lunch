package com.emplk.go4lunch.ui.chat.list;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ChatLastMessageViewStateItem {

    @NonNull
    private final String userId;

    @NonNull
    private final String name;

    @NonNull
    private final String lastMessage;

    @NonNull
    private final String photoUrl;

    @NonNull
    private final String date;

    public ChatLastMessageViewStateItem(
        @NonNull String userId,
        @NonNull String name,
        @NonNull String lastMessage,
        @NonNull String photoUrl,
        @NonNull String date
    ) {
        this.userId = userId;
        this.name = name;
        this.lastMessage = lastMessage;
        this.photoUrl = photoUrl;
        this.date = date;
    }

    @NonNull
    public String getUserId() {
        return userId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getLastMessage() {
        return lastMessage;
    }

    @NonNull
    public String getPhotoUrl() {
        return photoUrl;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatLastMessageViewStateItem that = (ChatLastMessageViewStateItem) o;
        return userId.equals(that.userId) && name.equals(that.name) && lastMessage.equals(that.lastMessage) && Objects.equals(photoUrl, that.photoUrl) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, lastMessage, photoUrl, date);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatLastMessageViewStateItem{" +
            "userId='" + userId + '\'' +
            ", name='" + name + '\'' +
            ", lastMessage='" + lastMessage + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            ", date='" + date + '\'' +
            '}';
    }
}
