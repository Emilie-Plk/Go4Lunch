package com.emplk.go4lunch.domain.authentication.use_case;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class GetCurrentLoggedUserUseCaseTest {

    private final AuthRepository authRepository = mock(AuthRepository.class);

    LoggedUserEntity loggedUserEntity = Stubs.getTestLoggedUserEntity();

    private GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    @Before
    public void setUp() {
        doReturn(loggedUserEntity)
            .when(authRepository)
            .getCurrentLoggedUser();

        getCurrentLoggedUserUseCase = new GetCurrentLoggedUserUseCase(authRepository);
    }

    @Test
    public void testInvoke() {
        // When
        getCurrentLoggedUserUseCase.invoke();

        // Then
        verify(authRepository).getCurrentLoggedUser();
        verifyNoMoreInteractions(authRepository);
    }
}