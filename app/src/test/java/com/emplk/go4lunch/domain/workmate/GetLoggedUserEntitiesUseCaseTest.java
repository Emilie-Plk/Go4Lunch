package com.emplk.go4lunch.domain.workmate;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.user.UserRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class GetLoggedUserEntitiesUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final UserRepository userRepository = mock(UserRepository.class);

    private GetLoggedUserEntitiesUseCase getLoggedUserEntitiesUseCase;

    @Before
    public void setUp() {
        MutableLiveData<List<LoggedUserEntity>> loggedUserEntitiesLiveData = new MutableLiveData<>();
        LoggedUserEntity loggedUserEntity1 = mock(LoggedUserEntity.class);
        LoggedUserEntity loggedUserEntity2 = mock(LoggedUserEntity.class);
        LoggedUserEntity loggedUserEntity3 = mock(LoggedUserEntity.class);
        List<LoggedUserEntity> loggedUserEntities = Arrays.asList(loggedUserEntity1, loggedUserEntity2, loggedUserEntity3);
        loggedUserEntitiesLiveData.setValue(loggedUserEntities);
        doReturn(loggedUserEntitiesLiveData).when(userRepository).getLoggedUserEntitiesLiveData();

        getLoggedUserEntitiesUseCase = new GetLoggedUserEntitiesUseCase(userRepository);
    }

    @Test
    public void testInvoke() {
        List<LoggedUserEntity> result = getValueForTesting(getLoggedUserEntitiesUseCase.invoke());

        assertEquals(3, result.size());
        verify(userRepository).getLoggedUserEntitiesLiveData();
        verifyNoMoreInteractions(userRepository);
    }
}