package com.emplk.go4lunch.di;

import com.emplk.go4lunch.data.authentication.AuthRepositoryImpl;
import com.emplk.go4lunch.data.gps_location.GpsLocationRepositoryBroadcastReceiver;
import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.gps.GpsLocationRepository;

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

    @Binds
    public abstract GpsLocationRepository bindGpsLocationRepository(GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver);
}
