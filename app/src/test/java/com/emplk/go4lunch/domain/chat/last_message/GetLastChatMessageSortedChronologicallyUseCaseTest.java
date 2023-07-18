package com.emplk.go4lunch.domain.chat.last_message;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GetLastChatMessageSortedChronologicallyUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private static final String TEST_USER_ID = "TEST_USER_ID";
    private final ChatRepository chatRepository = mock(ChatRepository.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private GetLastChatMessageSortedChronologicallyUseCase getLastChatMessageSortedChronologicallyUseCase;

    @Before
    public void setUp() {
        doReturn(TEST_USER_ID).when(getCurrentLoggedUserIdUseCase).invoke();


        getLastChatMessageSortedChronologicallyUseCase = new GetLastChatMessageSortedChronologicallyUseCase(chatRepository, getCurrentLoggedUserIdUseCase);
    }


    @Test
    public void testInvoke() {
        // Given
        MutableLiveData<List<LastChatMessageEntity>> lastChatMessagesList = new MutableLiveData<>();
        List<LastChatMessageEntity> lastChatMessageEntities = Stubs.getTestFiveLastChatMessageEntityList();
        lastChatMessagesList.setValue(lastChatMessageEntities);
        doReturn(lastChatMessagesList).when(chatRepository).getLastChatMessagesList(TEST_USER_ID);

        // When
        getLastChatMessageSortedChronologicallyUseCase.invoke();

        // Then
        verify(chatRepository).getLastChatMessagesList(TEST_USER_ID);
    }

    @Test
    public void testInvoke_sortChronologically_withSuccess() {
        // Given
        MutableLiveData<List<LastChatMessageEntity>> lastChatMessagesList = new MutableLiveData<>();
        List<LastChatMessageEntity> unsortedMessages = Arrays.asList(
            Stubs.getTestLastChatMessageEntity(3),
            Stubs.getTestLastChatMessageEntity(1),
            Stubs.getTestLastChatMessageEntity(2)
        );
        lastChatMessagesList.setValue(unsortedMessages);
        doReturn(lastChatMessagesList).when(chatRepository).getLastChatMessagesList(TEST_USER_ID);

        // When
        List<LastChatMessageEntity> result = getValueForTesting(getLastChatMessageSortedChronologicallyUseCase.invoke());

        List<LastChatMessageEntity> expectedSortedMessages = Arrays.asList(
            Stubs.getTestLastChatMessageEntity(1),
            Stubs.getTestLastChatMessageEntity(2),
            Stubs.getTestLastChatMessageEntity(3)
        );

        assertEquals(expectedSortedMessages, result);
    }

    @Test
    public void testInvoke_sortChronologically_withError() {
        // Given
        MutableLiveData<List<LastChatMessageEntity>> lastChatMessagesList = new MutableLiveData<>();
        List<LastChatMessageEntity> unsortedMessages = Arrays.asList(
            Stubs.getTestLastChatMessageEntity(3),
            Stubs.getTestLastChatMessageEntity(1),
            Stubs.getTestLastChatMessageEntity(2)
        );
        lastChatMessagesList.setValue(unsortedMessages);
        doReturn(lastChatMessagesList).when(chatRepository).getLastChatMessagesList(TEST_USER_ID);

        // When
        List<LastChatMessageEntity> result = getValueForTesting(getLastChatMessageSortedChronologicallyUseCase.invoke());

        List<LastChatMessageEntity> expectedSortedMessages = Arrays.asList(
            Stubs.getTestLastChatMessageEntity(2),
            Stubs.getTestLastChatMessageEntity(1),
            Stubs.getTestLastChatMessageEntity(3)
        );

        assertNotEquals(expectedSortedMessages, result);
    }
}