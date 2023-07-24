package com.emplk.go4lunch.domain.chat.conversation;

import androidx.annotation.NonNull;

import com.google.firebase.Timestamp;

import java.util.Objects;

public class ChatConversationEntity {

    @NonNull
    private final String id;

    @NonNull
    private final SenderEntity senderEntity;

    @NonNull
    private final RecipientEntity recipientEntity;

    @NonNull
    private final String message;

    @NonNull
    private final Timestamp timestamp;

    public ChatConversationEntity(
        @NonNull String id,
        @NonNull SenderEntity senderEntity,
        @NonNull RecipientEntity recipientEntity,
        @NonNull String message,
        @NonNull Timestamp timestamp
    ) {
        this.id = id;
        this.senderEntity = senderEntity;
        this.recipientEntity = recipientEntity;
        this.message = message;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public SenderEntity getSenderEntity() {
        return senderEntity;
    }

    @NonNull
    public RecipientEntity getRecipientEntity() {
        return recipientEntity;
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
        return id.equals(that.id) && senderEntity.equals(that.senderEntity) && recipientEntity.equals(that.recipientEntity) && message.equals(that.message) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, senderEntity, recipientEntity, message, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "ChatConversationEntity{" +
            "id='" + id + '\'' +
            ", senderEntity=" + senderEntity +
            ", recipientEntity=" + recipientEntity +
            ", message='" + message + '\'' +
            ", timestamp=" + timestamp +
            '}';
    }
}
