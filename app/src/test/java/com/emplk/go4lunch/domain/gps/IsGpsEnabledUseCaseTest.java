package com.emplk.go4lunch.domain.gps;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class IsGpsEnabledUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    public final GpsLocationRepository gpsLocationRepository = mock(GpsLocationRepository.class);

    public final MutableLiveData<LocationStateEntity> locationStateEntityMutableLiveData = new MutableLiveData<>();

    public IsGpsEnabledUseCase isGpsEnabledUseCase;

    @Before
    public void setUp() {
        isGpsEnabledUseCase = new IsGpsEnabledUseCase(gpsLocationRepository);
    }

    @Test
    public void gpsProviderIsEnabled() {
        locationStateEntityMutableLiveData.setValue(Stubs.getTestLocationStateEntitySuccess());
        doReturn(locationStateEntityMutableLiveData).when(gpsLocationRepository).getLocationStateLiveData();
        Boolean result = getValueForTesting(isGpsEnabledUseCase.invoke());
        assertTrue(result);
    }

    @Test
    public void gpsProviderIsDisabled() {
        locationStateEntityMutableLiveData.setValue(Stubs.getTestLocationStateEntityGpsProviderDisabled());
        doReturn(locationStateEntityMutableLiveData).when(gpsLocationRepository).getLocationStateLiveData();
        Boolean result = getValueForTesting(isGpsEnabledUseCase.invoke());
        assertFalse(result);
    }
}