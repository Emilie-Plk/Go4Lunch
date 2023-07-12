package com.emplk.go4lunch.domain.detail;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class GetDetailsRestaurantWrapperUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final DetailsRestaurantRepository detailsRestaurantRepository = mock(DetailsRestaurantRepository.class);

    private GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase;

    @Before
    public void setUp() {
        getDetailsRestaurantWrapperUseCase = new GetDetailsRestaurantWrapperUseCase(detailsRestaurantRepository);
    }

    @Test
    public void testInvoke() {
        // Given
        MutableLiveData<DetailsRestaurantWrapper> detailsRestaurantWrapperMutableLiveData = new MutableLiveData<>();
        doReturn(detailsRestaurantWrapperMutableLiveData).when(detailsRestaurantRepository).getRestaurantDetails(Stubs.TEST_RESTAURANT_ID);

        DetailsRestaurantWrapper detailsRestaurantWrapper = new DetailsRestaurantWrapper.Success(Stubs.getTestDetailsRestaurantEntity());
        detailsRestaurantWrapperMutableLiveData.setValue(detailsRestaurantWrapper);

        // When
        getDetailsRestaurantWrapperUseCase.invoke(Stubs.TEST_RESTAURANT_ID);

        // Then
        verify(detailsRestaurantRepository).getRestaurantDetails(Stubs.TEST_RESTAURANT_ID);
    }

}