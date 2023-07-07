package com.emplk.go4lunch.data.location;

import static org.mockito.Mockito.mock;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.google.android.gms.location.FusedLocationProviderClient;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GpsLocationRepositoryBroadcastReceiverTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private static final int LOCATION_REQUEST_INTERVAL_MS = 8_000;
    private static final int SMALLEST_DISPLACEMENT_THRESHOLD_METER = 100;

    private final Context context = mock(Context.class);

    private final FusedLocationProviderClient fusedLocationProviderClient = mock(FusedLocationProviderClient.class);


    private GpsLocationRepositoryBroadcastReceiver gpsLocationRepositoryBroadcastReceiver;

    @Before
    public void setUp() {
        gpsLocationRepositoryBroadcastReceiver = new GpsLocationRepositoryBroadcastReceiver(
            context,
            fusedLocationProviderClient);
    }

    @Test
    public void startLocationRequest_success() {

    }
}