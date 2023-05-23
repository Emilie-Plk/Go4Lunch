package com.emplk.go4lunch.ui.restaurant_map;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
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
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        viewModel.getLocationLiveData().observe(this, mapViewState -> {
                LatLng latLng = mapViewState.getLatLng();
                googleMap.addMarker(new MarkerOptions().position(latLng).title("Marker in your area"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        );
    }
}