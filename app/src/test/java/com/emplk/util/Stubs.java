package com.emplk.util;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

public class Stubs {

    // region LoggedUserEntity
    private static final String TEST_LOGGED_USER_ENTITY_ID = "TEST_LOGGED_USER_ENTITY_ID";
    private static final String TEST_LOGGED_USER_ENTITY_NAME = "TEST_LOGGED_USER_ENTITY_NAME";
    private static final String TEST_LOGGED_USER_ENTITY_EMAIL = "TEST_LOGGED_USER_ENTITY_EMAIL";
    private static final String TEST_LOGGED_USER_ENTITY_PHOTO_URL = "TEST_LOGGED_USER_ENTITY_PHOTO_URL";

    public static LoggedUserEntity getTestLoggedUserEntity() {
        return new LoggedUserEntity(
            TEST_LOGGED_USER_ENTITY_ID,
            TEST_LOGGED_USER_ENTITY_NAME,
            TEST_LOGGED_USER_ENTITY_EMAIL,
            TEST_LOGGED_USER_ENTITY_PHOTO_URL
        );
    }
    // endregion


}
