package com.emplk.go4lunch.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.domain.authentication.GetCurrentUserUseCase;
import com.emplk.go4lunch.domain.authentication.LoggedUserEntity;
import com.emplk.go4lunch.domain.authentication.LogoutUserUseCase;
import com.emplk.go4lunch.domain.autocomplete.GetAutocompleteWrapperUseCase;
import com.emplk.go4lunch.domain.autocomplete.entity.AutocompleteWrapper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    @NonNull
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    @NonNull
    private final LogoutUserUseCase logoutUserUseCase;

    @NonNull
    private final GetAutocompleteWrapperUseCase getAutocompleteWrapperUseCase;

    @Inject
    public MainViewModel(
        @NonNull GetCurrentUserUseCase getCurrentUserUseCase,
        @NonNull LogoutUserUseCase logoutUserUseCase,
        @NonNull GetAutocompleteWrapperUseCase getAutocompleteWrapperUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.logoutUserUseCase = logoutUserUseCase;
        this.getAutocompleteWrapperUseCase = getAutocompleteWrapperUseCase;
    }

    public LiveData<LoggedUserEntity> getCurrentUserLiveData() {
        return getCurrentUserUseCase.invoke();
    }

    // TODO: make sure that input passed is @NonNull
    public LiveData<List<PredictionViewState>> onUserSearchQuery(@Nullable String input) {
        MediatorLiveData<List<PredictionViewState>> predictionListViewStateMediatorLiveData = new MediatorLiveData<>();

        return Transformations.switchMap(getAutocompleteWrapperUseCase.invoke(input), predictionWrapper -> {
            if (predictionWrapper instanceof AutocompleteWrapper.Success) {

            } else {
                return null;
            }
        });
    });
}

private void combine(@Nullable String input, @NonNull SearchType searchType) {
}

    public void signOut() {
        logoutUserUseCase.invoke();
    }
}

