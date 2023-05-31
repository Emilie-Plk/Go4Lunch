package com.emplk.go4lunch.di;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import androidx.annotation.NonNull;

import com.emplk.go4lunch.data.GoogleMapsApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public class AppModule {

    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/";

    @Provides
    @Singleton
    public Retrofit provideRetrofit(@NonNull Application application) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .cache(new Cache(application.getCacheDir(), 1_024 * 1_024))
            .build();

        return new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    }

    @Provides
    @Singleton
    public GoogleMapsApi provideGoogleMapsApi(@NonNull Retrofit retrofit) {
        return retrofit.create(GoogleMapsApi.class);
    }

    @Provides
    @Singleton
    public FusedLocationProviderClient provideFusedLocationProviderClient(@ApplicationContext Context context) {
        return LocationServices.getFusedLocationProviderClient(context);
    }

    @Provides
    @Singleton
    public FirebaseAuth provideFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }


    @Provides
    @Singleton
    public Resources provideResources(@NonNull Application application) {
        return application.getResources();
    }
}