package com.emplk.go4lunch.ui.chat;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ChatMessageViewStateItem {

    @NonNull
    private final String userId;

    @NonNull
    private final String name;


    @NonNull
    private final String message;

    @NonNull
    private final String date;

    @NonNull
    private final MessageTypeState messageTypeState;

    public ChatMessageViewStateItem(
        @NonNull String userId,
        @NonNull String name,
        @NonNull String message,
        @NonNull String date,
        @NonNull MessageTypeState messageTypeState
    ) {
        this.userId = userId;
        this.name = name;
        this.message = message;
        this.date = date;
        this.messageTypeState = messageTypeState;
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
    public String getMessage() {
        return message;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    @NonNull
    public MessageTypeState getMessageTypeState() {
        return messageTypeState;
    }

    @Override
    public String toString() {
        return "ChatMessageViewStateItem{" +
            "userId='" + userId + '\'' +
            ", name='" + name + '\'' +
            ", message='" + message + '\'' +
            ", date='" + date + '\'' +
            ", messageTypeState=" + messageTypeState +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatMessageViewStateItem that = (ChatMessageViewStateItem) o;
        return userId.equals(that.userId) && name.equals(that.name) && message.equals(that.message) && date.equals(that.date) && messageTypeState == that.messageTypeState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, message, date, messageTypeState);
    }
}
