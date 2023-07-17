package com.emplk.go4lunch.domain.workmate;

import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;

import org.junit.Before;
import org.junit.Rule;

public class GetWorkmateEntitiesWithRestaurantChoiceListUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final UserRepository userRepository = mock(UserRepository.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);


    private final GetLoggedUserEntitiesUseCase getLoggedUserEntitiesUseCase = mock(GetLoggedUserEntitiesUseCase.class);

    private GetWorkmateEntitiesWithRestaurantChoiceListUseCase getWorkmateEntitiesWithRestaurantChoiceListUseCase;

    @Before
    public void setUp() {
        getWorkmateEntitiesWithRestaurantChoiceListUseCase = new GetWorkmateEntitiesWithRestaurantChoiceListUseCase(
            userRepository,
            getCurrentLoggedUserIdUseCase,
            getLoggedUserEntitiesUseCase
        );
    }
}