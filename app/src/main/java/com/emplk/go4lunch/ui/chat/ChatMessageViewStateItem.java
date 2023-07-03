package com.emplk.go4lunch.ui.chat;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ChatMessageViewStateItem {

    private final String receiverId;

    private final String name;

    private final String message;

    private final String date;

    public ChatMessageViewStateItem(
        String receiverId,
        String name,
        String message,
        String date
    ) {
        this.receiverId = receiverId;
        this.name = name;
        this.message = message;
        this.date = date;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessageViewStateItem that = (ChatMessageViewStateItem) o;
        return Objects.equals(receiverId, that.receiverId) && Objects.equals(name, that.name) && Objects.equals(message, that.message) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiverId, name, message, date);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatMessageViewStateItem{" +
            "receiverId='" + receiverId + '\'' +
            ", name='" + name + '\'' +
            ", message='" + message + '\'' +
            ", date='" + date + '\'' +
            '}';
    }
}
