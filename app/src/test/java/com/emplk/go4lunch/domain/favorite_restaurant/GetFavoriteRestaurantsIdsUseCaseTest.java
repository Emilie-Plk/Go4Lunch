package com.emplk.go4lunch.domain.favorite_restaurant;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Set;

public class GetFavoriteRestaurantsIdsUseCaseTest {

    @Rule
    public
    InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final FavoriteRestaurantRepository favoriteRestaurantRepository = mock(FavoriteRestaurantRepository.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private GetFavoriteRestaurantsIdsUseCase getFavoriteRestaurantsIdsUseCase;

    @Before
    public void setUp() {
        doReturn(Stubs.TEST_USER_ID).when(getCurrentLoggedUserIdUseCase).invoke();

        MutableLiveData<Set<String>> restaurantIds = new MutableLiveData<>();
        restaurantIds.setValue(Stubs.getTestRestaurantIdSet(5));
        doReturn(restaurantIds).when(favoriteRestaurantRepository).getUserFavoriteRestaurantIdsLiveData(Stubs.TEST_USER_ID);

        getFavoriteRestaurantsIdsUseCase = new GetFavoriteRestaurantsIdsUseCase(
            favoriteRestaurantRepository,
            getCurrentLoggedUserIdUseCase
        );
    }

    @Test
    public void testInvoke() {
        // When
        getFavoriteRestaurantsIdsUseCase.invoke();
        // Then
        verify(favoriteRestaurantRepository).getUserFavoriteRestaurantIdsLiveData(getCurrentLoggedUserIdUseCase.invoke());
        verifyNoMoreInteractions(favoriteRestaurantRepository);
    }
}