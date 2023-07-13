package com.emplk.go4lunch.domain.permission;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class HasGpsPermissionUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final GpsPermissionRepository gpsPermissionRepository = mock(GpsPermissionRepository.class);

    private HasGpsPermissionUseCase hasGpsPermissionUseCase;

    @Before
    public void setUp() {
        hasGpsPermissionUseCase = new HasGpsPermissionUseCase(gpsPermissionRepository);
    }

    @Test
    public void hasGpsPermission() {
        // Given
        MutableLiveData<Boolean> hasGpsPermissionLiveData = new MutableLiveData<>(true);
        doReturn(hasGpsPermissionLiveData).when(gpsPermissionRepository).hasGpsPermissionLiveData();

        // When
        Boolean result = getValueForTesting(hasGpsPermissionUseCase.invoke());

        // Then
        assertTrue(result);
    }

    @Test
    public void hasNotGpsPermission() {
        // Given
        MutableLiveData<Boolean> hasGpsPermissionLiveData = new MutableLiveData<>(false);
        doReturn(hasGpsPermissionLiveData).when(gpsPermissionRepository).hasGpsPermissionLiveData();

        // When
        Boolean result = getValueForTesting(hasGpsPermissionUseCase.invoke());

        // Then
        assertFalse(result);
    }
}