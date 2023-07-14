package com.emplk.go4lunch.ui.restaurant_detail;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import android.content.res.Resources;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.authentication.use_case.GetCurrentLoggedUserIdUseCase;
import com.emplk.go4lunch.domain.detail.GetDetailsRestaurantWrapperUseCase;
import com.emplk.go4lunch.domain.detail.entity.DetailsRestaurantWrapper;
import com.emplk.go4lunch.domain.favorite_restaurant.AddFavoriteRestaurantUseCase;
import com.emplk.go4lunch.domain.favorite_restaurant.RemoveFavoriteRestaurantUseCase;
import com.emplk.go4lunch.domain.restaurant_choice.AddUserRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.restaurant_choice.RemoveUserRestaurantChoiceUseCase;
import com.emplk.go4lunch.domain.user.UserEntity;
import com.emplk.go4lunch.domain.user.use_case.GetUserEntityUseCase;
import com.emplk.go4lunch.domain.workmate.GetWorkmateEntitiesGoingToSameRestaurantUseCase;
import com.emplk.go4lunch.domain.workmate.WorkmateEntity;
import com.emplk.go4lunch.ui.workmate_list.WorkmatesViewStateItem;
import com.emplk.util.Stubs;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantDetailViewModelTest {

    private static final String KEY_RESTAURANT_ID = "KEY_RESTAURANT_ID";

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final GetDetailsRestaurantWrapperUseCase getDetailsRestaurantWrapperUseCase = mock(GetDetailsRestaurantWrapperUseCase.class);

    private final Resources resources = mock(Resources.class);

    private final AddFavoriteRestaurantUseCase addFavoriteRestaurantUseCase = mock(AddFavoriteRestaurantUseCase.class);

    private final RemoveFavoriteRestaurantUseCase removeFavoriteRestaurantUseCase = mock(RemoveFavoriteRestaurantUseCase.class);

    private final AddUserRestaurantChoiceUseCase addUserRestaurantChoiceUseCase = mock(AddUserRestaurantChoiceUseCase.class);

    private final RemoveUserRestaurantChoiceUseCase removeUserRestaurantChoiceUseCase = mock(RemoveUserRestaurantChoiceUseCase.class);

    private final GetWorkmateEntitiesGoingToSameRestaurantUseCase getWorkmateEntitiesGoingToSameRestaurantUseCase = mock(GetWorkmateEntitiesGoingToSameRestaurantUseCase.class);

    private final GetUserEntityUseCase getUserEntityUseCase = mock(GetUserEntityUseCase.class);

    private final GetCurrentLoggedUserIdUseCase getCurrentLoggedUserIdUseCase = mock(GetCurrentLoggedUserIdUseCase.class);

    @Mock
    private SavedStateHandle savedStateHandle = mock(SavedStateHandle.class);

    private final MutableLiveData<DetailsRestaurantWrapper> detailsRestaurantWrapperMutableLiveData = new MutableLiveData<>();

    private MutableLiveData<WorkmateState> workmateStateMutableLiveData;

    private RestaurantDetailViewModel viewModel;

    @Before
    public void setUp() {
        workmateStateMutableLiveData = new MutableLiveData<>();
        doReturn("KEY_RESTAURANT_ID").when(savedStateHandle).get(KEY_RESTAURANT_ID);
        doReturn(detailsRestaurantWrapperMutableLiveData).when(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);

        MutableLiveData<List<WorkmateEntity>> workmateEntitiesMutableLiveData = new MutableLiveData<>();
        doReturn(workmateEntitiesMutableLiveData).when(getWorkmateEntitiesGoingToSameRestaurantUseCase).invoke(KEY_RESTAURANT_ID);

        MutableLiveData<UserEntity> userEntityMutableLiveData = new MutableLiveData<>(mock(UserEntity.class));
        doReturn(userEntityMutableLiveData).when(getUserEntityUseCase).invoke();

        doReturn("coucou").when(resources).getString(R.string.google_image_url);

        doReturn(Stubs.TEST_USER_ID).when(getCurrentLoggedUserIdUseCase).invoke();

        viewModel = new RestaurantDetailViewModel(
            getDetailsRestaurantWrapperUseCase,
            resources,
            addFavoriteRestaurantUseCase,
            removeFavoriteRestaurantUseCase,
            addUserRestaurantChoiceUseCase,
            removeUserRestaurantChoiceUseCase,
            getWorkmateEntitiesGoingToSameRestaurantUseCase,
            getUserEntityUseCase,
            getCurrentLoggedUserIdUseCase,
            savedStateHandle
        );
    }

    @Test
    public void detailsRestaurantWrapperSuccess_RestaurantDetailViewStateReturnsDetailsStateItem() {
        // Given
        detailsRestaurantWrapperMutableLiveData.setValue(Stubs.getTestDetailsRestaurantWrapperSuccess());
        doReturn(detailsRestaurantWrapperMutableLiveData).when(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);

        // When
        RestaurantDetailViewState result = getValueForTesting(viewModel.getRestaurantDetails());

        // Then
        assertEquals(Stubs.getTestRestaurantDetailViewState(), result);
        verify(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);
        verify(getUserEntityUseCase).invoke();
        verifyNoMoreInteractions(getDetailsRestaurantWrapperUseCase, getUserEntityUseCase, getWorkmateEntitiesGoingToSameRestaurantUseCase);
    }

    @Test
    public void onAddFavoriteRestaurant_should_call_addFavoriteRestaurantUseCase() {
        // WHEN
        viewModel.onAddFavoriteRestaurant();
        // THEN
        verify(addFavoriteRestaurantUseCase).invoke(KEY_RESTAURANT_ID);
        verifyNoMoreInteractions(addFavoriteRestaurantUseCase);
    }


    @Test
    public void onRemoveFavoriteRestaurant_should_call_removeFavoriteRestaurantUseCase() {
        // WHEN
        viewModel.onRemoveFavoriteRestaurant();
        // THEN
        verify(removeFavoriteRestaurantUseCase).invoke(KEY_RESTAURANT_ID);
        verifyNoMoreInteractions(removeFavoriteRestaurantUseCase);
    }

    @Test
    public void onAddUserRestaurantChoice_should_call_addUserRestaurantChoiceUseCase() {
        // When
        viewModel.onAddUserRestaurantChoice(Stubs.TEST_RESTAURANT_NAME, Stubs.TEST_RESTAURANT_VICINITY, Stubs.TEST_RESTAURANT_PHOTO_URL);
        // Then
        verify(addUserRestaurantChoiceUseCase).invoke(KEY_RESTAURANT_ID, Stubs.TEST_RESTAURANT_NAME, Stubs.TEST_RESTAURANT_VICINITY, Stubs.TEST_RESTAURANT_PHOTO_URL);
        verifyNoMoreInteractions(addUserRestaurantChoiceUseCase);
    }

    @Test
    public void onRemoveUserRestaurantChoice_should_call_removeUserRestaurantChoiceUseCase() {
        // WHEN
        viewModel.onRemoveUserRestaurantChoice();
        // THEN
        verify(removeUserRestaurantChoiceUseCase).invoke();
        verifyNoMoreInteractions(removeUserRestaurantChoiceUseCase);
    }

    @Test
    public void detailsRestaurantWrapperLoading_RestaurantDetailViewStateReturnsLoadingStateItem() {
        // Given
        detailsRestaurantWrapperMutableLiveData.setValue(new DetailsRestaurantWrapper.Loading());
        doReturn(detailsRestaurantWrapperMutableLiveData).when(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);

        // When
        RestaurantDetailViewState result = getValueForTesting(viewModel.getRestaurantDetails());

        // Then
        assertEquals(Stubs.getTestRestaurantDetailViewStateLoading(), result);
        verify(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);
        verify(getUserEntityUseCase).invoke();
        verifyNoMoreInteractions(getDetailsRestaurantWrapperUseCase, getUserEntityUseCase, getWorkmateEntitiesGoingToSameRestaurantUseCase);
    }

    @Test
    public void detailsRestaurantWrapperError_RestaurantDetailViewStateReturnsLoadingStateItem() {
        // Given
        detailsRestaurantWrapperMutableLiveData.setValue(new DetailsRestaurantWrapper.Error(new Throwable("TEST_ERROR")));
        doReturn(detailsRestaurantWrapperMutableLiveData).when(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);

        // When
        RestaurantDetailViewState result = getValueForTesting(viewModel.getRestaurantDetails());

        // Then
        assertEquals(Stubs.getTestRestaurantDetailViewStateError(), result);
        verify(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);
        verify(getUserEntityUseCase).invoke();
        verifyNoMoreInteractions(getDetailsRestaurantWrapperUseCase, getUserEntityUseCase, getWorkmateEntitiesGoingToSameRestaurantUseCase);
    }

    @Test
    public void ratingOnFive_shouldReturnRatingOnThree() {
        // Given
        detailsRestaurantWrapperMutableLiveData.setValue(Stubs.getTestDetailsRestaurantWrapperSuccess());
        doReturn(detailsRestaurantWrapperMutableLiveData).when(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);

        // When
        RestaurantDetailViewState result = getValueForTesting(viewModel.getRestaurantDetails());
        Float rating = ((RestaurantDetailViewState.RestaurantDetail) result).getRating();

        // Then
        assertEquals(3.0f, rating, 0.0);
    }

    @Test
    public void getWorkmatesViewStateItems() {
        // Given
        List<WorkmateEntity> workmateEntities = new ArrayList<>();
        WorkmateEntity workmate1 = Stubs.getTestWorkmateEntity();
        WorkmateEntity workmate2 = Stubs.getTestWorkmateEntity();
        WorkmateEntity workmate3 = Stubs.getTestWorkmateEntity();
        workmateEntities.add(workmate1);
        workmateEntities.add(workmate2);
        workmateEntities.add(workmate3);
        MutableLiveData<List<WorkmateEntity>> workmateEntitiesMutableLiveData = new MutableLiveData<>(workmateEntities);
        doReturn(workmateEntitiesMutableLiveData).when(getWorkmateEntitiesGoingToSameRestaurantUseCase).invoke(KEY_RESTAURANT_ID);

        // When
        List<WorkmatesViewStateItem> result = getValueForTesting(viewModel.getWorkmatesGoingToRestaurant());

        assertEquals(3, result.size());
        // Then

        verify(getWorkmateEntitiesGoingToSameRestaurantUseCase).invoke(KEY_RESTAURANT_ID);
        verify(getUserEntityUseCase).invoke();
        verify(getCurrentLoggedUserIdUseCase).invoke();
        verify(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);
        verifyNoMoreInteractions(getDetailsRestaurantWrapperUseCase, getUserEntityUseCase, getWorkmateEntitiesGoingToSameRestaurantUseCase);
    }

    @Test
    public void workmateState_workmateGoing() {
        getWorkmatesViewStateItems();

        WorkmateState result = getValueForTesting(viewModel.getWorkerState());
        assertEquals(WorkmateState.WORKMATE_GOING, result);
    }

    @Test
    public void workmateState_noWorkmate() {
        MutableLiveData<List<WorkmateEntity>> workmateEntitiesMutableLiveData = new MutableLiveData<>(Collections.emptyList());
        doReturn(workmateEntitiesMutableLiveData).when(getWorkmateEntitiesGoingToSameRestaurantUseCase).invoke(KEY_RESTAURANT_ID);

        List<WorkmatesViewStateItem> result = getValueForTesting(viewModel.getWorkmatesGoingToRestaurant());
        WorkmateState resultWork = getValueForTesting(viewModel.getWorkerState());

        assertEquals(0, result.size());
        assertEquals(WorkmateState.NO_WORKMATE, resultWork);
    }

    @Test
    public void workmateState_isWorkmateGoingWhenWorkmatesAttending() {
        // Given
        List<WorkmateEntity> workmateEntities = new ArrayList<>();
        WorkmateEntity workmate1 = Stubs.getTestWorkmateEntity_currentUser();
        WorkmateEntity workmate2 = Stubs.getTestWorkmateEntity();
        WorkmateEntity workmate3 = Stubs.getTestWorkmateEntity();
        workmateEntities.add(workmate1);
        workmateEntities.add(workmate2);
        workmateEntities.add(workmate3);
        MutableLiveData<List<WorkmateEntity>> workmateEntitiesMutableLiveData = new MutableLiveData<>(workmateEntities);
        doReturn(workmateEntitiesMutableLiveData).when(getWorkmateEntitiesGoingToSameRestaurantUseCase).invoke(KEY_RESTAURANT_ID);

        // When
        List<WorkmatesViewStateItem> result = getValueForTesting(viewModel.getWorkmatesGoingToRestaurant());

        // Then
        assertEquals(2, result.size());
        verify(getWorkmateEntitiesGoingToSameRestaurantUseCase).invoke(KEY_RESTAURANT_ID);
        verify(getUserEntityUseCase).invoke();
        verify(getCurrentLoggedUserIdUseCase).invoke();
        verify(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);
        verifyNoMoreInteractions(getDetailsRestaurantWrapperUseCase, getUserEntityUseCase, getWorkmateEntitiesGoingToSameRestaurantUseCase);
    }
}
