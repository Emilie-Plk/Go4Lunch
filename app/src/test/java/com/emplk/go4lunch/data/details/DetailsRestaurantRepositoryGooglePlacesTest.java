package com.emplk.go4lunch.data.details;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import android.util.LruCache;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.emplk.go4lunch.data.GoogleMapsApi;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DetailsRestaurantRepositoryGooglePlacesTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    private final GoogleMapsApi googleMapsApi = mock(GoogleMapsApi.class);

    private final LruCache<DetailsKey, DetailsRestaurantEntity> detailsLruCache = mock(LruCache.class);

    private DetailsRestaurantRepositoryGooglePlaces repository;

    private final static String FAKE_API_KEY = "123456789";

    @Before
    public void setUp() {
        repository = new DetailsRestaurantRepositoryGooglePlaces(googleMapsApi);
        //   detailsLruCache = new LruCache<>(200);  // TODO: how to mock LruCache?
    }

    @Test
    public void verify_getDetailsRestaurant() {
        // GIVEN
        String placeId = "ChIJN1t_tDeuEmsRUsoyG83frY4";

        // WHEN
        repository.getRestaurantDetails(placeId, FAKE_API_KEY);

        // THEN
        verify(googleMapsApi).getPlaceDetails(placeId, FAKE_API_KEY);
        verifyNoMoreInteractions(googleMapsApi);
    }

}