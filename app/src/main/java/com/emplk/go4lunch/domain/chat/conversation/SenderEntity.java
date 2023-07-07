package com.emplk.go4lunch.domain.chat.conversation;

import androidx.annotation.NonNull;

import java.util.Objects;

public class SenderEntity {

    @NonNull
    private final String senderId;

    @NonNull
    private final String senderName;

    @NonNull
    private final String senderPictureUrl;

    public SenderEntity(
        @NonNull String senderId,
        @NonNull String senderName,
        @NonNull String senderPictureUrl
    ) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.senderPictureUrl = senderPictureUrl;
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
    public String getSenderPictureUrl() {
        return senderPictureUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SenderEntity that = (SenderEntity) o;
        return senderId.equals(that.senderId) && senderName.equals(that.senderName) && senderPictureUrl.equals(that.senderPictureUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(senderId, senderName, senderPictureUrl);
    }

    @NonNull
    @Override
    public String toString() {
        return "SenderEntity{" +
            "senderId='" + senderId + '\'' +
            ", senderName='" + senderName + '\'' +
            ", senderPictureUrl='" + senderPictureUrl + '\'' +
            '}';
    }
}
