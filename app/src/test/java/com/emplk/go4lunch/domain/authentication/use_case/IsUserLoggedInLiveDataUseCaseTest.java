package com.emplk.go4lunch.domain.authentication.use_case;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.AuthRepository;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IsUserLoggedInLiveDataUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final AuthRepository authRepository = mock(AuthRepository.class);

    private IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase;

    @Before
    public void setUp() {
        isUserLoggedInLiveDataUseCase = new IsUserLoggedInLiveDataUseCase(authRepository);
    }

    @Test
    public void user_is_logged_in() {
        // Given
        MutableLiveData<Boolean> isUserLoggedLiveData = new MutableLiveData<>(true);
        doReturn(isUserLoggedLiveData).when(authRepository).isUserLoggedLiveData();

        // When
        Boolean result = getValueForTesting(isUserLoggedInLiveDataUseCase.invoke());

        // Then
        assertTrue(result);
        verify(authRepository).isUserLoggedLiveData();

        verifyNoMoreInteractions(authRepository);
    }

    @Test
    public void user_is_logged_out() {
        // Given
        MutableLiveData<Boolean> isUserLoggedLiveData = new MutableLiveData<>(false);
        doReturn(isUserLoggedLiveData).when(authRepository).isUserLoggedLiveData();

        // When
        Boolean result = getValueForTesting(isUserLoggedInLiveDataUseCase.invoke());

        // Then
        assertFalse(result);
        verify(authRepository).isUserLoggedLiveData();
        verifyNoMoreInteractions(authRepository);
    }
}