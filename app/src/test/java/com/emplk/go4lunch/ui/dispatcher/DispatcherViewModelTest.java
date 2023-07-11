package com.emplk.go4lunch.ui.dispatcher;

import static com.emplk.util.TestUtil.getValueForTesting;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;

import com.emplk.go4lunch.domain.authentication.use_case.IsUserLoggedInLiveDataUseCase;
import com.emplk.go4lunch.domain.location.StartLocationRequestUseCase;
import com.emplk.go4lunch.domain.permission.HasGpsPermissionUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class DispatcherViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final HasGpsPermissionUseCase hasGpsPermissionUseCase = mock(HasGpsPermissionUseCase.class);

    private final IsUserLoggedInLiveDataUseCase isUserLoggedInLiveDataUseCase = mock(IsUserLoggedInLiveDataUseCase.class);

    private final StartLocationRequestUseCase startLocationRequestUseCase = mock(StartLocationRequestUseCase.class);


    private MutableLiveData<Boolean> hasPermissionMutableLiveData;

    private MutableLiveData<Boolean> isUserLoggedInMutableLiveData;

    private DispatcherViewModel viewModel;

    @Before
    public void setUp() {
        hasPermissionMutableLiveData = new MutableLiveData<>();
        isUserLoggedInMutableLiveData = new MutableLiveData<>();

        doReturn(hasPermissionMutableLiveData).when(hasGpsPermissionUseCase).invoke();
        doReturn(isUserLoggedInMutableLiveData).when(isUserLoggedInLiveDataUseCase).invoke();

        viewModel = new DispatcherViewModel(hasGpsPermissionUseCase, isUserLoggedInLiveDataUseCase, startLocationRequestUseCase);
    }


    @Test
    public void nominal_case() {
        // GIVEN
        hasPermissionMutableLiveData.setValue(true);
        isUserLoggedInMutableLiveData.setValue(true);

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
        // WHEN
        DispatcherViewAction result = getValueForTesting(viewModel.getDispatcherViewAction());

        // THEN
        assertEquals(result, DispatcherViewAction.GO_TO_LOGIN_ACTIVITY);
    }
}
