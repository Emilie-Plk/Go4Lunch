package com.emplk.go4lunch.data.chat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class LastChatMessageDto {

    @Nullable
    private final String message;

    @Nullable
    private final String senderId;
    @Nullable
    private final String senderName;

    @Nullable
    private final String senderPictureUrl;

    @Nullable
    private final String recipientId;

    @Nullable
    private final String recipientName;

    @Nullable
    private final String recipientPictureUrl;

    @Nullable
    private final Timestamp timestamp;

    public LastChatMessageDto() {
        this(null, null, null, null, null, null, null, null);
    }

    public LastChatMessageDto(
        @Nullable String message,
        @Nullable String senderId,
        @Nullable String senderName,
        @Nullable String senderPictureUrl,
        @Nullable String recipientId,
        @Nullable String recipientName,
        @Nullable String recipientPictureUrl,
        @Nullable Timestamp timestamp
    ) {
        this.message = message;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderPictureUrl = senderPictureUrl;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientPictureUrl = recipientPictureUrl;
        this.timestamp = timestamp;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public String getSenderId() {
        return senderId;
    }

    @Nullable
    public String getSenderName() {
        return senderName;
    }

    @Nullable
    public String getSenderPictureUrl() {
        return senderPictureUrl;
    }

    @Nullable
    public String getRecipientId() {
        return recipientId;
    }

    @Nullable
    public String getRecipientName() {
        return recipientName;
    }

    @Nullable
    public String getRecipientPictureUrl() {
        return recipientPictureUrl;
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
        return Objects.equals(message, that.message) && Objects.equals(senderId, that.senderId) && Objects.equals(senderName, that.senderName) && Objects.equals(senderPictureUrl, that.senderPictureUrl) && Objects.equals(recipientId, that.recipientId) && Objects.equals(recipientName, that.recipientName) && Objects.equals(recipientPictureUrl, that.recipientPictureUrl) && Objects.equals(timestamp, that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, senderId, senderName, senderPictureUrl, recipientId, recipientName, recipientPictureUrl, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "LastChatMessageDto{" +
            "message='" + message + '\'' +
            ", senderId='" + senderId + '\'' +
            ", senderName='" + senderName + '\'' +
            ", senderPictureUrl='" + senderPictureUrl + '\'' +
            ", recipientId='" + recipientId + '\'' +
            ", recipientName='" + recipientName + '\'' +
            ", recipientPictureUrl='" + recipientPictureUrl + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
