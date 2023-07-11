package com.emplk.go4lunch.domain.chat.last_message;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class SaveLastChatMessageSentUseCaseTest {

    private final ChatRepository chatRepository = mock(ChatRepository.class);

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase = mock(GetCurrentLoggedUserUseCase.class);

    private SaveLastChatMessageSentUseCase saveLastChatMessageSentUseCase;

    @Before
    public void setUp() {
        saveLastChatMessageSentUseCase = new SaveLastChatMessageSentUseCase(chatRepository, getCurrentLoggedUserUseCase);
    }

    @Test
    public void testInvoke() {
        // Given
        doReturn(Stubs.getTestLoggedUserEntity()).when(getCurrentLoggedUserUseCase).invoke();

        // When
        saveLastChatMessageSentUseCase.invoke(
            Stubs.TEST_RECIPIENT_ENTITY_ID,
            Stubs.TEST_RECIPIENT_ENTITY_NAME,
            Stubs.TEST_RECIPIENT_ENTITY_PHOTO_URL,
            Stubs.TEST_CHAT_MESSAGE
        );

        // Then
        verify(getCurrentLoggedUserUseCase).invoke();
        verify(chatRepository).saveLastMessage(Stubs.getTestSendMessageEntity());
        verifyNoMoreInteractions(chatRepository, getCurrentLoggedUserUseCase);
    }
}