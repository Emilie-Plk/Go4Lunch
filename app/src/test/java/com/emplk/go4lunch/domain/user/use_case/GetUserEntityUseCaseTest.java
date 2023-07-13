package com.emplk.go4lunch.domain.user.use_case;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInLiveDataUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.GetFavoriteRestaurantsIdsUseCase;
import com.emplk.go4lunch.domain.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

public class GetUserEntityUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final GetFavoriteRestaurantsIdsUseCase getFavoriteRestaurantsIdsUseCase = mock(GetFavoriteRestaurantsIdsUseCase.class);

    private final GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase = mock(GetUserWithRestaurantChoiceEntityLiveDataUseCase.class);

    private final IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase = mock(IsUserLoggedInLiveDataUseCase.class);

    private final AuthRepository authRepository = mock(AuthRepository.class);

    private GetUserEntityUseCase getUserEntityUseCase;

    @Before
    public void setUp() {
        MutableLiveData<Set<String>> favoriteRestaurantIdsMutableLiveData = new MutableLiveData<>();
        Set<String> favoriteRestaurantsIds = new HashSet<>();
        favoriteRestaurantsIds.add("favoriteRestaurant1");
        favoriteRestaurantsIds.add("favoriteRestaurant2");
        favoriteRestaurantsIds.add("favoriteRestaurant3");
        favoriteRestaurantIdsMutableLiveData.setValue(favoriteRestaurantsIds);
        doReturn(favoriteRestaurantIdsMutableLiveData).when(getFavoriteRestaurantsIdsUseCase).invoke();


        MutableLiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityMutableLiveData = new MutableLiveData<>();
        UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity = Stubs.getTestCurrentUserWithRestaurantChoiceEntity();
        userWithRestaurantChoiceEntityMutableLiveData.setValue(userWithRestaurantChoiceEntity);
        doReturn(userWithRestaurantChoiceEntityMutableLiveData).when(getUserWithRestaurantChoiceEntityLiveDataUseCase).invoke();

        MutableLiveData<Boolean> isUserLoggedInMutableLiveData = new MutableLiveData<>();
        isUserLoggedInMutableLiveData.setValue(true);
        doReturn(isUserLoggedInMutableLiveData).when(isUserLoggedInLiveDataUseCase).invoke();

        MutableLiveData<LoggedUserEntity> loggedUserEntityMutableLiveData = new MutableLiveData<>();
        loggedUserEntityMutableLiveData.setValue(Stubs.getTestLoggedUserEntity());
        doReturn(loggedUserEntityMutableLiveData).when(authRepository).getLoggedUserLiveData();


        getUserEntityUseCase = new GetUserEntityUseCase(
            getFavoriteRestaurantsIdsUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            isUserLoggedInLiveDataUseCase,
            authRepository
        );
    }

    @Test
    public void invoke() {
        // Given
        Set<String> favoriteRestaurantsIds = new HashSet<>();
        favoriteRestaurantsIds.add("favoriteRestaurant1");
        favoriteRestaurantsIds.add("favoriteRestaurant2");
        favoriteRestaurantsIds.add("favoriteRestaurant3");


        UserEntity expectedUserEntity = new UserEntity(
            Stubs.getTestLoggedUserEntity(),
            favoriteRestaurantsIds,
            Stubs.getTestCurrentUserWithRestaurantChoiceEntity().getAttendingRestaurantId()
        );

        // When
        UserEntity result = getValueForTesting(getUserEntityUseCase.invoke());

        // Then
        assertEquals(expectedUserEntity, result);

        verify(getFavoriteRestaurantsIdsUseCase).invoke();
        verify(getUserWithRestaurantChoiceEntityLiveDataUseCase).invoke();
        verify(isUserLoggedInLiveDataUseCase).invoke();
        verify(authRepository).getLoggedUserLiveData();
        verifyNoMoreInteractions(getFavoriteRestaurantsIdsUseCase, getUserWithRestaurantChoiceEntityLiveDataUseCase, isUserLoggedInLiveDataUseCase, authRepository);
    }

}