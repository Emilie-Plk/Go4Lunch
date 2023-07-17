package com.emplk.go4lunch.domain.workmate;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class GetWorkmateEntitiesGoingToSameRestaurantUseCaseTest {

    @Rule
    public final InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final GetWorkmateEntitiesWithRestaurantChoiceListUseCase getWorkmateEntitiesWithRestaurantChoiceListUseCase = mock(GetWorkmateEntitiesWithRestaurantChoiceListUseCase.class);

    private GetWorkmateEntitiesGoingToSameRestaurantUseCase getWorkmateEntitiesGoingToSameRestaurantUseCase;

    @Before
    public void setUp() {
        MutableLiveData<List<WorkmateEntity>> workmateEntitiesMutableLiveData = new MutableLiveData<>();
        List<WorkmateEntity> workmateEntities = Stubs.getThreeTestWorkmateEntities();
        workmateEntitiesMutableLiveData.setValue(workmateEntities);
        doReturn(workmateEntitiesMutableLiveData).when(getWorkmateEntitiesWithRestaurantChoiceListUseCase).invoke();

        getWorkmateEntitiesGoingToSameRestaurantUseCase = new GetWorkmateEntitiesGoingToSameRestaurantUseCase(getWorkmateEntitiesWithRestaurantChoiceListUseCase);
    }

    @Test
    public void testInvoke() {
        List<WorkmateEntity> workmateEntities = getValueForTesting(getWorkmateEntitiesGoingToSameRestaurantUseCase.invoke(Stubs.TEST_RESTAURANT_ID));
        assertEquals(3, workmateEntities.size());
    }

    @Test
    public void threeWorkmatesWithRestaurantChoice_twoWorkmatesGoingToSameRestaurant() {
        List<WorkmateEntity> workmateEntities = Stubs.getThreeTestWorkmateEntities();
        WorkmateEntity workmateGoingToMcDo = new WorkmateEntity(
            new LoggedUserEntity(
                "WORKMATE_ID",
                "WORKMATE_NAME",
                "WORKMATE_EMAIL",
                "WORKMATE_PHOTO_URL"
            ),
            "MCDONALDS",
            Stubs.ATTENDING_RESTAURANT_NAME,
            Stubs.ATTENDING_RESTAURANT_VICINITY
        );
        workmateEntities.add(workmateGoingToMcDo);
        MutableLiveData<List<WorkmateEntity>> workmateEntitiesMutableLiveData = new MutableLiveData<>(workmateEntities);
        doReturn(workmateEntitiesMutableLiveData).when(getWorkmateEntitiesWithRestaurantChoiceListUseCase).invoke();

        List<WorkmateEntity> result = getValueForTesting(getWorkmateEntitiesGoingToSameRestaurantUseCase.invoke(Stubs.TEST_RESTAURANT_ID));
        assertEquals(3, result.size());
    }
}