package com.emplk.go4lunch.ui.dispatcher;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.emplk.go4lunch.data.permission.PermissionRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class DispatcherViewModel extends ViewModel {

    @NonNull
    private final PermissionRepository permissionRepository;

    @Inject
    public DispatcherViewModel(
        @NonNull PermissionRepository permissionRepository
    ) {
        this.permissionRepository = permissionRepository;
    }

    public LiveData<Boolean> isPermissionGranted() {
        return permissionRepository.getLocationPermission();
    }
}
