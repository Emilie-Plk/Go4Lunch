package com.emplk.go4lunch.ui.main;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInLiveDataUseCase;
import com.emplk.go4lunch.domain.authentication.use_case.LogoutUserUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.restaurant_choice.GetUserWithRestaurantChoiceEntityLiveDataUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.UserWithRestaurantChoiceEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class MainViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final LogoutUserUseCase logoutUserUseCase = mock(LogoutUserUseCase.class);

    private final IsGpsEnabledUseCase isGpsEnabledUseCase = mock(IsGpsEnabledUseCase.class);

    private final StartLocationRequestUseCase startLocationRequestUseCase = mock(StartLocationRequestUseCase.class);

    private final IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase = mock(IsUserLoggedInLiveDataUseCase.class);

    private final GetUserWithRestaurantChoiceEntityLiveDataUseCase getUserWithRestaurantChoiceEntityLiveDataUseCase = mock(GetUserWithRestaurantChoiceEntityLiveDataUseCase.class);

    private final GetUserEntityUseCase getUserEntityUseCase = mock(GetUserEntityUseCase.class);

    MutableLiveData<Boolean> isUserLoggedInMutableLiveData = new MutableLiveData<>();

    MutableLiveData<Boolean> isGpsEnabledMutableLiveData = new MutableLiveData<>();

    MutableLiveData<UserEntity> currentUserEntityMutableLiveData = new MutableLiveData<>();

    MutableLiveData<UserWithRestaurantChoiceEntity> userWithRestaurantChoiceEntityMutableLiveData = new MutableLiveData<>();

    private MainViewModel mainViewModel;

    @Before
    public void setUp() {
        doReturn(isUserLoggedInMutableLiveData).when(isUserLoggedInLiveDataUseCase).invoke();


        doReturn(isGpsEnabledMutableLiveData).when(isGpsEnabledUseCase).invoke();

        UserEntity userEntity = Stubs.getCurrentUserEntity();
        currentUserEntityMutableLiveData.setValue(userEntity);
        doReturn(currentUserEntityMutableLiveData).when(getUserEntityUseCase).invoke();

        UserWithRestaurantChoiceEntity userWithRestaurantChoiceEntity = Stubs.getTestCurrentUserWithRestaurantChoiceEntity();
        userWithRestaurantChoiceEntityMutableLiveData.setValue(userWithRestaurantChoiceEntity);
        doReturn(userWithRestaurantChoiceEntityMutableLiveData).when(getUserWithRestaurantChoiceEntityLiveDataUseCase).invoke();

        mainViewModel = new MainViewModel(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }

    @Test
    public void getUserInfo() {
        // When
        LoggedUserEntity result = getValueForTesting(mainViewModel.getUserInfoLiveData());

        // Then
        assertEquals(Stubs.getTestLoggedUserEntity(), result);
        verify(getUserEntityUseCase).invoke();
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }

    @Test
    public void onUserLogged_isLoggedIn() {
        // Given
        isUserLoggedInMutableLiveData.setValue(true);

        // When
        boolean result = getValueForTesting(mainViewModel.onUserLogged());

        // Then
        assertTrue(result);
    }

    @Test
    public void onUserLogged_isLoggedOut() {
        // Given
        isUserLoggedInMutableLiveData.setValue(false);

        // When
        boolean result = getValueForTesting(mainViewModel.onUserLogged());

        // Then
        assertFalse(result);
    }


    @Test
    public void getUserWithRestaurantChoice() {
        // When
        UserWithRestaurantChoiceEntity result = getValueForTesting(mainViewModel.getUserWithRestaurantChoice());

        assertEquals(Stubs.getTestCurrentUserWithRestaurantChoiceEntity(), result);
        assertEquals(Stubs.TEST_USER_ID, result.getId());
        assertEquals(Stubs.ATTENDING_RESTAURANT_ID, result.getAttendingRestaurantId());
        verify(getUserWithRestaurantChoiceEntityLiveDataUseCase).invoke();
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }

    @Test
    public void gpsIsEnabled() {
        // Given
        isGpsEnabledMutableLiveData.setValue(true);

        // When
        boolean result = getValueForTesting(mainViewModel.isGpsEnabledLiveData());

        // Then
        assertTrue(result);
        verify(isGpsEnabledUseCase).invoke();
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }


    @Test
    public void gpsIsDisabled() {
        // Given
        isGpsEnabledMutableLiveData.setValue(false);

        // When
        boolean result = getValueForTesting(mainViewModel.isGpsEnabledLiveData());

        // Then
        assertFalse(result);
        verify(isGpsEnabledUseCase).invoke();
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }


    @Test
    public void testSignOut() {
        // When
        mainViewModel.signOut();

        // Then
        verify(logoutUserUseCase).invoke();
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }

    @Test
    public void onGetFragmentState_initialState() {
        // When
        FragmentState result = getValueForTesting(mainViewModel.getFragmentStateSingleLiveEvent());

        // Then
        assertEquals(FragmentState.MAP_FRAGMENT, result);
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }

    @Test
    public void onGetFragmentState_listFragment() {
        // Given
        mainViewModel.onChangeFragmentView(FragmentState.LIST_FRAGMENT);
        // When
        FragmentState result = getValueForTesting(mainViewModel.getFragmentStateSingleLiveEvent());

        // Then
        assertEquals(FragmentState.LIST_FRAGMENT, result);
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }

    @Test
    public void onGetFragmentState_workmatesFragment() {
        // Given
        mainViewModel.onChangeFragmentView(FragmentState.WORKMATES_FRAGMENT);
        // When
        FragmentState result = getValueForTesting(mainViewModel.getFragmentStateSingleLiveEvent());

        // Then
        assertEquals(FragmentState.WORKMATES_FRAGMENT, result);
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }

    @Test
    public void onGetFragmentState_chatFragment() {
        // Given
        mainViewModel.onChangeFragmentView(FragmentState.CHAT_FRAGMENT);
        // When
        FragmentState result = getValueForTesting(mainViewModel.getFragmentStateSingleLiveEvent());

        // Then
        assertEquals(FragmentState.CHAT_FRAGMENT, result);
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }

    @Test
    public void onResume() {
        // When
        mainViewModel.onResume();

        // Then
        verify(startLocationRequestUseCase).invoke();
        verifyNoMoreInteractions(
            logoutUserUseCase,
            isGpsEnabledUseCase,
            startLocationRequestUseCase,
            isUserLoggedInLiveDataUseCase,
            getUserWithRestaurantChoiceEntityLiveDataUseCase,
            getUserEntityUseCase
        );
    }
}