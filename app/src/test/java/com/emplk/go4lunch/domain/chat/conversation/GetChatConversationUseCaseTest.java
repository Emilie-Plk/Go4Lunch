package com.emplk.go4lunch.domain.chat.conversation;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class GetChatConversationUseCaseTest {

    private static final String TEST_SENDER_ID = "senderId";
    private static final String TEST_RECIPIENT_ID = "recipientId";
    private final ChatRepository chatRepository = mock(ChatRepository.class);
    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private GetChatConversationUseCase getChatConversationUseCase;

    @Before
    public void setUp() {
        doReturn(TEST_SENDER_ID).when(getCurrentLoggedUserIdUseCase).invoke();

        List<ChatConversationEntity> chatConversationEntities = Stubs.getTestChatConversationEntityList();
        MutableLiveData<List<ChatConversationEntity>> chatConversationEntityLiveData = new MutableLiveData<>(chatConversationEntities);
        doReturn(chatConversationEntityLiveData).when(chatRepository).getChatConversation(TEST_SENDER_ID, TEST_RECIPIENT_ID);

        getChatConversationUseCase = new GetChatConversationUseCase(chatRepository, getCurrentLoggedUserIdUseCase);
    }

    @Test
    public void testInvoke() {
        // When
        getChatConversationUseCase.invoke(TEST_RECIPIENT_ID);

        // Then
        verify(chatRepository).getChatConversation(TEST_SENDER_ID, TEST_RECIPIENT_ID);
        verifyNoMoreInteractions(chatRepository);
    }
}