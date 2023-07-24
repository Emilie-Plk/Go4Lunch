package com.emplk.go4lunch.ui.restaurant_map;

import static com.emplk.util.TestUtil.getEmitCountForTesting;
import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.autocomplete.use_case.GetPredictionPlaceIdUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.domain.workmate.GetAttendantsGoingToSameRestaurantAsUserUseCase;
import com.emplk.go4lunch.ui.restaurant_map.map__marker.RestaurantMarkerViewStateItem;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapViewModelTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final IsGpsEnabledUseCase isGpsEnabledUseCase = mock(IsGpsEnabledUseCase.class);
    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase = mock(GetCurrentLocationStateUseCase.class);

    private final GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase = mock(GetNearbySearchWrapperUseCase.class);

    private final GetAttendantsGoingToSameRestaurantAsUserUseCase getAttendantsGoingToSameRestaurantAsUserUseCase = mock(GetAttendantsGoingToSameRestaurantAsUserUseCase.class);


    private final GetPredictionPlaceIdUseCase getPredictionPlaceIdUseCase = mock(GetPredictionPlaceIdUseCase.class);
    private final MutableLiveData<NearbySearchWrapper> nearbySearchWrapperMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<LocationStateEntity> locationStateEntityMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<Map<String, Integer>> restaurantIdWithAttendantsMapMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<Boolean> isGpsEnabledMutableLiveData = new MutableLiveData<>();

    MutableLiveData<String> predictionIdLiveData = new MutableLiveData<>();

    private MapViewModel mapViewModel;

    @Before
    public void setUp() {
        NearbySearchWrapper nearbySearchWrapper = new NearbySearchWrapper.Success(Stubs.getTestNearbySearchEntityList(4));
        nearbySearchWrapperMutableLiveData.setValue(nearbySearchWrapper);
        doReturn(nearbySearchWrapperMutableLiveData).when(getNearbySearchWrapperUseCase).invoke();

        LocationStateEntity locationStateEntity = Stubs.getTestLocationStateEntitySuccess();
        locationStateEntityMutableLiveData.setValue(locationStateEntity);
        doReturn(locationStateEntityMutableLiveData).when(getCurrentLocationStateUseCase).invoke();

        isGpsEnabledMutableLiveData.setValue(true);
        doReturn(isGpsEnabledMutableLiveData).when(isGpsEnabledUseCase).invoke();

        Map<String, Integer> restaurantIdWithAttendantsMap = new HashMap<>();
        restaurantIdWithAttendantsMap.put(Stubs.TEST_NEARBYSEARCH_ID + 0, 5);
        restaurantIdWithAttendantsMap.put(Stubs.TEST_NEARBYSEARCH_ID + 1, 3);
        restaurantIdWithAttendantsMap.put(Stubs.TEST_NEARBYSEARCH_ID + 2, 1);
        restaurantIdWithAttendantsMapMutableLiveData.setValue(restaurantIdWithAttendantsMap);
        doReturn(restaurantIdWithAttendantsMapMutableLiveData).when(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();


        predictionIdLiveData.setValue(null);
        doReturn(predictionIdLiveData).when(getPredictionPlaceIdUseCase).invoke();

        mapViewModel = new MapViewModel(
            isGpsEnabledUseCase,
            getCurrentLocationStateUseCase,
            getNearbySearchWrapperUseCase,
            getAttendantsGoingToSameRestaurantAsUserUseCase,
            getPredictionPlaceIdUseCase
        );
    }

    @Test
    public void nominal_case() {
        // When
        List<RestaurantMarkerViewStateItem> result = getValueForTesting(mapViewModel.getMapViewState());
        int emitCountNoRestaurantFound = getEmitCountForTesting(mapViewModel.getNoRestaurantFoundSingleLiveEvent());
        int emitCountNoMatch = getEmitCountForTesting(mapViewModel.getNoRestaurantMatchSingleLiveEvent());

        // Then
        assertEquals(4, result.size());
        assertEquals(0, emitCountNoRestaurantFound);
        assertEquals(0, emitCountNoMatch);
        assertEquals(Stubs.TEST_NEARBYSEARCH_ID + 0, result.get(0).getId());
        assertEquals(Stubs.TEST_NEARBYSEARCH_ID + 1, result.get(1).getId());
        assertEquals(Stubs.TEST_NEARBYSEARCH_ID + 2, result.get(2).getId());
        assertEquals(Stubs.TEST_NEARBYSEARCH_ID + 3, result.get(3).getId());

        verify(getCurrentLocationStateUseCase).invoke();
        verify(isGpsEnabledUseCase).invoke();
        verify(getNearbySearchWrapperUseCase).invoke();
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLocationStateUseCase, isGpsEnabledUseCase, getNearbySearchWrapperUseCase, getAttendantsGoingToSameRestaurantAsUserUseCase);
    }

    @Test
    public void nearbySearchWrapperLoading_shouldReturnNoRestaurantMarkerViewState() {
        // Given
        NearbySearchWrapper nearbySearchWrapper = new NearbySearchWrapper.Loading();
        nearbySearchWrapperMutableLiveData.setValue(nearbySearchWrapper);

        // When
        List<RestaurantMarkerViewStateItem> result = getValueForTesting(mapViewModel.getMapViewState());

        // Then
        assertNull(result);
        verify(getCurrentLocationStateUseCase).invoke();
        verify(isGpsEnabledUseCase).invoke();
        verify(getNearbySearchWrapperUseCase).invoke();
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLocationStateUseCase, isGpsEnabledUseCase, getNearbySearchWrapperUseCase, getAttendantsGoingToSameRestaurantAsUserUseCase);
    }

    @Test
    public void gpsEnabledNull_shouldReturn() {
        // Given
        isGpsEnabledMutableLiveData.setValue(null);

        // When
        List<RestaurantMarkerViewStateItem> result = getValueForTesting(mapViewModel.getMapViewState());

        // Then
        assertNull(result);
    }

    @Test
    public void nearbySearchWrapperNull_shouldReturn() {
        // Given
        nearbySearchWrapperMutableLiveData.setValue(null);

        // When
        List<RestaurantMarkerViewStateItem> result = getValueForTesting(mapViewModel.getMapViewState());

        // Then
        assertNull(result);
    }

    @Test
    public void locationStateEntitySuccess_shouldReturnSuccess() {
        // When
        LocationStateEntity result = getValueForTesting(mapViewModel.getLocationState());

        // Then
        assertTrue(result instanceof LocationStateEntity.Success);
    }

    @Test
    public void locationStateEntityGpsProviderDisabled_shouldReturnGpsProviderDisabled() {
        // Given
        LocationStateEntity locationStateEntity = new LocationStateEntity.GpsProviderDisabled();
        locationStateEntityMutableLiveData.setValue(locationStateEntity);

        // When
        LocationStateEntity result = getValueForTesting(mapViewModel.getLocationState());

        // Then
        assertTrue(result instanceof LocationStateEntity.GpsProviderDisabled);
    }

    @Test
    public void predictionPlaceIdIsNotNull_shouldReturnMatchingMarkerViewState() {
        // Given
        predictionIdLiveData.setValue(Stubs.TEST_PREDICTION_ID + 0);

        // When
        List<RestaurantMarkerViewStateItem> result = getValueForTesting(mapViewModel.getMapViewState());

        // Then
        assertEquals(1, result.size());
        assertEquals(Stubs.TEST_NEARBYSEARCH_ID + 0, result.get(0).getId());
        verify(getCurrentLocationStateUseCase).invoke();
        verify(isGpsEnabledUseCase).invoke();
        verify(getNearbySearchWrapperUseCase).invoke();
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verify(getPredictionPlaceIdUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLocationStateUseCase, isGpsEnabledUseCase, getNearbySearchWrapperUseCase, getAttendantsGoingToSameRestaurantAsUserUseCase, getPredictionPlaceIdUseCase);
    }

    @Test
    public void nearbySearchWrapperNoResult_shouldTriggerNoRestaurantFoundSingleLiveEvent() {
        // Given
        nearbySearchWrapperMutableLiveData.setValue(new NearbySearchWrapper.NoResults());
        // When
        List<RestaurantMarkerViewStateItem> result = getValueForTesting(mapViewModel.getMapViewState());
        int emitCount = getEmitCountForTesting(mapViewModel.getNoRestaurantFoundSingleLiveEvent());


        // Then
        assertEquals(0, result.size());
        assertEquals(1, emitCount);
        verify(getCurrentLocationStateUseCase).invoke();
        verify(isGpsEnabledUseCase).invoke();
        verify(getNearbySearchWrapperUseCase).invoke();
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verify(getPredictionPlaceIdUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLocationStateUseCase, isGpsEnabledUseCase, getNearbySearchWrapperUseCase, getAttendantsGoingToSameRestaurantAsUserUseCase, getPredictionPlaceIdUseCase);
    }

    @Test
    public void predictionPlaceIdNotMatching_shouldTriggerNoRestaurantMatchSingleLiveEvent() {
        // Given
        predictionIdLiveData.setValue(Stubs.TEST_PREDICTION_ID + 123456789);
        // When
        List<RestaurantMarkerViewStateItem> result = getValueForTesting(mapViewModel.getMapViewState());
        int emitCount = getEmitCountForTesting(mapViewModel.getNoRestaurantMatchSingleLiveEvent());


        // Then
        assertEquals(0, result.size());
        assertEquals(1, emitCount);
        verify(getCurrentLocationStateUseCase).invoke();
        verify(isGpsEnabledUseCase).invoke();
        verify(getNearbySearchWrapperUseCase).invoke();
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verify(getPredictionPlaceIdUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLocationStateUseCase, isGpsEnabledUseCase, getNearbySearchWrapperUseCase, getAttendantsGoingToSameRestaurantAsUserUseCase, getPredictionPlaceIdUseCase);
    }
}

