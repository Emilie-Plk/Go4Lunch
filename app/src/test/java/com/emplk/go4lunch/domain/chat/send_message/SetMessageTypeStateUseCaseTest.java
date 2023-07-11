package com.emplk.go4lunch.domain.chat.send_message;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.ui.chat.conversation.MessageTypeState;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class SetMessageTypeStateUseCaseTest {

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private SetMessageTypeStateUseCase setMessageTypeStateUseCase;

    @Before
    public void setUp() {
        setMessageTypeStateUseCase = new SetMessageTypeStateUseCase(getCurrentLoggedUserIdUseCase);
    }

    @Test
    public void testInvoke_messageType_isRecipient() {
        // Given
        doReturn(Stubs.getTestLoggedUserEntity().getId()).when(getCurrentLoggedUserIdUseCase).invoke();

        // When
       MessageTypeState result = setMessageTypeStateUseCase.invoke(Stubs.TEST_LOGGED_USER_ENTITY_ID);

        // Then
        assertEquals(MessageTypeState.RECIPIENT, result);
    }

    @Test
    public void testInvoke_messageType_isSender() {
        // Given
        doReturn("123").when(getCurrentLoggedUserIdUseCase).invoke();

        // When
        MessageTypeState result = setMessageTypeStateUseCase.invoke(Stubs.TEST_LOGGED_USER_ENTITY_ID);

        // Then
        assertEquals(MessageTypeState.SENDER, result);
    }
}