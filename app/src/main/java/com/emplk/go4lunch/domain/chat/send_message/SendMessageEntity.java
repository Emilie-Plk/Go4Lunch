package com.emplk.go4lunch.domain.chat.send_message;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.chat.conversation.RecipientEntity;
import com.emplk.go4lunch.domain.chat.conversation.SenderEntity;

import java.util.Objects;

public class SendMessageEntity {
    @NonNull
    private final SenderEntity senderEntity;

    @NonNull
    private final RecipientEntity recipientEntity;

    @NonNull
    private final String message;

    public SendMessageEntity(
        @NonNull SenderEntity senderEntity,
        @NonNull RecipientEntity recipientEntity,
        @NonNull String message
    ) {
        this.senderEntity = senderEntity;
        this.recipientEntity = recipientEntity;
        this.message = message;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SendMessageEntity that = (SendMessageEntity) o;
        return senderEntity.equals(that.senderEntity) && recipientEntity.equals(that.recipientEntity) && message.equals(that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderEntity, recipientEntity, message);
    }

    @NonNull
    @Override
    public String toString() {
        return "SendMessageEntity{" +
            "senderEntity=" + senderEntity +
            ", recipientEntity=" + recipientEntity +
            ", message='" + message + '\'' +
            '}';
    }
}
