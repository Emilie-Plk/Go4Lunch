package com.emplk.go4lunch.ui.workmate_list;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserUseCase;
import com.emplk.go4lunch.domain.workmate.GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class WorkmatesViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase = mock(GetWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase.class);

    private final GetCurrentLoggedUserUseCase getCurrentLoggedUserUseCase = mock(GetCurrentLoggedUserUseCase.class);

    private WorkmatesViewModel viewModel;

    @Before
    public void setUp() {
        LoggedUserEntity loggedUserEntity = mock(LoggedUserEntity.class);
        MutableLiveData<List<WorkmateEntity>> workmateEntitiesWithAndWithoutChoiceMutableLiveData = new MutableLiveData<>();

        doReturn(workmateEntitiesWithAndWithoutChoiceMutableLiveData).when(getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase).invoke();
        doReturn(loggedUserEntity).when(getCurrentLoggedUserUseCase).invoke();

        List<WorkmateEntity> testWorkmateEntitiesWithAndWithoutChoice = getTestWorkmateEntitiesWithAndWithoutChoice();
        workmateEntitiesWithAndWithoutChoiceMutableLiveData.setValue(testWorkmateEntitiesWithAndWithoutChoice);

        viewModel = new WorkmatesViewModel(
            getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase,
            getCurrentLoggedUserUseCase
        );
    }

    @Test
    public void nominal_case() {
        // WHEN
        List<WorkmatesViewStateItem> result = getValueForTesting(viewModel.getWorkmates());

        // THEN
        assertEquals(3, result.size());
        verify(getCurrentLoggedUserUseCase).invoke();
        verify(getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase).invoke();
        verifyNoMoreInteractions(getWorkmateEntitiesWithAndWithoutRestaurantChoiceUseCase);
        verifyNoMoreInteractions(getCurrentLoggedUserUseCase);
    }

    private List<WorkmateEntity> getTestWorkmateEntitiesWithAndWithoutChoice() {
        List<WorkmateEntity> workmateEntitiesWithAndWithoutChoice = new ArrayList<>();

        LoggedUserEntity loggedUser1 = new LoggedUserEntity("uid1", "username1", "email1", "urlPicture1");
        WorkmateEntity workmate1 = new WorkmateEntity(loggedUser1, "restaurantId1", "restaurantName1", "restaurantVicinity1");
        workmateEntitiesWithAndWithoutChoice.add(workmate1);

        LoggedUserEntity loggedUser2 = new LoggedUserEntity("uid2", "username2", "email2", "urlPicture2");
        WorkmateEntity workmate2 = new WorkmateEntity(loggedUser2, "restaurantId2", "restaurantName2", "restaurantVicinity2");
        workmateEntitiesWithAndWithoutChoice.add(workmate2);

        LoggedUserEntity loggedUser3 = new LoggedUserEntity("uid3", "username3", "email3", "urlPicture3");
        WorkmateEntity workmate3 = new WorkmateEntity(loggedUser3, null, null, null);
        workmateEntitiesWithAndWithoutChoice.add(workmate3);

        return workmateEntitiesWithAndWithoutChoice;
    }
}