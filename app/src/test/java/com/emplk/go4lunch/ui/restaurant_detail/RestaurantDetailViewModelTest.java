package com.emplk.go4lunch.ui.restaurant_detail;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.content.res.Resources;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

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

    private final SavedStateHandle savedStateHandle = Mockito.mock(SavedStateHandle.class);

    private RestaurantDetailViewModel viewModel;

    @Before
    public void setUp() {
        Mockito.doReturn( "RESTAURANT_ID").when(savedStateHandle).get(KEY_RESTAURANT_ID);

        MutableLiveData<List<WorkmateEntity>> workmateEntitiesMutableLiveData = new MutableLiveData<>();
        doReturn(workmateEntitiesMutableLiveData).when(getWorkmateEntitiesGoingToSameRestaurantUseCase).invoke(KEY_RESTAURANT_ID);

        MutableLiveData<UserEntity> userEntityMutableLiveData = new MutableLiveData<>(mock(UserEntity.class));
        doReturn(userEntityMutableLiveData).when(getUserEntityUseCase).invoke();

        String userId = "123";
        doReturn(userId).when(getCurrentLoggedUserIdUseCase).invoke();

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

  /*  @Test
    public void nominal_case() {
        // WHEN
        MutableLiveData<DetailsRestaurantWrapper> detailsRestaurantWrapperMutableLiveData = new MutableLiveData<>(mock(DetailsRestaurantWrapper.Success.class));
        doReturn(detailsRestaurantWrapperMutableLiveData).when(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);

        RestaurantDetailViewState result = getValueForTesting(viewModel.getRestaurantDetails());

        assertTrue(result instanceof RestaurantDetailViewState.RestaurantDetail);
        verify(getDetailsRestaurantWrapperUseCase).invoke(KEY_RESTAURANT_ID);
        verify(getUserEntityUseCase).invoke();
    }*/

/*    @Test
    public void onAddFavoriteRestaurant_should_call_addFavoriteRestaurantUseCase() {
        // WHEN
        viewModel.onAddFavoriteRestaurant();
        // THEN
        verify(addFavoriteRestaurantUseCase).invoke(KEY_RESTAURANT_ID);
    }*/
}