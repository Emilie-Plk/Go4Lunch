package com.emplk.go4lunch.domain.restaurant_choice;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class RemoveUserRestaurantChoiceUseCaseTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase = mock(GetCurrentLoggedUserUseCase.class);

    private RemoveUserRestaurantChoiceUseCase removeUserRestaurantChoiceUseCase;

    @Before
    public void setUp() {
        doReturn(Stubs.getTestLoggedUserEntity()).when(getCurrentLoggedUserUseCase).invoke();
        removeUserRestaurantChoiceUseCase = new RemoveUserRestaurantChoiceUseCase(userRepository, getCurrentLoggedUserUseCase);
    }

    @Test
    public void testInvoke() {
        // When
        removeUserRestaurantChoiceUseCase.invoke();

        // Then
        verify(getCurrentLoggedUserUseCase).invoke();
        verify(userRepository).deleteUserRestaurantChoice(
            Stubs.getTestLoggedUserEntity()
        );
        verifyNoMoreInteractions(userRepository, getCurrentLoggedUserUseCase);
    }
}