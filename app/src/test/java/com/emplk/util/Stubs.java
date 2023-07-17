package com.emplk.util;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.chat.conversation.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.conversation.RecipientEntity;
import com.emplk.go4lunch.domain.chat.conversation.SenderEntity;
import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;
import com.emplk.go4lunch.domain.chat.send_message.SendMessageEntity;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantEntity;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.notification.NotificationEntity;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;
import com.emplk.go4lunch.ui.restaurant_detail.AttendanceState;
import com.emplk.go4lunch.ui.restaurant_detail.RestaurantDetailViewState;
import com.emplk.go4lunch.ui.utils.RestaurantDetailsFavoriteState;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Stubs {

    // region LoggedUserEntity
    public static final String TEST_USER_ID = "TEST_USER_ID";
    public static final String TEST_USER_NAME = "TEST_USER_NAME";
    public static final String TEST_USER_EMAIL = "TEST_USER_EMAIL";
    public static final String TEST_USER_PHOTO_URL = "TEST_USER_PHOTO_URL";

    public static final String TEST_CHAT_MESSAGE = "TEST_CHAT_MESSAGE";


    public static LoggedUserEntity getTestLoggedUserEntity() {
        return new LoggedUserEntity(
            TEST_USER_ID,
            TEST_USER_NAME,
            TEST_USER_EMAIL,
            TEST_USER_PHOTO_URL
        );
    }

    public static List<LoggedUserEntity> getFourTestLoggedUserEntities() {
        List<LoggedUserEntity> loggedUserEntityList = new ArrayList<>();
        loggedUserEntityList.add(
            new LoggedUserEntity(
                TEST_USER_ID + 1,
                TEST_USER_NAME,
                TEST_USER_EMAIL,
                TEST_USER_PHOTO_URL
            )
        );
        loggedUserEntityList.add(
            new LoggedUserEntity(
                TEST_USER_ID + 2,
                TEST_USER_NAME,
                TEST_USER_EMAIL,
                TEST_USER_PHOTO_URL
            )
        );
        loggedUserEntityList.add(
            new LoggedUserEntity(
                TEST_USER_ID + 3,
                TEST_USER_NAME,
                TEST_USER_EMAIL,
                TEST_USER_PHOTO_URL
            )
        );
        loggedUserEntityList.add(
            new LoggedUserEntity(
                TEST_USER_ID + 4,
                TEST_USER_NAME,
                TEST_USER_EMAIL,
                TEST_USER_PHOTO_URL
            )
        );
        return loggedUserEntityList;
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
            ChatConversationEntity conversationEntity = createMockChatConversationEntity(i);
            conversationEntities.add(conversationEntity);
        }
        return conversationEntities;
    }

    public static ChatConversationEntity createMockChatConversationEntity(int index) {
        SenderEntity senderEntity = Stubs.getTestSenderEntity();
        RecipientEntity recipientEntity = Stubs.getTestRecipientEntity();
        Timestamp timestamp = createMockTimestamp(index);

        return new ChatConversationEntity(senderEntity, recipientEntity, TEST_CHAT_MESSAGE, timestamp);
    }

    private static Timestamp createMockTimestamp(int index) {
        // Create a mock Timestamp object with unique seconds and nanoseconds
        long seconds = 1689264308 + index;
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
    // endregion


    // region RESTAURANT
    public static final String TEST_RESTAURANT_ID = "TEST_RESTAURANT_ID";

    public static final String TEST_RESTAURANT_NAME = "TEST_RESTAURANT_NAME";
    public static final String TEST_RESTAURANT_VICINITY = "TEST_RESTAURANT_VICINITY";
    public static final String TEST_RESTAURANT_PHOTO_URL = "TEST_RESTAURANT_PHOTO_URL";

    public static final Float TEST_RESTAURANT_RATING = 5f;
    public static final String TEST_RESTAURANT_PHONE_NUMBER = "TEST_RESTAURANT_PHONE_NUMBER";
    public static final String TEST_RESTAURANT_WEBSITE = "TEST_RESTAURANT_WEBSITE";

    // region DetailRestaurantEntity
    public static DetailsRestaurantEntity getTestDetailsRestaurantEntity() {
        return new DetailsRestaurantEntity(
            TEST_RESTAURANT_ID,
            TEST_RESTAURANT_NAME,
            TEST_RESTAURANT_VICINITY,
            TEST_RESTAURANT_PHOTO_URL,
            TEST_RESTAURANT_RATING,
            TEST_RESTAURANT_PHONE_NUMBER,
            TEST_RESTAURANT_WEBSITE,
            true);
    }

    // endregion

    public static Set<String> getTestRestaurantIdSet(int index) {
        Set<String> restaurantIdSet = new HashSet<>();
        for (int i = 0; i < index; i++) {
            restaurantIdSet.add(TEST_RESTAURANT_ID + i);
        }
        return restaurantIdSet;
    }

    // endregion

    // region LocationStateEntity

    public static final int TEST_RESTAURANT_RADIUS = 1_000;
    public static final String TEST_RESTAURANT_TYPE = "restaurant";

    public static final Double TEST_LATITUDE = 48.856614;
    public static final Double TEST_LONGITUDE = 2.3522219;

    public static LocationStateEntity.Success getTestLocationStateEntitySuccess() {
        return new LocationStateEntity.Success(
            new LocationEntity(
                TEST_LATITUDE,
                TEST_LONGITUDE
            )
        );
    }

    public static LocationStateEntity.GpsProviderDisabled getTestLocationStateEntityGpsProviderDisabled() {
        return new LocationStateEntity.GpsProviderDisabled();
    }
    // endregion

    // region NearbySearchEntity

    public static final LatLng TEST_USER_LAT_LNG = new LatLng(TEST_LATITUDE, TEST_LONGITUDE);

    public static final LatLng TEST_NEARBYSEARCH_LAT_LNG = new LatLng(TEST_LATITUDE + 1, TEST_LONGITUDE + 1);

    public static final String TEST_NEARBYSEARCH_ID = "TEST_NEARBYSEARCH_ID";
    public static final String TEST_NEARBYSEARCH_NAME = "TEST_NEARBYSEARCH_NAME";
    public static final String TEST_NEARBYSEARCH_VICINITY = "TEST_NEARBYSEARCH_VICINITY";

    public static final Integer TEST_NEARBYSEARCH_DISTANCE = 1000;
    public static final String TEST_NEARBYSEARCH_PICTURE_URL = "TEST_NEARBYSEARCH_PHOTO_URL";
    public static final Float TEST_NEARBYSEARCH_RATING = 3.5f;
    public static final LocationEntity TEST_NEARBYSEARCH_LOCATION_ENTITY = new LocationEntity(
        TEST_NEARBYSEARCH_LAT_LNG.latitude,
        TEST_NEARBYSEARCH_LAT_LNG.longitude
    );
    public static final Boolean TEST_NEARBYSEARCH_OPEN_NOW = true;

    public static LocationEntity getTestUserLocationEntity() {
        return new LocationEntity(TEST_USER_LAT_LNG.latitude, TEST_USER_LAT_LNG.longitude);
    }

    public static List<NearbySearchEntity> getTestNearbySearchEntityList(int index) {
        List<NearbySearchEntity> nearbySearchEntities = new ArrayList<>();

        for (int i = 0; i < index; i++) {
            NearbySearchEntity nearbySearchEntity = new NearbySearchEntity(
                TEST_NEARBYSEARCH_ID + i,
                TEST_NEARBYSEARCH_NAME,
                TEST_NEARBYSEARCH_VICINITY,
                TEST_NEARBYSEARCH_PICTURE_URL,
                TEST_NEARBYSEARCH_RATING,
                new LocationEntity(TEST_NEARBYSEARCH_LOCATION_ENTITY.getLatitude() + i, TEST_NEARBYSEARCH_LOCATION_ENTITY.getLongitude() + i),
                TEST_NEARBYSEARCH_DISTANCE,
                TEST_NEARBYSEARCH_OPEN_NOW
            );
            nearbySearchEntities.add(nearbySearchEntity);
        }
        return nearbySearchEntities;
    }

    public static String ATTENDING_USER_ID = "ATTENDING_USER_ID";
    public static String ATTENDING_USER_NAME = "ATTENDING_USER_NAME";
    public static String ATTENDING_RESTAURANT_ID = "ATTENDING_RESTAURANT_ID";
    public static String ATTENDING_RESTAURANT_NAME = "ATTENDING_RESTAURANT_NAME";
    public static String ATTENDING_RESTAURANT_VICINITY = "ATTENDING_RESTAURANT_VICINITY";

    public static UserWithRestaurantChoiceEntity getTestUserWithDifferentRestaurantChoiceEntity(String restaurantId) {
        return new UserWithRestaurantChoiceEntity(
            ATTENDING_USER_ID,
            ATTENDING_TIMESTAMP,
            ATTENDING_USER_NAME,
            ATTENDING_RESTAURANT_ID + restaurantId,
            ATTENDING_RESTAURANT_NAME,
            ATTENDING_RESTAURANT_VICINITY
        );
    }

    public static UserWithRestaurantChoiceEntity getTestUserWithSameRestaurantChoiceEntity() {
        return new UserWithRestaurantChoiceEntity(
            ATTENDING_USER_ID,
            ATTENDING_TIMESTAMP,
            ATTENDING_USER_NAME,
            ATTENDING_RESTAURANT_ID,
            ATTENDING_RESTAURANT_NAME,
            ATTENDING_RESTAURANT_VICINITY
        );
    }

    public static Timestamp ATTENDING_TIMESTAMP = new Timestamp(1686691200, 0);

    public static UserWithRestaurantChoiceEntity getTestCurrentUserWithRestaurantChoiceEntity() {
        return new UserWithRestaurantChoiceEntity(
            TEST_USER_ID,
            ATTENDING_TIMESTAMP,
            ATTENDING_USER_NAME,
            ATTENDING_RESTAURANT_ID,
            ATTENDING_RESTAURANT_NAME,
            ATTENDING_RESTAURANT_VICINITY
        );
    }
    // endregion

    // region NotificationEntity
    public static NotificationEntity getTestNotificationEntity(List<String> workmateNamesGoingToSameRestaurant) {
        return new NotificationEntity(
            ATTENDING_RESTAURANT_NAME,
            ATTENDING_RESTAURANT_ID,
            ATTENDING_RESTAURANT_VICINITY,
            workmateNamesGoingToSameRestaurant
        );
    }

    // endregion

    // region DetailsRestaurantWrapper

    public static DetailsRestaurantWrapper getTestDetailsRestaurantWrapperLoading() {
        return new DetailsRestaurantWrapper.Loading();
    }

    public static DetailsRestaurantWrapper getTestDetailsRestaurantWrapperError() {
        return new DetailsRestaurantWrapper.Error(
            new Exception("TEST_ERROR")
        );
    }

    public static DetailsRestaurantWrapper.Success getTestDetailsRestaurantWrapperSuccess() {
        return new DetailsRestaurantWrapper.Success(
            new DetailsRestaurantEntity(
                "KEY_RESTAURANT_ID",
                TEST_RESTAURANT_NAME,
                TEST_RESTAURANT_VICINITY,
                TEST_RESTAURANT_PHOTO_URL,
                TEST_RESTAURANT_RATING,
                TEST_RESTAURANT_PHONE_NUMBER,
                TEST_RESTAURANT_WEBSITE,
                true
            )
        );
    }
    // endregion

    // region RestaurantDetailViewState
    public static RestaurantDetailViewState getTestRestaurantDetailViewState() {
        return new RestaurantDetailViewState.RestaurantDetail(
            "KEY_RESTAURANT_ID",
            TEST_RESTAURANT_NAME,
            TEST_RESTAURANT_VICINITY,
            null,
            3.0f,
            TEST_RESTAURANT_PHONE_NUMBER,
            TEST_RESTAURANT_WEBSITE,
            AttendanceState.IS_NOT_ATTENDING,
            RestaurantDetailsFavoriteState.IS_NOT_FAVORITE,
            true,
            true,
            true
        );
    }

    public static RestaurantDetailViewState getTestRestaurantDetailViewStateError() {
        return new RestaurantDetailViewState.Error(
            "TEST_ERROR"
        );
    }

    public static RestaurantDetailViewState getTestRestaurantDetailViewStateLoading() {
        return new RestaurantDetailViewState.Loading();
    }
    // endregion

    // region WorkmateEntity
    public static WorkmateEntity getTestWorkmateEntity_currentUser() {
        return new WorkmateEntity(
            Stubs.getTestLoggedUserEntity(),
            "KEY_RESTAURANT_ID",
            ATTENDING_RESTAURANT_NAME,
            ATTENDING_RESTAURANT_VICINITY
        );
    }

    public static WorkmateEntity getTestWorkmateEntity() {
        return new WorkmateEntity(
            new LoggedUserEntity(
                "WORKMATE_ID",
                "WORKMATE_NAME",
                "WORKMATE_EMAIL",
                "WORKMATE_PHOTO_URL"
            ),
            TEST_RESTAURANT_ID,
            ATTENDING_RESTAURANT_NAME,
            ATTENDING_RESTAURANT_VICINITY
        );
    }

    public static List<WorkmateEntity> getThreeTestWorkmateEntities() {
        List<WorkmateEntity> workmateEntities = new ArrayList<>();
        workmateEntities.add(getTestWorkmateEntity());
        workmateEntities.add(getTestWorkmateEntity());
        workmateEntities.add(getTestWorkmateEntity());
        return workmateEntities;
    }

    // endregion
}
