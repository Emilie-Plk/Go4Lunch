package com.emplk.go4lunch.ui.chat.last_messages;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ChatLastMessageViewStateItem {

    @NonNull
    private final String id;

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
        @NonNull String id,
        @NonNull String userId,
        @NonNull String name,
        @NonNull String lastMessage,
        @NonNull String photoUrl,
        @NonNull String date
    ) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.lastMessage = lastMessage;
        this.photoUrl = photoUrl;
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
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
        return id.equals(that.id) && userId.equals(that.userId) && name.equals(that.name) && lastMessage.equals(that.lastMessage) && photoUrl.equals(that.photoUrl) && date.equals(that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, name, lastMessage, photoUrl, date);
    }

    @Override
    public String toString() {
        return "ChatLastMessageViewStateItem{" +
            "id='" + id + '\'' +
            ", userId='" + userId + '\'' +
            ", name='" + name + '\'' +
            ", lastMessage='" + lastMessage + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            ", date='" + date + '\'' +
            '}';
    }
}
