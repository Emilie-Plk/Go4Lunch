package com.emplk.go4lunch.domain.nearby_search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.emplk.util.Stubs;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SortNearbyRestaurantsUseCaseTest {

    public final SortNearbyRestaurantsUseCase sortNearbyRestaurantsUseCase = new SortNearbyRestaurantsUseCase();

    @Test
    public void testInvoke() {
        // When
        List<NearbySearchEntity> unsortedNearbySearchEntityList = new ArrayList<>();

        List<NearbySearchEntity> sortedNearbySearchEntityList = Stubs.getTestNearbySearchEntityList(3);

        unsortedNearbySearchEntityList.add(sortedNearbySearchEntityList.get(2));
        unsortedNearbySearchEntityList.add(sortedNearbySearchEntityList.get(1));
        unsortedNearbySearchEntityList.add(sortedNearbySearchEntityList.get(0));

        List<NearbySearchEntity> result = sortNearbyRestaurantsUseCase.invoke(unsortedNearbySearchEntityList, Stubs.getTestUserLocationEntity());

        // Then
        assertEquals(sortedNearbySearchEntityList, result);
        assertTrue(result.get(0).getLocationEntity().getLatitude() < result.get(1).getLocationEntity().getLatitude());
        assertTrue(result.get(1).getLocationEntity().getLatitude() < result.get(2).getLocationEntity().getLatitude());
        assertTrue(result.get(0).getLocationEntity().getLongitude() < result.get(1).getLocationEntity().getLongitude());
        assertTrue(result.get(1).getLocationEntity().getLongitude() < result.get(2).getLocationEntity().getLongitude());
    }
}