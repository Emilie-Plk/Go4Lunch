package com.emplk.go4lunch.domain.nearby_search;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.nearby_search.entity.NearbySearchEntity;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class SortNearbyRestaurantsUseCase {

    @Inject
    public SortNearbyRestaurantsUseCase(
    ) {
    }

    public List<NearbySearchEntity> invoke(@NonNull List<NearbySearchEntity> nearbySearchEntityList, @NonNull LocationEntity userLocationEntity) {
        LatLng userLatLng = new LatLng(userLocationEntity.getLatitude(), userLocationEntity.getLongitude());
        return nearbySearchEntityList.stream()
            .sorted(Comparator.comparingDouble(place ->
                    SphericalUtil.computeDistanceBetween(
                        userLatLng,
                        new LatLng(place.getLocationEntity().getLatitude(), place.getLocationEntity().getLongitude()
                        )
                    )
                )
            )
            .collect(Collectors.toList());
    }
}