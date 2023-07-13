package com.emplk.go4lunch.domain.favorite_restaurant;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class AddFavoriteRestaurantUseCaseTest {

    private final FavoriteRestaurantRepository favoriteRestaurantRepository = mock(FavoriteRestaurantRepository.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private AddFavoriteRestaurantUseCase addFavoriteRestaurantUseCase;

    @Before
    public void setUp() {
        doReturn(Stubs.TEST_USER_ID).when(getCurrentLoggedUserIdUseCase).invoke();

        addFavoriteRestaurantUseCase = new AddFavoriteRestaurantUseCase(
            favoriteRestaurantRepository,
            getCurrentLoggedUserIdUseCase
        );
    }

    @Test
    public void testInvoke() {
        // When
        addFavoriteRestaurantUseCase.invoke(Stubs.TEST_RESTAURANT_ID);
        // Then
        verify(favoriteRestaurantRepository).addFavoriteRestaurant(Stubs.TEST_USER_ID, Stubs.TEST_RESTAURANT_ID);
        verifyNoMoreInteractions(favoriteRestaurantRepository);
    }
}