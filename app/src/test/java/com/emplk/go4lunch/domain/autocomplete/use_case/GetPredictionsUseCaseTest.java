package com.emplk.go4lunch.domain.autocomplete.use_case;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.autocomplete.PredictionEntity;
import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.domain.location.GetCurrentLocationStateUseCase;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class GetPredictionsUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private static final int RADIUS = 1_000;
    private static final String TYPES = "restaurant";

    private final PredictionsRepository repository = mock(PredictionsRepository.class);

    private final GetCurrentLocationStateUseCase getCurrentLocationStateUseCase = mock(GetCurrentLocationStateUseCase.class);

    private GetPredictionsUseCase getPredictionsUseCase;

    @Before
    public void setUp() {
        getPredictionsUseCase = new GetPredictionsUseCase(repository, getCurrentLocationStateUseCase);
    }

    @Test
    public void testInvoke() {
        // Given
        LocationStateEntity locationStateEntity = Stubs.getTestLocationStateEntitySuccess();
        MutableLiveData<LocationStateEntity> locationStateEntityLiveData = new MutableLiveData<>(locationStateEntity);
        locationStateEntityLiveData.setValue(locationStateEntity);
        doReturn(locationStateEntityLiveData).when(getCurrentLocationStateUseCase).invoke();

        List<PredictionEntity> predictionEntities = Stubs.getPredictionEntityList();
        MutableLiveData<List<PredictionEntity>> predictionEntitiesLiveData = new MutableLiveData<>(predictionEntities);
        predictionEntitiesLiveData.setValue(predictionEntities);
        doReturn(predictionEntitiesLiveData).when(repository)
            .getPredictionsLiveData(
                "TEST",
                Stubs.TEST_LATITUDE,
                Stubs.TEST_LONGITUDE,
                RADIUS,
                TYPES
            );

        // When
        List<PredictionEntity> result = getValueForTesting(getPredictionsUseCase.invoke("TEST"));

        // Then
        assertEquals(predictionEntities, result);
        verify(repository).getPredictionsLiveData(
            "TEST",
            Stubs.TEST_LATITUDE,
            Stubs.TEST_LONGITUDE,
            RADIUS,
            TYPES
        );
        verify(getCurrentLocationStateUseCase).invoke();
    }

    @Test
    public void testInvoke_gpsProviderDisabled() {
        // Given
        LocationStateEntity locationStateEntity = Stubs.getTestLocationStateEntityGpsProviderDisabled();
        MutableLiveData<LocationStateEntity> locationStateEntityLiveData = new MutableLiveData<>(locationStateEntity);
        locationStateEntityLiveData.setValue(locationStateEntity);
        doReturn(locationStateEntityLiveData).when(getCurrentLocationStateUseCase).invoke();

        List<PredictionEntity> predictionEntities = Stubs.getPredictionEntityList();
        MutableLiveData<List<PredictionEntity>> predictionEntitiesLiveData = new MutableLiveData<>(predictionEntities);
        predictionEntitiesLiveData.setValue(predictionEntities);
        doReturn(predictionEntitiesLiveData).when(repository)
            .getPredictionsLiveData(
                "TEST",
                Stubs.TEST_LATITUDE,
                Stubs.TEST_LONGITUDE,
                RADIUS,
                TYPES
            );

        // When
        List<PredictionEntity> result = getValueForTesting(getPredictionsUseCase.invoke("TEST"));

        // Then
        assertNull(result);
        verify(getCurrentLocationStateUseCase).invoke();
        verifyNoMoreInteractions(repository, getCurrentLocationStateUseCase);
    }
}