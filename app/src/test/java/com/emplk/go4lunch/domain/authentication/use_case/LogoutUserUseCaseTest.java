package com.emplk.go4lunch.domain.authentication.use_case;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.AuthRepository;

import org.junit.Before;
import org.junit.Test;

public class LogoutUserUseCaseTest {

    private final AuthRepository authRepository = mock(AuthRepository.class);

    private LogoutUserUseCase logoutUserUseCase;

    @Before
    public void setUp() {
        logoutUserUseCase = new LogoutUserUseCase(authRepository);
    }

    @Test
    public void testInvoke() {
        // When
        logoutUserUseCase.invoke();

        // Then
        verify(authRepository).signOut();
        verifyNoMoreInteractions(authRepository);
    }
}