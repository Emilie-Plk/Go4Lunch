package com.emplk.go4lunch.ui.chat.conversation;

import androidx.annotation.NonNull;

import java.util.Objects;

public class ChatConversationMessageViewStateItem {

    @NonNull
    private final String userId;

    @NonNull
    private final String name;

    @NonNull
    private final String photoUrl;

    @NonNull
    private final String message;

    @NonNull
    private final String date;

    @NonNull
    private final MessageTypeState messageTypeState;

    public ChatConversationMessageViewStateItem(
        @NonNull String userId,
        @NonNull String name,
        @NonNull String photoUrl,
        @NonNull String message,
        @NonNull String date,
        @NonNull MessageTypeState messageTypeState
    ) {
        this.userId = userId;
        this.name = name;
        this.photoUrl = photoUrl;
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
    public String getPhotoUrl() {
        return photoUrl;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatConversationMessageViewStateItem that = (ChatConversationMessageViewStateItem) o;
        return userId.equals(that.userId) && name.equals(that.name) && photoUrl.equals(that.photoUrl) && message.equals(that.message) && date.equals(that.date) && messageTypeState == that.messageTypeState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, photoUrl, message, date, messageTypeState);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatConversationMessageViewStateItem{" +
            "userId='" + userId + '\'' +
            ", name='" + name + '\'' +
            ", photoUrl='" + photoUrl + '\'' +
            ", message='" + message + '\'' +
            ", date='" + date + '\'' +
            ", messageTypeState=" + messageTypeState +
            '}';
    }
}
