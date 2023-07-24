package com.emplk.go4lunch.domain.autocomplete.use_case;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import com.emplk.go4lunch.domain.autocomplete.PredictionsRepository;

import org.junit.Before;
import org.junit.Test;

public class ResetPredictionPlaceIdUseCaseTest {

    private final PredictionsRepository repository = mock(PredictionsRepository.class);

    private ResetPredictionPlaceIdUseCase resetPredictionPlaceIdUseCase;

    @Before
    public void setUp() {
        resetPredictionPlaceIdUseCase = new ResetPredictionPlaceIdUseCase(repository);
    }

    @Test
    public void testInvoke() {
        resetPredictionPlaceIdUseCase.invoke();

        verify(repository).resetPredictionPlaceIdQuery();
        verifyNoMoreInteractions(repository);
    }
}