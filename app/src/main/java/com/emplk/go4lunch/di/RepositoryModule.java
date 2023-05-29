package com.emplk.go4lunch.di;

import com.emplk.go4lunch.data.authentication.AuthRepositoryImpl;
import com.emplk.go4lunch.data.autocomplete.AutocompleteRepositoryGooglePlaces;
import com.emplk.go4lunch.data.details.DetailsRestaurantRepositoryGooglePlaces;
import com.emplk.go4lunch.data.gps_location.GpsLocationRepositoryBroadcastReceiver;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchRepositoryGooglePlaces;
import com.emplk.go4lunch.data.permission.GpsPermissionRepositoryImpl;
import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.autocomplete.AutocompleteRepository;
import com.emplk.go4lunch.domain.detail.DetailsRestaurantRepository;
import com.emplk.go4lunch.domain.gps.GpsLocationRepository;
import com.emplk.go4lunch.domain.nearby_search.NearbySearchRepository;
import com.emplk.go4lunch.domain.permission.GpsPermissionRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@InstallIn(SingletonComponent.class)
@Module
public abstract class RepositoryModule {
    @Singleton
    @Binds
    public abstract AuthRepository bindAuthRepository(AuthRepositoryImpl authRepository);

    @Singleton
    @Binds
    public abstract GpsLocationRepository bindGpsLocationRepository(GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver);

    @Singleton
    @Binds
    public abstract NearbySearchRepository bindsNearbySearchRepository(NearbySearchRepositoryGooglePlaces nearbySearchRepositoryGooglePlaces);

    @Singleton
    @Binds
    public abstract GpsPermissionRepository bindsGpsPermissionRepository(GpsPermissionRepositoryImpl gpsPermissionRepositoryImpl);

    @Singleton
    @Binds
    public abstract DetailsRestaurantRepository bindsDetailsRestaurantRepository(DetailsRestaurantRepositoryGooglePlaces detailsRestaurantRepositoryGooglePlaces);

    @Binds
    public abstract AutocompleteRepository bindsAutocompleteRepository(AutocompleteRepositoryGooglePlaces autocompleteRepositoryGooglePlaces);
}
