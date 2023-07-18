package com.emplk.go4lunch.ui.chat.last_messages;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.chat.conversation.RecipientEntity;
import com.emplk.go4lunch.domain.chat.conversation.SenderEntity;
import com.emplk.go4lunch.domain.chat.last_message.GetLastChatMessageSortedChronologicallyUseCase;
import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class ChatLastMessageListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final GetLastChatMessageSortedChronologicallyUseCase getLastChatMessageSortedChronologicallyUseCase = mock(GetLastChatMessageSortedChronologicallyUseCase.class);

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase = mock(GetCurrentLoggedUserUseCase.class);

    private final MutableLiveData<List<LastChatMessageEntity>> lastChatMessageEntitiesLiveData = new MutableLiveData<>();

    private ChatLastMessageListViewModel chatLastMessageListViewModel;

    @Before
    public void setUp() {
        List<LastChatMessageEntity> lastChatMessageEntities = Stubs.getTestFiveLastChatMessageEntityList();
        lastChatMessageEntitiesLiveData.setValue(lastChatMessageEntities);
        doReturn(lastChatMessageEntitiesLiveData).when(getLastChatMessageSortedChronologicallyUseCase).invoke();

        LoggedUserEntity loggedUserEntity = Stubs.getTestLoggedUserEntity();
        doReturn(loggedUserEntity).when(getCurrentLoggedUserUseCase).invoke();

        chatLastMessageListViewModel = new ChatLastMessageListViewModel(getLastChatMessageSortedChronologicallyUseCase, getCurrentLoggedUserUseCase);
    }

    @Test
    public void testInvoke() {
        // When
        List<ChatLastMessageViewStateItem> result = getValueForTesting(chatLastMessageListViewModel.getChatLastMessageViewStateItems());

        // Then
        assertEquals(5, result.size());
        verify(getCurrentLoggedUserUseCase, times(2)).invoke();
        verify(getLastChatMessageSortedChronologicallyUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLoggedUserUseCase, getLastChatMessageSortedChronologicallyUseCase);
    }

    @Test
    public void getWorkmatePhotoUrl_shouldReturnRecipientPhotoUrl() {
        // When
        List<ChatLastMessageViewStateItem> result = getValueForTesting(chatLastMessageListViewModel.getChatLastMessageViewStateItems());

        // Then
        assertEquals(Stubs.TEST_RECIPIENT_ENTITY_PHOTO_URL, result.get(0).getPhotoUrl());
        verify(getCurrentLoggedUserUseCase, times(2)).invoke();
        verify(getLastChatMessageSortedChronologicallyUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLoggedUserUseCase, getLastChatMessageSortedChronologicallyUseCase);
    }

    @Test
    public void getWorkmatePhotoUrl_shouldReturnSenderPhotoUrl() {
        // Given
        SenderEntity senderEntity = Stubs.getTestSenderEntity();

        RecipientEntity recipientEntity = new RecipientEntity(
            Stubs.TEST_USER_ID,
            Stubs.TEST_USER_NAME,
            Stubs.TEST_USER_PHOTO_URL
        );

        LastChatMessageEntity lastChatMessageEntity = new LastChatMessageEntity(
            "Hello",
            senderEntity,
            recipientEntity,
            Stubs.TIMESTAMP
        );
        List<LastChatMessageEntity> lastChatMessageEntities = Collections.singletonList(lastChatMessageEntity);
        lastChatMessageEntitiesLiveData.setValue(lastChatMessageEntities);

        // When
        List<ChatLastMessageViewStateItem> result = getValueForTesting(chatLastMessageListViewModel.getChatLastMessageViewStateItems());

        // Then
        assertEquals(Stubs.TEST_SENDER_ENTITY_PHOTO_URL, result.get(0).getPhotoUrl());
        verify(getCurrentLoggedUserUseCase, times(2)).invoke();
        verify(getLastChatMessageSortedChronologicallyUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLoggedUserUseCase, getLastChatMessageSortedChronologicallyUseCase);
    }

    @Test
    public void getWorkmateUserName_shouldReturnRecipientName() {
        // When
        List<ChatLastMessageViewStateItem> result = getValueForTesting(chatLastMessageListViewModel.getChatLastMessageViewStateItems());

        // Then
        assertEquals(Stubs.TEST_RECIPIENT_ENTITY_NAME, result.get(0).getName());
        verify(getCurrentLoggedUserUseCase, times(2)).invoke();
        verify(getLastChatMessageSortedChronologicallyUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLoggedUserUseCase, getLastChatMessageSortedChronologicallyUseCase);
    }

    @Test
    public void getWorkmateUserName_shouldReturnSenderName() {
        // Given
        SenderEntity senderEntity = Stubs.getTestSenderEntity();

        RecipientEntity recipientEntity = new RecipientEntity(
            Stubs.TEST_USER_ID,
            Stubs.TEST_USER_NAME,
            Stubs.TEST_USER_PHOTO_URL
        );

        LastChatMessageEntity lastChatMessageEntity = new LastChatMessageEntity(
            "Hello",
            senderEntity,
            recipientEntity,
            Stubs.TIMESTAMP
        );
        List<LastChatMessageEntity> lastChatMessageEntities = Collections.singletonList(lastChatMessageEntity);
        lastChatMessageEntitiesLiveData.setValue(lastChatMessageEntities);

        // When
        List<ChatLastMessageViewStateItem> result = getValueForTesting(chatLastMessageListViewModel.getChatLastMessageViewStateItems());

        // Then
        assertEquals(Stubs.TEST_SENDER_ENTITY_NAME, result.get(0).getName());
        verify(getCurrentLoggedUserUseCase, times(2)).invoke();
        verify(getLastChatMessageSortedChronologicallyUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLoggedUserUseCase, getLastChatMessageSortedChronologicallyUseCase);
    }

    @Test
    public void getWorkmateId_shouldReturnRecipientId() {
        // When
        List<ChatLastMessageViewStateItem> result = getValueForTesting(chatLastMessageListViewModel.getChatLastMessageViewStateItems());

        // Then
        assertEquals(Stubs.TEST_RECIPIENT_ENTITY_ID, result.get(0).getUserId());
        verify(getCurrentLoggedUserUseCase, times(2)).invoke();
        verify(getLastChatMessageSortedChronologicallyUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLoggedUserUseCase, getLastChatMessageSortedChronologicallyUseCase);
    }

    @Test
    public void getWorkmateUserId_shouldReturnSenderId() {
        // Given
        SenderEntity senderEntity = Stubs.getTestSenderEntity();

        RecipientEntity recipientEntity = new RecipientEntity(
            Stubs.TEST_USER_ID,
            Stubs.TEST_USER_NAME,
            Stubs.TEST_USER_PHOTO_URL
        );

        LastChatMessageEntity lastChatMessageEntity = new LastChatMessageEntity(
            "Hello",
            senderEntity,
            recipientEntity,
            Stubs.TIMESTAMP
        );
        List<LastChatMessageEntity> lastChatMessageEntities = Collections.singletonList(lastChatMessageEntity);
        lastChatMessageEntitiesLiveData.setValue(lastChatMessageEntities);

        // When
        List<ChatLastMessageViewStateItem> result = getValueForTesting(chatLastMessageListViewModel.getChatLastMessageViewStateItems());

        // Then
        assertEquals(Stubs.TEST_SENDER_ENTITY_ID, result.get(0).getUserId());
        verify(getCurrentLoggedUserUseCase, times(2)).invoke();
        verify(getLastChatMessageSortedChronologicallyUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLoggedUserUseCase, getLastChatMessageSortedChronologicallyUseCase);
    }
}