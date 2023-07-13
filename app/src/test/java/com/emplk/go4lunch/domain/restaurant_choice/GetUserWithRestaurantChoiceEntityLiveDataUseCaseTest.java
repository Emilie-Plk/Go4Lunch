package com.emplk.go4lunch.domain.restaurant_choice;

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
import com.emplk.go4lunch.domain.user.UserRepository;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GetUserWithRestaurantChoiceEntityLiveDataUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final UserRepository userRepository = mock(UserRepository.class);

    private final AuthRepository authRepository = mock(AuthRepository.class);

    private GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase;

    @Before
    public void setUp() {
        MutableLiveData<LoggedUserEntity> currentLoggedUserMutableLiveData = new MutableLiveData<>();
        currentLoggedUserMutableLiveData.setValue(Stubs.getTestLoggedUserEntity());
        doReturn(currentLoggedUserMutableLiveData).when(authRepository).getLoggedUserLiveData();

        MutableLiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityMutableLiveData = new MutableLiveData<>();
        userWithRestaurantChoiceEntityMutableLiveData.setValue(Stubs.getTestCurrentUserWithRestaurantChoiceEntity());
        doReturn(userWithRestaurantChoiceEntityMutableLiveData).when(userRepository).getUserWithRestaurantChoiceEntity(Stubs.TEST_USER_ID);


        getUserWithRestaurantChoiceEntityLiveDataUseCase = new GetUserWithRestaurantChoiceEntityLiveDataUseCase(
            userRepository,
            authRepository
        );
    }

    @Test
    public void testInvoke() {
        // When
        UserWithRestaurantChoiceEntity result = getValueForTesting(getUserWithRestaurantChoiceEntityLiveDataUseCase.invoke());

        // Then
        assertEquals(Stubs.getTestCurrentUserWithRestaurantChoiceEntity(), result);
        verify(userRepository).getUserWithRestaurantChoiceEntity(Stubs.TEST_USER_ID);
        verify(authRepository).getLoggedUserLiveData();
        verifyNoMoreInteractions(userRepository, authRepository);
    }
}