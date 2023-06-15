package com.emplk.go4lunch.domain.authentication.use_case;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GetCurrentLoggedUserUseCaseTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase;

    private final AuthRepository authRepository = mock(AuthRepository.class);

    @Before
    public void setUp() {
        LoggedUserEntity loggedUserEntity = mock(LoggedUserEntity.class);

        doReturn(loggedUserEntity)
            .when(authRepository)
            .getCurrentLoggedUser();

        getCurrentLoggedUserUseCase = new GetCurrentLoggedUserUseCase(authRepository);
    }

    @Test
    public void invoke() {
// WHEN
        getCurrentLoggedUserUseCase.invoke();

// THEN
        verify(authRepository).getCurrentLoggedUser();
        verifyNoMoreInteractions(authRepository);
    }
}