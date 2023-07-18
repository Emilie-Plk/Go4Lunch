package com.emplk.go4lunch.domain.workmate;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetWorkmateEntitiesWithRestaurantChoiceListUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final UserRepository userRepository = mock(UserRepository.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private final GetLoggedUserEntitiesUseCase getLoggedUserEntitiesUseCase = mock(GetLoggedUserEntitiesUseCase.class);

    private GetWorkmateEntitiesWithRestaurantChoiceListUseCase getWorkmateEntitiesWithRestaurantChoiceListUseCase;

    @Before
    public void setUp() {
        List<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntities = new ArrayList<>();
        UserWithRestaurantChoiceEntity userWithRestaurantChoice1 = new UserWithRestaurantChoiceEntity(
            Stubs.TEST_USER_ID + 1,
            Stubs.TIMESTAMP,
            Stubs.TEST_USER_NAME + 1,
            Stubs.TEST_RESTAURANT_ID,
            Stubs.TEST_RESTAURANT_NAME,
            Stubs.TEST_RESTAURANT_VICINITY
        );

        UserWithRestaurantChoiceEntity userWithRestaurantChoice2 = new UserWithRestaurantChoiceEntity(
            Stubs.TEST_USER_ID + 2,
            Stubs.TIMESTAMP,
            Stubs.TEST_USER_NAME + 2,
            Stubs.TEST_RESTAURANT_ID,
            Stubs.TEST_RESTAURANT_NAME,
            Stubs.TEST_RESTAURANT_VICINITY
        );
        UserWithRestaurantChoiceEntity currentUserWithRestaurantChoice = Stubs.getTestCurrentUserWithRestaurantChoiceEntity();
        userWithRestaurantChoiceEntities.add(userWithRestaurantChoice1);
        userWithRestaurantChoiceEntities.add(userWithRestaurantChoice2);
        userWithRestaurantChoiceEntities.add(currentUserWithRestaurantChoice);
        MutableLiveData<List<UserWithRestaurantChoiceEntity>> userWithRestaurantChoiceEntitiesLiveData = new MutableLiveData<>();
        userWithRestaurantChoiceEntitiesLiveData.setValue(userWithRestaurantChoiceEntities);
        doReturn(userWithRestaurantChoiceEntitiesLiveData).when(userRepository).getUsersWithRestaurantChoiceEntities();

        String currentLoggedUserId = Stubs.TEST_USER_ID;
        doReturn(currentLoggedUserId).when(getCurrentLoggedUserIdUseCase).invoke();

        List<LoggedUserEntity> loggedUserEntities = Stubs.getFourTestLoggedUserEntities();
        MutableLiveData<List<LoggedUserEntity>> loggedUserEntitiesLiveData = new MutableLiveData<>();
        loggedUserEntitiesLiveData.setValue(loggedUserEntities);
        doReturn(loggedUserEntitiesLiveData).when(getLoggedUserEntitiesUseCase).invoke();

        getWorkmateEntitiesWithRestaurantChoiceListUseCase = new GetWorkmateEntitiesWithRestaurantChoiceListUseCase(
            userRepository,
            getCurrentLoggedUserIdUseCase,
            getLoggedUserEntitiesUseCase
        );
    }

    @Test
    public void testInvoke() {
        // When
        List<WorkmateEntity> result = getValueForTesting(getWorkmateEntitiesWithRestaurantChoiceListUseCase.invoke());

        // Then
        assertEquals(2, result.size());
        assert result.get(0).getAttendingRestaurantName() != null;
        assertEquals(Stubs.TEST_RESTAURANT_NAME, result.get(0).getAttendingRestaurantName());
        assertEquals(Stubs.TEST_USER_NAME, result.get(0).getLoggedUserEntity().getName());

        verify(userRepository).getUsersWithRestaurantChoiceEntities();
        verify(getCurrentLoggedUserIdUseCase).invoke();
        verify(getLoggedUserEntitiesUseCase).invoke();
        verifyNoMoreInteractions(userRepository, getCurrentLoggedUserIdUseCase, getLoggedUserEntitiesUseCase);
    }
}