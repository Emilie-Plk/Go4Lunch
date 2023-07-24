package com.emplk.go4lunch.domain.autocomplete.use_case;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GetPredictionPlaceIdUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final PredictionsRepository predictionsRepository = mock(PredictionsRepository.class);

    private GetPredictionPlaceIdUseCase getPredictionPlaceIdUseCase;

    @Before
    public void setUp() {
        String placeId = Stubs.TEST_PREDICTION_ID;
        MutableLiveData<String> placeIdMutableLiveData = new MutableLiveData<>();
        placeIdMutableLiveData.setValue(placeId);
        doReturn(placeIdMutableLiveData).when(predictionsRepository).getPredictionPlaceIdLiveData();

        getPredictionPlaceIdUseCase = new GetPredictionPlaceIdUseCase(predictionsRepository);
    }

    @Test
    public void testInvoke() {
        // When
        String result = getValueForTesting(getPredictionPlaceIdUseCase.invoke());

        // Then
        assertEquals(result, Stubs.TEST_PREDICTION_ID);
        verify(predictionsRepository).getPredictionPlaceIdLiveData();
    }
}