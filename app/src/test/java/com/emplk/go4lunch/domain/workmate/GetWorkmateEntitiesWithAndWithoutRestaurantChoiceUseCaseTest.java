package com.emplk.go4lunch.domain.workmate;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCaseTest {


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase = mock(GetCurrentLoggedUserUseCase.class);

    private final GetWorkmateEntitiesWithRestaurantChoiceListUseCase getWorkmateEntitiesWithRestaurantChoiceListUseCase = mock(GetWorkmateEntitiesWithRestaurantChoiceListUseCase.class);

    private final GetLoggedUserEntitiesUseCase getLoggedUserEntitiesUseCase = mock(GetLoggedUserEntitiesUseCase.class);

    private GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase;

    @Before
    public void setUp() {
        // Given
        // 3 workmates with restaurant choice
        //
        MutableLiveData<List<WorkmateEntity>> workmateEntitiesWithRestaurantChoiceLiveData = new MutableLiveData<>();
        List<WorkmateEntity> workmateEntityList = new ArrayList<>();
        WorkmateEntity workmateEntity1 = new WorkmateEntity(
            new LoggedUserEntity(
                "TEST_USER_ID1",
                "WORKMATE_NAME_1",
                "WORKMATE_EMAIL_1",
                "WORKMATE_PHOTO_URL_1"
            ),
            "RESTAURANT_ID_1",
            "RESTAURANT_NAME_1",
            "RESTAURANT_VICINITY_1"
        );
        WorkmateEntity workmateEntity2 = new WorkmateEntity(
            new LoggedUserEntity(
                "TEST_USER_ID2",
                "WORKMATE_NAME_2",
                "WORKMATE_EMAIL_2",
                "WORKMATE_PHOTO_URL_2"
            ),
            "RESTAURANT_ID_2",
            "RESTAURANT_NAME_2",
            "RESTAURANT_VICINITY_2"
        );
        WorkmateEntity workmateEntity3 = new WorkmateEntity(
            new LoggedUserEntity(
                "TEST_USER_ID3",
                "WORKMATE_NAME_3",
                "WORKMATE_EMAIL_3",
                "WORKMATE_PHOTO_URL_3"
            ),
            "RESTAURANT_ID_3",
            "RESTAURANT_NAME_3",
            "RESTAURANT_VICINITY_3"
        );
        workmateEntityList.add(workmateEntity1);
        workmateEntityList.add(workmateEntity2);
        workmateEntityList.add(workmateEntity3);
        workmateEntitiesWithRestaurantChoiceLiveData.setValue(workmateEntityList);
        doReturn(workmateEntitiesWithRestaurantChoiceLiveData).when(getWorkmateEntitiesWithRestaurantChoiceListUseCase).invoke();

        MutableLiveData<List<LoggedUserEntity>> loggedUserEntitiesLiveData = new MutableLiveData<>();
        List<LoggedUserEntity> loggedUserEntityList = Stubs.getFourTestLoggedUserEntities();
        loggedUserEntityList.add(Stubs.getTestLoggedUserEntity());
        loggedUserEntitiesLiveData.setValue(loggedUserEntityList);
        doReturn(loggedUserEntitiesLiveData).when(getLoggedUserEntitiesUseCase).invoke();


        LoggedUserEntity currentLoggedUser = Stubs.getTestLoggedUserEntity();
        doReturn(currentLoggedUser).when(getCurrentLoggedUserUseCase).invoke();

        getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase = new GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase(
            getWorkmateEntitiesWithRestaurantChoiceListUseCase,
            getCurrentLoggedUserUseCase,
            getLoggedUserEntitiesUseCase);
    }

    @Test
    public void testInvoke() {
        List<WorkmateEntity> workmateEntities = getValueForTesting(getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase.invoke());

        assertEquals(4, workmateEntities.size());
        assertEquals("TEST_USER_ID1", workmateEntities.get(0).getLoggedUserEntity().getId());
        assertEquals("RESTAURANT_ID_1", workmateEntities.get(0).getAttendingRestaurantId());
        assertEquals("TEST_USER_ID2", workmateEntities.get(1).getLoggedUserEntity().getId());
        assertEquals("RESTAURANT_ID_2", workmateEntities.get(1).getAttendingRestaurantId());
        assertNull(workmateEntities.get(3).getAttendingRestaurantId());
    }
}