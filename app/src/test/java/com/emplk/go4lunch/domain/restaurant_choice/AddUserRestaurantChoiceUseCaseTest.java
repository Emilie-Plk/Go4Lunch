package com.emplk.go4lunch.domain.restaurant_choice;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.user.ChosenRestaurantEntity;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class AddUserRestaurantChoiceUseCaseTest {

    private final UserRepository userRepository = mock(UserRepository.class);

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase = mock(GetCurrentLoggedUserUseCase.class);

    private AddUserRestaurantChoiceUseCase addUserRestaurantChoiceUseCase;

    @Before
    public void setUp() {
        doReturn(Stubs.getTestLoggedUserEntity()).when(getCurrentLoggedUserUseCase).invoke();
        addUserRestaurantChoiceUseCase = new AddUserRestaurantChoiceUseCase(userRepository, getCurrentLoggedUserUseCase);
    }

    @Test
    public void invoke() {
        // When
        addUserRestaurantChoiceUseCase.invoke(
            Stubs.TEST_RESTAURANT_ID,
            Stubs.TEST_RESTAURANT_NAME,
            Stubs.TEST_RESTAURANT_VICINITY,
            Stubs.TEST_NEARBYSEARCH_PICTURE_URL
        );

        // Then
        verify(getCurrentLoggedUserUseCase).invoke();
        verify(userRepository).upsertUserRestaurantChoice(
            Stubs.getTestLoggedUserEntity(),
            new ChosenRestaurantEntity(
                null,
                Stubs.TEST_RESTAURANT_ID,
                Stubs.TEST_RESTAURANT_NAME,
                Stubs.TEST_RESTAURANT_VICINITY,
                Stubs.TEST_NEARBYSEARCH_PICTURE_URL
            )
        );
        verifyNoMoreInteractions(userRepository, getCurrentLoggedUserUseCase);
    }
}
