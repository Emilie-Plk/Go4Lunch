package com.emplk.go4lunch.domain.chat.last_message;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class LastChatMessageEntity {

    @NonNull
    private final String lastMessage;

    @NonNull
    private final String senderId;

    @NonNull
    private final String senderName;

    @NonNull
    private final String senderPhotoUrl;

    @NonNull
    private final String recipientId;

    @NonNull
    private final String recipientName;

    @NonNull
    private final String recipientPhotoUrl;

    @NonNull
    private final Timestamp timestamp;

    public LastChatMessageEntity(
        @NonNull String lastMessage,
        @NonNull String senderId,
        @NonNull String senderName,
        @NonNull String senderPhotoUrl,
        @NonNull String recipientId,
        @NonNull String recipientName,
        @NonNull String recipientPhotoUrl,
        @NonNull Timestamp timestamp
    ) {
        this.lastMessage = lastMessage;
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderPhotoUrl = senderPhotoUrl;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.recipientPhotoUrl = recipientPhotoUrl;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getLastMessage() {
        return lastMessage;
    }

    @NonNull
    public String getSenderId() {
        return senderId;
    }
    @NonNull
    public String getSenderName() {
        return senderName;
    }

    @NonNull
    public String getSenderPhotoUrl() {
        return senderPhotoUrl;
    }

    @NonNull
    public String getRecipientId() {
        return recipientId;
    }

    @NonNull
    public String getRecipientName() {
        return recipientName;
    }

    @NonNull
    public String getRecipientPhotoUrl() {
        return recipientPhotoUrl;
    }


    @NonNull
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastChatMessageEntity that = (LastChatMessageEntity) o;
        return lastMessage.equals(that.lastMessage) && senderId.equals(that.senderId) && senderName.equals(that.senderName) && senderPhotoUrl.equals(that.senderPhotoUrl) && recipientId.equals(that.recipientId) && recipientName.equals(that.recipientName) && recipientPhotoUrl.equals(that.recipientPhotoUrl) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastMessage, senderId, senderName, senderPhotoUrl, recipientId, recipientName, recipientPhotoUrl, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "LastChatMessageEntity{" +
            "lastMessage='" + lastMessage + '\'' +
            ", senderId='" + senderId + '\'' +
            ", senderName='" + senderName + '\'' +
            ", senderPhotoUrl='" + senderPhotoUrl + '\'' +
            ", recipientId='" + recipientId + '\'' +
            ", recipientName='" + recipientName + '\'' +
            ", recipientPhotoUrl='" + recipientPhotoUrl + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
