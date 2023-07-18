package com.emplk.go4lunch.ui.chat.last_messages;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.chat.last_message.GetLastChatMessageSortedChronologicallyUseCase;
import com.emplk.go4lunch.domain.chat.last_message.LastChatMessageEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class ChatLastMessageListViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final GetLastChatMessageSortedChronologicallyUseCase getLastChatMessageSortedChronologicallyUseCase = mock(GetLastChatMessageSortedChronologicallyUseCase.class);

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase = mock(GetCurrentLoggedUserUseCase.class);

    private ChatLastMessageListViewModel chatLastMessageListViewModel;

    @Before
    public void setUp() {
        List<LastChatMessageEntity> lastChatMessageEntities = Stubs.getTestFiveLastChatMessageEntityList();
        MutableLiveData<List<LastChatMessageEntity>> lastChatMessageEntitiesLiveData = new MutableLiveData<>(lastChatMessageEntities);
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
    }
}