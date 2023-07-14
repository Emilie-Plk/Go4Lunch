package com.emplk.go4lunch.ui.chat.conversation;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.chat.conversation.ChatConversationEntity;
import com.emplk.go4lunch.domain.chat.conversation.GetChatConversationUseCase;
import com.emplk.go4lunch.domain.chat.send_message.SendMessageUseCase;
import com.emplk.go4lunch.domain.chat.send_message.SetMessageTypeStateUseCase;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class ChatConversationViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final SendMessageUseCase sendMessageUseCase = mock(SendMessageUseCase.class);

    private final GetChatConversationUseCase getChatConversationUseCase = mock(GetChatConversationUseCase.class);

    private final SetMessageTypeStateUseCase setMessageTypeStateUseCase = mock(SetMessageTypeStateUseCase.class);

    private ChatConversationViewModel viewModel;

    @Before
    public void setUp() {

     //   doReturn()
        viewModel = new ChatConversationViewModel(
            sendMessageUseCase,
            getChatConversationUseCase,
            setMessageTypeStateUseCase
        );
    }

    @Test
    public void sendMessage() {
        // Given
        String recipientId = Stubs.TEST_RECIPIENT_ENTITY_ID;
        String recipientName = Stubs.TEST_RECIPIENT_ENTITY_NAME;
        String recipientPhotoUrl = Stubs.TEST_RECIPIENT_ENTITY_PHOTO_URL;
        String message = Stubs.TEST_CHAT_MESSAGE;

        // When
        viewModel.sendMessage(
            recipientId,
            recipientName,
            recipientPhotoUrl,
            message
        );

        // Then
        verify(sendMessageUseCase).invoke(
            recipientId,
            recipientName,
            recipientPhotoUrl,
            message);
        verifyNoMoreInteractions(sendMessageUseCase, getChatConversationUseCase, setMessageTypeStateUseCase);
    }

    @Test
    public void getChatMessages() {
        // Given
        String workmateId = "WORKMATE_ID";
        MutableLiveData<List<ChatConversationEntity>> chatConversationEntityLiveData = new MutableLiveData<>();
        List<ChatConversationEntity> chatConversationEntities = Stubs.getTestChatConversationEntityList();
        chatConversationEntityLiveData.setValue(chatConversationEntities);
        doReturn(chatConversationEntityLiveData).when(getChatConversationUseCase).invoke(workmateId);

        // When
        List<ChatConversationMessageViewStateItem> result = getValueForTesting(viewModel.getChatMessages(workmateId));

        // Then
        assertEquals(
            chatConversationEntities.size(),
            result.size()
        );
        verify(getChatConversationUseCase).invoke(workmateId);
     //   verify(setMessageTypeStateUseCase).invoke(Stubs.TEST_RECIPIENT_ENTITY_ID);
     //  verifyNoMoreInteractions(sendMessageUseCase, getChatConversationUseCase, setMessageTypeStateUseCase);
    }

}