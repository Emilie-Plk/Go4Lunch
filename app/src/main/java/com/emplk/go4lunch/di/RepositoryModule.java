package com.emplk.go4lunch.di;

import com.emplk.go4lunch.data.authentication.AuthRepositoryFirebaseAuth;
import com.emplk.go4lunch.data.chat.ChatRepositoryFirestore;
import com.emplk.go4lunch.data.details.DetailsRestaurantRepositoryGooglePlaces;
import com.emplk.go4lunch.data.favorite_restaurant.FavoriteRestaurantRepositoryFirestore;
import com.emplk.go4lunch.data.location.GpsLocationRepositoryBroadcastReceiver;
import com.emplk.go4lunch.data.nearbySearchRestaurants.NearbySearchRepositoryGooglePlaces;
import com.emplk.go4lunch.data.permission.GpsPermissionRepositoryImpl;
import com.emplk.go4lunch.data.settings.NotificationRepositoryImplementation;
import com.emplk.go4lunch.data.user.UserRepositoryFirestore;
import com.emplk.go4lunch.domain.authentication.AuthRepository;
import com.emplk.go4lunch.domain.chat.ChatRepository;
import com.emplk.go4lunch.domain.detail.DetailsRestaurantRepository;
import com.emplk.go4lunch.domain.favorite_restaurant.FavoriteRestaurantRepository;
import com.emplk.go4lunch.domain.gps.GpsLocationRepository;
import com.emplk.go4lunch.domain.nearby_search.NearbySearchRepository;
import com.emplk.go4lunch.domain.permission.GpsPermissionRepository;
import com.emplk.go4lunch.domain.settings.NotificationRepository;
import com.emplk.go4lunch.domain.user.UserRepository;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;


@InstallIn(SingletonComponent.class)
@Module
public abstract class RepositoryModule {
    @Binds
    @Singleton
    public abstract AuthRepository bindAuthRepository(AuthRepositoryFirebaseAuth authRepository);

    @Binds
    @Singleton
    public abstract GpsLocationRepository bindGpsLocationRepository(GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver);

    @Binds
    @Singleton
    public abstract NearbySearchRepository bindsNearbySearchRepository(NearbySearchRepositoryGooglePlaces nearbySearchRepositoryGooglePlaces);

    @Binds
    @Singleton
    public abstract GpsPermissionRepository bindsGpsPermissionRepository(GpsPermissionRepositoryImpl gpsPermissionRepositoryImpl);

    @Binds
    @Singleton
    public abstract DetailsRestaurantRepository bindsDetailsRestaurantRepository(DetailsRestaurantRepositoryGooglePlaces detailsRestaurantRepositoryGooglePlaces);

    @Binds
    @Singleton
    public abstract FavoriteRestaurantRepository bindsFavoriteRestaurantRepository(FavoriteRestaurantRepositoryFirestore favoriteRestaurantRepositoryFirestore);

    @Binds
    @Singleton
    public abstract UserRepository bindsUserRepository(UserRepositoryFirestore userRepositoryFirestore);

    @Binds
    @Singleton
    public abstract NotificationRepository bindNotificationRepository(NotificationRepositoryImplementation notificationRepositoryImplementation);

    @Binds
    @Singleton
    public abstract ChatRepository bindChatRepository(ChatRepositoryFirestore chatRepositoryFirestore);
}
