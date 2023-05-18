package com.emplk.go4lunch.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.auth.AuthRepository;
import com.emplk.go4lunch.data.auth.FirebaseUserEntity;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MainViewModel extends ViewModel {

    private final AuthRepository authRepository;

    private final LiveData<FirebaseUserEntity> firebaseUserEntityLiveData;

    @Inject
    public MainViewModel(@NonNull AuthRepository authRepository) {
        this.authRepository = authRepository;
        firebaseUserEntityLiveData = authRepository.getCurrentUser();
    }

    public LiveData<FirebaseUserEntity> getCurrentUserLiveData() {
        return firebaseUserEntityLiveData;
    }

    public void signOut() {
        authRepository.signOut();
    }
}

