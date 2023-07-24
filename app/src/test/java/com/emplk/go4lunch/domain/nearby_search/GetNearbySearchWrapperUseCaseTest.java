package com.emplk.go4lunch.domain.nearby_search;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GetNearbySearchWrapperUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final NearbySearchRepository nearbySearchRepository = mock(NearbySearchRepository.class);

    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase = mock(GetCurrentLocationStateUseCase.class);

    private GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase;

    @Before
    public void setUp() {
        getNearbySearchWrapperUseCase = new GetNearbySearchWrapperUseCase(nearbySearchRepository, getCurrentLocationStateUseCase);
    }

    @Test
    public void testInvoke_withLocationStateEntitySuccess() {
        // Given
        LocationEntity location = ((LocationStateEntity.Success) Stubs.getTestLocationStateEntitySuccess()).locationEntity;

        MutableLiveData<NearbySearchWrapper> nearbySearchWrapperMutableLiveData = new MutableLiveData<>();
        NearbySearchWrapper nearbySearchWrapper = mock(NearbySearchWrapper.Success.class);
        nearbySearchWrapperMutableLiveData.setValue(nearbySearchWrapper);
        doReturn(nearbySearchWrapperMutableLiveData).when(nearbySearchRepository).getNearbyRestaurants(location.getLatitude(), location.getLongitude(), Stubs.TEST_RESTAURANT_TYPE, Stubs.TEST_RESTAURANT_RADIUS);

        MutableLiveData<LocationStateEntity> locationStateEntityMutableLiveData = new MutableLiveData<>();
        locationStateEntityMutableLiveData.setValue(Stubs.getTestLocationStateEntitySuccess());
        doReturn(locationStateEntityMutableLiveData).when(getCurrentLocationStateUseCase).invoke();

        // When
        NearbySearchWrapper result = getValueForTesting(getNearbySearchWrapperUseCase.invoke());

        // Then
        assertTrue(result instanceof NearbySearchWrapper.Success);
        verify(getCurrentLocationStateUseCase).invoke();
        verify(nearbySearchRepository).getNearbyRestaurants(location.getLatitude(), location.getLongitude(), Stubs.TEST_RESTAURANT_TYPE, Stubs.TEST_RESTAURANT_RADIUS);
        verifyNoMoreInteractions(getCurrentLocationStateUseCase, nearbySearchRepository);
    }

    @Test
    public void testInvoke_withLocationStateEntityGpsProviderDisabled() {
        // Given
        MutableLiveData<LocationStateEntity> locationStateEntityMutableLiveData = new MutableLiveData<>();
        locationStateEntityMutableLiveData.setValue(Stubs.getTestLocationStateEntityGpsProviderDisabled());
        doReturn(locationStateEntityMutableLiveData).when(getCurrentLocationStateUseCase).invoke();

        // When
        NearbySearchWrapper result = getValueForTesting(getNearbySearchWrapperUseCase.invoke());

        // Then
        assertTrue(result instanceof NearbySearchWrapper.RequestError);
        verify(getCurrentLocationStateUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLocationStateUseCase, nearbySearchRepository);
    }
}