package com.emplk.go4lunch.domain.location;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.gps.GpsLocationRepository;

import org.junit.Before;
import org.junit.Test;

public class GetCurrentLocationStateUseCaseTest {

    public final GpsLocationRepository gpsLocationRepository = mock(GpsLocationRepository.class);
    public GetCurrentLocationStateUseCase getCurrentLocationStateUseCase;

    @Before
    public void setUp() {
        getCurrentLocationStateUseCase = new GetCurrentLocationStateUseCase(gpsLocationRepository);
    }

    @Test
    public void testInvoke() {
        // When
        getCurrentLocationStateUseCase.invoke();

        // Then
        verify(gpsLocationRepository).getLocationStateLiveData();
        verifyNoMoreInteractions(gpsLocationRepository);
    }
}