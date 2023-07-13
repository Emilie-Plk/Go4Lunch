package com.emplk.go4lunch.ui.login;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.user.use_case.AddLoggedUserEntityUseCase;

import org.junit.Before;
import org.junit.Test;

public class LoginActivityViewModelTest {

    private final static AddLoggedUserEntityUseCase addLoggedUserEntityUseCase = mock(AddLoggedUserEntityUseCase.class);

    private LoginActivityViewModel loginActivityViewModel;

    @Before
    public void setUp() {
        loginActivityViewModel = new LoginActivityViewModel(addLoggedUserEntityUseCase);
    }

    @Test
    public void onLoginComplete_shouldAddLoggedUser() {
        loginActivityViewModel.onLoginComplete();

        verify(addLoggedUserEntityUseCase).invoke();
        verifyNoMoreInteractions(addLoggedUserEntityUseCase);
    }
}