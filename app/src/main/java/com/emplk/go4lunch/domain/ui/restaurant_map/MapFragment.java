package com.emplk.go4lunch.domain.ui.restaurant_map;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback {
    private MapViewModel viewModel;

    @NonNull
    public static SupportMapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        getMapAsync(this);

        viewModel.isGpsEnabled().observe(getViewLifecycleOwner(), isEnabled -> {
                if (!isEnabled) {
                    Toast.makeText(requireContext(), "Please enable GPS", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(requireContext(), "GPS enabled", Toast.LENGTH_SHORT).show();
                }
            }
        );
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        viewModel.getMapViewStateLiveData().observe(getViewLifecycleOwner(), mapViewState -> {
                LatLng latLng = mapViewState.getLatLng();
                float zoomLevel = 15f;
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker in your area"));
                // Create a CameraPosition object with the target location and desired zoom level
                CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(latLng)
                    .zoom(zoomLevel)
                    .build();

                // Create a CameraUpdate object to move and zoom the camera to the desired position
                CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);

                // Move and zoom the camera to the desired position
                googleMap.animateCamera(cameraUpdate);
            }
        );
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.refreshLocationRequest();
    }
}