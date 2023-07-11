package com.emplk.util;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.chat.conversation.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.conversation.RecipientEntity;
import com.emplk.go4lunch.domain.chat.conversation.SenderEntity;
import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;
import com.emplk.go4lunch.domain.chat.send_message.SendMessageEntity;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;


public class Stubs {

    // region LoggedUserEntity
    public static final String TEST_LOGGED_USER_ENTITY_ID = "TEST_LOGGED_USER_ENTITY_ID";
    public static final String TEST_LOGGED_USER_ENTITY_NAME = "TEST_LOGGED_USER_ENTITY_NAME";
    public static final String TEST_LOGGED_USER_ENTITY_EMAIL = "TEST_LOGGED_USER_ENTITY_EMAIL";
    public static final String TEST_LOGGED_USER_ENTITY_PHOTO_URL = "TEST_LOGGED_USER_ENTITY_PHOTO_URL";

    public static final String TEST_CHAT_MESSAGE = "TEST_CHAT_MESSAGE";

    public static LoggedUserEntity getTestLoggedUserEntity() {
        return new LoggedUserEntity(
            TEST_LOGGED_USER_ENTITY_ID,
            TEST_LOGGED_USER_ENTITY_NAME,
            TEST_LOGGED_USER_ENTITY_EMAIL,
            TEST_LOGGED_USER_ENTITY_PHOTO_URL
        );
    }
    // endregion

// region ChatConversation

    // region SenderEntity
    public static final String TEST_SENDER_ENTITY_ID = "TEST_SENDER_ENTITY_ID";
    public static final String TEST_SENDER_ENTITY_NAME = "TEST_SENDER_ENTITY_NAME";
    public static final String TEST_SENDER_ENTITY_PHOTO_URL = "TEST_SENDER_ENTITY_PHOTO_URL";

    public static SenderEntity getTestSenderEntity() {
        return new SenderEntity(
            TEST_SENDER_ENTITY_ID,
            TEST_SENDER_ENTITY_NAME,
            TEST_SENDER_ENTITY_PHOTO_URL
        );
    }
// endregion

    // region RecipientEntity
    public static final String TEST_RECIPIENT_ENTITY_ID = "TEST_RECIPIENT_ENTITY_ID";
    public static final String TEST_RECIPIENT_ENTITY_NAME = "TEST_RECIPIENT_ENTITY_NAME";
    public static final String TEST_RECIPIENT_ENTITY_PHOTO_URL = "TEST_RECIPIENT_ENTITY_PHOTO_URL";

    public static RecipientEntity getTestRecipientEntity() {
        return new RecipientEntity(
            TEST_RECIPIENT_ENTITY_ID,
            TEST_RECIPIENT_ENTITY_NAME,
            TEST_RECIPIENT_ENTITY_PHOTO_URL
        );
    }

    // endregion

    // region ChatConversationEntity

    public static List<ChatConversationEntity> getTestChatConversationEntityList() {
        List<ChatConversationEntity> conversationEntities = new ArrayList<>();

        // Create multiple ChatConversationEntity objects with unique timestamps
        for (int i = 0; i < 5; i++) {
            ChatConversationEntity conversationEntity = createMockConversationEntity(i);
            conversationEntities.add(conversationEntity);
        }

        return conversationEntities;
    }

    public static ChatConversationEntity createMockConversationEntity(int index) {
        SenderEntity senderEntity = Stubs.getTestSenderEntity();
        RecipientEntity recipientEntity = Stubs.getTestRecipientEntity();
        Timestamp timestamp = createMockTimestamp(index);

        return new ChatConversationEntity(senderEntity, recipientEntity, TEST_CHAT_MESSAGE, timestamp);
    }

    private static Timestamp createMockTimestamp(int index) {
        // Create a mock Timestamp object with unique seconds and nanoseconds
        long seconds = System.currentTimeMillis() / 1000 + index;
        int nanoseconds = index * 1000000;

        return new Timestamp(seconds, nanoseconds);
    }
    // endregion

    // region LastChatMessageEntity

    public static LastChatMessageEntity getTestLastChatMessageEntity(int index) {
        SenderEntity senderEntity = Stubs.getTestSenderEntity();
        RecipientEntity recipientEntity = Stubs.getTestRecipientEntity();

        Timestamp timestamp = createMockTimestamp(index);

        return new LastChatMessageEntity(TEST_CHAT_MESSAGE, senderEntity, recipientEntity, timestamp);
    }

    public static List<LastChatMessageEntity> getTestLastChatMessageEntityList() {
        List<LastChatMessageEntity> lastChatMessageEntities = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            LastChatMessageEntity lastChatMessageEntity = getTestLastChatMessageEntity(i);
            lastChatMessageEntities.add(lastChatMessageEntity);
        }

        return lastChatMessageEntities;
    }

// endregion

    // SendMessageEntity
    public static SendMessageEntity getTestSendMessageEntity() {
        LoggedUserEntity loggedUserEntity = Stubs.getTestLoggedUserEntity();
        SenderEntity senderEntity = new SenderEntity(
            loggedUserEntity.getId(),
            loggedUserEntity.getName(),
            loggedUserEntity.getPictureUrl()
        );
        RecipientEntity recipientEntity = Stubs.getTestRecipientEntity();

        return new SendMessageEntity(senderEntity, recipientEntity, TEST_CHAT_MESSAGE);
    }


}
