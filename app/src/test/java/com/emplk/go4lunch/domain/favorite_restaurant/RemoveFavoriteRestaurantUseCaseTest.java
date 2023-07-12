package com.emplk.go4lunch.domain.favorite_restaurant;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Test;

public class RemoveFavoriteRestaurantUseCaseTest {

    private final FavoriteRestaurantRepository favoriteRestaurantRepository = mock(FavoriteRestaurantRepository.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private RemoveFavoriteRestaurantUseCase removeFavoriteRestaurantUseCase;

    @Before
    public void setUp() {
        doReturn(Stubs.TEST_LOGGED_USER_ENTITY_ID).when(getCurrentLoggedUserIdUseCase).invoke();

        removeFavoriteRestaurantUseCase = new RemoveFavoriteRestaurantUseCase(
            favoriteRestaurantRepository,
            getCurrentLoggedUserIdUseCase
        );
    }

    @Test
    public void testInvoke() {
        // When
        removeFavoriteRestaurantUseCase.invoke(Stubs.TEST_RESTAURANT_ID);
        // Then
        verify(favoriteRestaurantRepository).removeFavoriteRestaurant(Stubs.TEST_LOGGED_USER_ENTITY_ID, Stubs.TEST_RESTAURANT_ID);
        verifyNoMoreInteractions(favoriteRestaurantRepository);
    }

}