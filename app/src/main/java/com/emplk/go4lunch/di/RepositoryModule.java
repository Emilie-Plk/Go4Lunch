package com.emplk.go4lunch.di;

import com.emplk.go4lunch.data.authentication.AuthRepositoryImpl;
import com.emplk.go4lunch.data.gps_location.GpsLocationRepositoryBroadcastReceiver;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchRepositoryGooglePlaces;
import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.gps.GpsLocationRepository;
import com.emplk.go4lunch.domain.nearby_search.NearbySearchRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@InstallIn(SingletonComponent.class)
@Module
public abstract class RepositoryModule {

    @Binds
    public abstract AuthRepository bindAuthRepository(AuthRepositoryImpl authRepository);

    @Binds
    public abstract GpsLocationRepository bindGpsLocationRepository(GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver);

    @Binds
    public abstract NearbySearchRepository bindsNearbySearchRepository(NearbySearchRepositoryGooglePlaces nearbySearchRepositoryGooglePlaces);
}
