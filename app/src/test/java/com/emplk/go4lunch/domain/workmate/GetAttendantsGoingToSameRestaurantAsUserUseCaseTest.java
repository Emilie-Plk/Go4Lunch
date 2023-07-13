package com.emplk.go4lunch.domain.workmate;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetAttendantsGoingToSameRestaurantAsUserUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private final UserRepository userRepository = mock(UserRepository.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    private GetAttendantsGoingToSameRestaurantAsUserUseCase getAttendantsGoingToSameRestaurantAsUserUseCase;

    @Before
    public void setUp() {
        MutableLiveData<List<UserWithRestaurantChoiceEntity>> userWithRestaurantChoiceEntitiesLiveData = new MutableLiveData<>();
        List<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntities = new ArrayList<>();
        UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity1 = Stubs.getTestUserWithSameRestaurantChoiceEntity();
        UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity2 = Stubs.getTestUserWithSameRestaurantChoiceEntity();
        UserWithRestaurantChoiceEntity currentUserWithRestaurantChoiceEntity = Stubs.getTestCurrentUserWithRestaurantChoiceEntity();
        userWithRestaurantChoiceEntities.add(userWithRestaurantChoiceEntity1);
        userWithRestaurantChoiceEntities.add(userWithRestaurantChoiceEntity2);
        userWithRestaurantChoiceEntities.add(currentUserWithRestaurantChoiceEntity);

        userWithRestaurantChoiceEntitiesLiveData.setValue(userWithRestaurantChoiceEntities);
        doReturn(userWithRestaurantChoiceEntitiesLiveData).when(userRepository).getUsersWithRestaurantChoiceEntities();

        doReturn(Stubs.TEST_USER_ID).when(getCurrentLoggedUserIdUseCase).invoke();

        getAttendantsGoingToSameRestaurantAsUserUseCase = new GetAttendantsGoingToSameRestaurantAsUserUseCase(userRepository, getCurrentLoggedUserIdUseCase);
    }

    @Test
    public void testInvoke() {
        // When
        Map<String, Integer> result = getValueForTesting(getAttendantsGoingToSameRestaurantAsUserUseCase.invoke());

        //Then
        Integer resultCount = result.get(Stubs.ATTENDING_RESTAURANT_ID);
        assertTrue(result.containsKey(Stubs.ATTENDING_RESTAURANT_ID));
        assert resultCount != null;
        assertEquals(2, resultCount.intValue());

        verify(userRepository).getUsersWithRestaurantChoiceEntities();
        verify(getCurrentLoggedUserIdUseCase).invoke();
        verifyNoMoreInteractions(userRepository, getCurrentLoggedUserIdUseCase);
    }
}