package com.emplk.go4lunch.ui.restaurant_list;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import android.content.res.Resources;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.go4lunch.domain.nearby_search.GetNearbySearchWrapperUseCase;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchWrapper;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;
import com.emplk.go4lunch.domain.workmate.GetAttendantsGoingToSameRestaurantAsUserUseCase;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantListViewModelTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final static String ERROR_MESSAGE = "ERROR_MESSAGE";

    private final static String NO_RESULT = "NO_RESULT";

    private static final String IMAGE_URL = "IMAGE_URL";

    private final GetNearbySearchWrapperUseCase getNearbySearchWrapperUseCase = mock(GetNearbySearchWrapperUseCase.class);

    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase = mock(GetCurrentLocationStateUseCase.class);

    private final HasGpsPermissionUseCase hasGpsPermissionUseCase = mock(HasGpsPermissionUseCase.class);

    private final IsGpsEnabledUseCase isGpsEnabledUseCase = mock(IsGpsEnabledUseCase.class);

    private final Resources resources = mock(Resources.class);

    private final GetAttendantsGoingToSameRestaurantAsUserUseCase getAttendantsGoingToSameRestaurantAsUserUseCase = mock(GetAttendantsGoingToSameRestaurantAsUserUseCase.class);

    private final MutableLiveData<Boolean> isGpsEnabledLiveData = new MutableLiveData<>();

    private final MutableLiveData<Boolean> hasGpsPermissionLiveData = new MutableLiveData<>();

    private final MutableLiveData<NearbySearchWrapper> nearbySearchWrapperLiveData = new MutableLiveData<>();

    MutableLiveData<LocationStateEntity> locationStateEntityMutableLiveData = new MutableLiveData<>();

    private final MutableLiveData<Map<String, Integer>> attendantsGoingToSameRestaurantAsUserLiveData = new MutableLiveData<>();

    private RestaurantListViewModel restaurantListViewModel;

    @Before
    public void setUp() {
        List<NearbySearchEntity> nearbySearchEntityList = Stubs.getTestNearbySearchEntityList(3);
        nearbySearchWrapperLiveData.setValue(new NearbySearchWrapper.Success(nearbySearchEntityList));
        doReturn(nearbySearchWrapperLiveData).when(getNearbySearchWrapperUseCase).invoke();

        locationStateEntityMutableLiveData.setValue(Stubs.getTestLocationStateEntitySuccess());
        doReturn(locationStateEntityMutableLiveData).when(getCurrentLocationStateUseCase).invoke();

        hasGpsPermissionLiveData.setValue(true);
        doReturn(hasGpsPermissionLiveData).when(hasGpsPermissionUseCase).invoke();

        isGpsEnabledLiveData.setValue(true);
        doReturn(isGpsEnabledLiveData).when(isGpsEnabledUseCase).invoke();

        doReturn(IMAGE_URL).when(resources).getString(R.string.google_image_url);
        doReturn(ERROR_MESSAGE).when(resources).getString(R.string.list_error_message_generic);
        doReturn(NO_RESULT).when(resources).getString(R.string.list_error_message_no_results);
        doReturn(ERROR_MESSAGE).when(resources).getString(R.string.list_error_message_no_gps);

        Map<String, Integer> attendantsByRestaurantIdsMap = new HashMap<>();
        attendantsByRestaurantIdsMap.put(Stubs.TEST_NEARBYSEARCH_ID + "1", 2);
        attendantsByRestaurantIdsMap.put("restaurantId2", 1);
        attendantsGoingToSameRestaurantAsUserLiveData.setValue(attendantsByRestaurantIdsMap);
        doReturn(attendantsGoingToSameRestaurantAsUserLiveData).when(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();

        restaurantListViewModel = new RestaurantListViewModel(
            getNearbySearchWrapperUseCase,
            getCurrentLocationStateUseCase,
            hasGpsPermissionUseCase,
            isGpsEnabledUseCase,
            resources,
            getAttendantsGoingToSameRestaurantAsUserUseCase
        );
    }

    @Test
    public void nominal_case() {
        // When
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertTrue(expectedViewStateItemList.get(0) instanceof RestaurantListViewStateItem.RestaurantItemItem);
        assertEquals(3, expectedViewStateItemList.size());
        verify(getNearbySearchWrapperUseCase).invoke();
        verify(getCurrentLocationStateUseCase).invoke();
        verify(hasGpsPermissionUseCase).invoke();
        verify(isGpsEnabledUseCase).invoke();
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verifyNoMoreInteractions(getNearbySearchWrapperUseCase, getCurrentLocationStateUseCase, hasGpsPermissionUseCase, isGpsEnabledUseCase, getAttendantsGoingToSameRestaurantAsUserUseCase);
    }

    @Test
    public void nearbySearchWrapperIsNull_shouldReturn() {
        // Given
        nearbySearchWrapperLiveData.setValue(null);

        // When
        List<RestaurantListViewStateItem> result = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertNull(result);
    }

    @Test
    public void locationStateIsNull_shouldReturn() {
        // Given
        locationStateEntityMutableLiveData.setValue(null);

        // When
        List<RestaurantListViewStateItem> result = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertNull(result);
    }

    @Test
    public void noGpsPermission_shouldReturnRestaurantListErrorItem() {
        // Given
        hasGpsPermissionLiveData.setValue(false);

        // When
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertTrue(expectedViewStateItemList.get(0) instanceof RestaurantListViewStateItem.RestaurantListErrorItem);
        assertEquals(ERROR_MESSAGE, ((RestaurantListViewStateItem.RestaurantListErrorItem) expectedViewStateItemList.get(0)).getErrorMessage());
        verify(hasGpsPermissionUseCase).invoke();
        verifyNoMoreInteractions(hasGpsPermissionUseCase);
    }

    @Test
    public void gpsProviderDisabled_shouldReturnRestaurantListErrorItem() {
        // Given
        locationStateEntityMutableLiveData.setValue(Stubs.getTestLocationStateEntityGpsProviderDisabled());

        // When
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertTrue(expectedViewStateItemList.get(0) instanceof RestaurantListViewStateItem.RestaurantListErrorItem);
        assertEquals(ERROR_MESSAGE, ((RestaurantListViewStateItem.RestaurantListErrorItem) expectedViewStateItemList.get(0)).getErrorMessage());
        verify(getCurrentLocationStateUseCase).invoke();
        verifyNoMoreInteractions(getCurrentLocationStateUseCase);
    }

    @Test
    public void gpsDisabled_shouldReturnRestaurantListErrorItem() {
        // Given
        isGpsEnabledLiveData.setValue(false);

        // When
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertTrue(expectedViewStateItemList.get(0) instanceof RestaurantListViewStateItem.RestaurantListErrorItem);
        assertEquals(ERROR_MESSAGE, ((RestaurantListViewStateItem.RestaurantListErrorItem) expectedViewStateItemList.get(0)).getErrorMessage());
        verify(isGpsEnabledUseCase).invoke();
        verifyNoMoreInteractions(isGpsEnabledUseCase);
    }

    @Test
    public void nearbySearchLoadingState_shouldReturnLoading() {
        // Given
        nearbySearchWrapperLiveData.setValue(new NearbySearchWrapper.Loading());

        // When
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertTrue(expectedViewStateItemList.get(0) instanceof RestaurantListViewStateItem.Loading);
        verify(getNearbySearchWrapperUseCase).invoke();
        verifyNoMoreInteractions(getNearbySearchWrapperUseCase);
    }

    @Test
    public void nearbySearchNoResults_shouldReturnLoading() {
        // Given
        nearbySearchWrapperLiveData.setValue(new NearbySearchWrapper.NoResults());

        // When
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertTrue(expectedViewStateItemList.get(0) instanceof RestaurantListViewStateItem.RestaurantListErrorItem);
        assertEquals(NO_RESULT, ((RestaurantListViewStateItem.RestaurantListErrorItem) expectedViewStateItemList.get(0)).getErrorMessage());
        verify(getNearbySearchWrapperUseCase).invoke();
        verifyNoMoreInteractions(getNearbySearchWrapperUseCase);
    }

    @Test
    public void nearbySearchRequestError_shouldReturnLoading() {
        // Given
        nearbySearchWrapperLiveData.setValue(new NearbySearchWrapper.RequestError(new Exception(ERROR_MESSAGE)));

        // When
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertTrue(expectedViewStateItemList.get(0) instanceof RestaurantListViewStateItem.RestaurantListErrorItem);
        assertEquals(ERROR_MESSAGE, ((RestaurantListViewStateItem.RestaurantListErrorItem) expectedViewStateItemList.get(0)).getErrorMessage());
        verify(getNearbySearchWrapperUseCase).invoke();
        verifyNoMoreInteractions(getNearbySearchWrapperUseCase);
    }

    @Test
    public void openingState_shouldReturnIsOpened() {
        // When
        NearbySearchEntity nearbySearchEntity = new NearbySearchEntity(
            Stubs.TEST_NEARBYSEARCH_ID,
            Stubs.TEST_NEARBYSEARCH_NAME,
            Stubs.TEST_RESTAURANT_VICINITY,
            Stubs.TEST_RESTAURANT_PHOTO_URL,
            Stubs.TEST_NEARBYSEARCH_RATING,
            Stubs.TEST_NEARBYSEARCH_LOCATION_ENTITY,
            Stubs.TEST_NEARBYSEARCH_DISTANCE,
            false
        );
        nearbySearchWrapperLiveData.setValue(new NearbySearchWrapper.Success(Collections.singletonList(nearbySearchEntity)));
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertEquals(RestaurantOpeningState.IS_CLOSED, ((RestaurantListViewStateItem.RestaurantItemItem) expectedViewStateItemList.get(0)).getRestaurantOpeningState());
        assertEquals(1, expectedViewStateItemList.size());
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verifyNoMoreInteractions(getAttendantsGoingToSameRestaurantAsUserUseCase);
    }

    @Test
    public void openingStateNotDefined_shouldReturnNotDefined() {
        // When
        NearbySearchEntity nearbySearchEntity = new NearbySearchEntity(
            Stubs.TEST_NEARBYSEARCH_ID,
            Stubs.TEST_NEARBYSEARCH_NAME,
            Stubs.TEST_RESTAURANT_VICINITY,
            Stubs.TEST_RESTAURANT_PHOTO_URL,
            Stubs.TEST_NEARBYSEARCH_RATING,
            Stubs.TEST_NEARBYSEARCH_LOCATION_ENTITY,
            Stubs.TEST_NEARBYSEARCH_DISTANCE,
            null
        );
        nearbySearchWrapperLiveData.setValue(new NearbySearchWrapper.Success(Collections.singletonList(nearbySearchEntity)));
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertEquals(RestaurantOpeningState.IS_NOT_DEFINED, ((RestaurantListViewStateItem.RestaurantItemItem) expectedViewStateItemList.get(0)).getRestaurantOpeningState());
        assertEquals(1, expectedViewStateItemList.size());
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verifyNoMoreInteractions(getAttendantsGoingToSameRestaurantAsUserUseCase);
    }

    @Test
    public void restaurantWith0rating_shouldReturn0() {
        // When
        NearbySearchEntity nearbySearchEntity = new NearbySearchEntity(
            Stubs.TEST_NEARBYSEARCH_ID,
            Stubs.TEST_NEARBYSEARCH_NAME,
            Stubs.TEST_RESTAURANT_VICINITY,
            Stubs.TEST_RESTAURANT_PHOTO_URL,
            null,
            Stubs.TEST_NEARBYSEARCH_LOCATION_ENTITY,
            Stubs.TEST_NEARBYSEARCH_DISTANCE,
            true
        );
        nearbySearchWrapperLiveData.setValue(new NearbySearchWrapper.Success(Collections.singletonList(nearbySearchEntity)));
        List<RestaurantListViewStateItem> expectedViewStateItemList = getValueForTesting(restaurantListViewModel.getRestaurants());

        // Then
        assertEquals(0f, ((RestaurantListViewStateItem.RestaurantItemItem) expectedViewStateItemList.get(0)).getRating(), 0.0);
        assertEquals(1, expectedViewStateItemList.size());
        verify(getAttendantsGoingToSameRestaurantAsUserUseCase).invoke();
        verifyNoMoreInteractions(getAttendantsGoingToSameRestaurantAsUserUseCase);
    }
}