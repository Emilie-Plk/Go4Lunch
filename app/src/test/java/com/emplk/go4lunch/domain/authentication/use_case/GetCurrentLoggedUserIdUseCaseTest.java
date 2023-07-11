package com.emplk.go4lunch.domain.authentication.use_case;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class GetCurrentLoggedUserIdUseCaseTest {

    private final AuthRepository authRepository = mock(AuthRepository.class);

    private GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase;

    @Before
    public void setUp() {
        getCurrentLoggedUserIdUseCase = new GetCurrentLoggedUserIdUseCase(authRepository);
    }

    @Test
    public void success() {
        // Given
        LoggedUserEntity loggedUserEntity = Stubs.getTestLoggedUserEntity();
        String userId = loggedUserEntity.getId();
        doReturn(loggedUserEntity.getId()).when(authRepository).getCurrentLoggedUserId();

        // When
        String result = getCurrentLoggedUserIdUseCase.invoke();

        // Then
        assertEquals(userId, result);
        verify(authRepository).getCurrentLoggedUserId();

        verifyNoMoreInteractions(authRepository);
    }

    @Test(expected = IllegalStateException.class)
    public void failure() {
        // Given
        doReturn(null).when(authRepository).getCurrentLoggedUserId();

        // When
        getCurrentLoggedUserIdUseCase.invoke();
    }

}