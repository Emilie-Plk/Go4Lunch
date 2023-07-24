package com.emplk.go4lunch.domain.chat.last_message;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.chat.conversation.RecipientEntity;
import com.emplk.go4lunch.domain.chat.conversation.SenderEntity;
import com.google.firebase.Timestamp;

import java.util.Objects;

public class LastChatMessageEntity {
    @NonNull
    private final String id;
    @NonNull
    private final String lastMessage;

    @NonNull
    private final SenderEntity senderEntity;

    @NonNull
    private final RecipientEntity recipientEntity;

    @NonNull
    private final Timestamp timestamp;

    public LastChatMessageEntity(
        @NonNull String id,
        @NonNull String lastMessage,
        @NonNull SenderEntity senderEntity,
        @NonNull RecipientEntity recipientEntity,
        @NonNull Timestamp timestamp
    ) {
        this.id = id;
        this.lastMessage = lastMessage;
        this.senderEntity = senderEntity;
        this.recipientEntity = recipientEntity;
        this.timestamp = timestamp;
    }

    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getLastMessage() {
        return lastMessage;
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
    public Timestamp getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LastChatMessageEntity that = (LastChatMessageEntity) o;
        return id.equals(that.id) && lastMessage.equals(that.lastMessage) && senderEntity.equals(that.senderEntity) && recipientEntity.equals(that.recipientEntity) && timestamp.equals(that.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastMessage, senderEntity, recipientEntity, timestamp);
    }

    @NonNull
    @Override
    public String toString() {
        return "LastChatMessageEntity{" +
            "id='" + id + '\'' +
            ", lastMessage='" + lastMessage + '\'' +
            ", senderEntity=" + senderEntity +
            ", recipientEntity=" + recipientEntity +
            ", timestamp=" + timestamp +
            '}';
    }
}
