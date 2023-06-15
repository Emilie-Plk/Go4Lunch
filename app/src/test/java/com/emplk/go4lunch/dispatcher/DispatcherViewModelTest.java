package com.emplk.go4lunch.dispatcher;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInUseCase;
import com.emplk.go4lunch.domain.gps.IsGpsEnabledUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;
import com.emplk.go4lunch.ui.dispatcher.DispatcherViewAction;
import com.emplk.go4lunch.ui.dispatcher.DispatcherViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DispatcherViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final HasGpsPermissionUseCase hasGpsPermissionUseCase = mock(HasGpsPermissionUseCase.class);

    private final IsUserLoggedInUseCase isUserLoggedInUseCase = mock(IsUserLoggedInUseCase.class);

    private final StartLocationRequestUseCase startLocationRequestUseCase = mock(StartLocationRequestUseCase.class);

    private final IsGpsEnabledUseCase isGpsEnabledUseCase = mock(IsGpsEnabledUseCase.class);

    private MutableLiveData<Boolean> hasPermissionMutableLiveData;

    private MutableLiveData<Boolean> isUserLoggedInMutableLiveData;

    private MutableLiveData<Boolean> isGpsEnabledMutableLiveData;

    private DispatcherViewModel viewModel;

    @Before
    public void setUp() {

        hasPermissionMutableLiveData = new MutableLiveData<>();
        isUserLoggedInMutableLiveData = new MutableLiveData<>();
        isGpsEnabledMutableLiveData = new MutableLiveData<>();

        doReturn(hasPermissionMutableLiveData).when(hasGpsPermissionUseCase).invoke();
        doReturn(isUserLoggedInMutableLiveData).when(isUserLoggedInUseCase).invoke();
        doReturn(isGpsEnabledMutableLiveData).when(isGpsEnabledUseCase).invoke();

        viewModel = new DispatcherViewModel(hasGpsPermissionUseCase, isUserLoggedInUseCase, startLocationRequestUseCase, isGpsEnabledUseCase);
    }


    @Test
    public void nominal_case() {
        // GIVEN
        hasPermissionMutableLiveData.setValue(true);
        isUserLoggedInMutableLiveData.setValue(true);
        isGpsEnabledMutableLiveData.setValue(true);

        // WHEN
        DispatcherViewAction result = getValueForTesting(viewModel.getDispatcherViewAction());

        // THEN
        assertEquals(result, DispatcherViewAction.GO_TO_MAIN_ACTIVITY);
    }

    @Test
    public void edge_case() {
        // GIVEN
        hasPermissionMutableLiveData.setValue(false);
        isUserLoggedInMutableLiveData.setValue(false);
        isGpsEnabledMutableLiveData.setValue(false);

        // WHEN
        DispatcherViewAction result = getValueForTesting(viewModel.getDispatcherViewAction());

        // THEN
        assertEquals(result, DispatcherViewAction.GO_TO_ONBOARDING_ACTIVITY);
    }

    @Test
    public void should_dispatch_to_permission_request() {
        // GIVEN
        hasPermissionMutableLiveData.setValue(false);
        isUserLoggedInMutableLiveData.setValue(true);
        isGpsEnabledMutableLiveData.setValue(true);

        // WHEN
        DispatcherViewAction result = getValueForTesting(viewModel.getDispatcherViewAction());

        // THEN
        assertEquals(result, DispatcherViewAction.GO_TO_ONBOARDING_ACTIVITY);
    }

    @Test
    public void should_dispatch_to_login() {
        // GIVEN
        hasPermissionMutableLiveData.setValue(true);
        isUserLoggedInMutableLiveData.setValue(false);
        isGpsEnabledMutableLiveData.setValue(true);

        // WHEN
        DispatcherViewAction result = getValueForTesting(viewModel.getDispatcherViewAction());

        // THEN
        assertEquals(result, DispatcherViewAction.GO_TO_LOGIN_ACTIVITY);
    }


}
