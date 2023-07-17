package com.emplk.go4lunch.domain.location;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.gps.GpsLocationRepository;

import org.junit.Before;
import org.junit.Test;

public class StartLocationRequestUseCaseTest {


    public final GpsLocationRepository gpsLocationRepository = mock(GpsLocationRepository.class);

    public StartLocationRequestUseCase startLocationRequestUseCase;

    @Before
    public void setUp() {
        startLocationRequestUseCase = new StartLocationRequestUseCase(gpsLocationRepository);
    }

    @Test
    public void testInvoke() {
        startLocationRequestUseCase.invoke();

        verify(gpsLocationRepository).startLocationRequest();
        verifyNoMoreInteractions(gpsLocationRepository);
    }
}