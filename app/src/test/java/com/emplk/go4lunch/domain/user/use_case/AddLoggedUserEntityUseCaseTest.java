package com.emplk.go4lunch.domain.user.use_case;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class AddLoggedUserEntityUseCaseTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase = mock(GetCurrentLoggedUserUseCase.class);

    private AddLoggedUserEntityUseCase addLoggedUserEntityUseCase;

    @Before
    public void setUp() {
        doReturn(Stubs.getTestLoggedUserEntity()).when(getCurrentLoggedUserUseCase).invoke();
        addLoggedUserEntityUseCase = new AddLoggedUserEntityUseCase(userRepository, getCurrentLoggedUserUseCase);
    }

    @Test
    public void testInvoke() {
        // When
        addLoggedUserEntityUseCase.invoke();

        // Then
        verify(userRepository).upsertLoggedUserEntity(Stubs.getTestLoggedUserEntity());
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void edgeCase() {
        doReturn(null).when(getCurrentLoggedUserUseCase).invoke();
        addLoggedUserEntityUseCase.invoke();

        verifyNoInteractions(userRepository);
    }
}