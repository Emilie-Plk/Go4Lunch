package com.emplk.go4lunch.ui.restaurant_map;

import static com.emplk.go4lunch.ui.utils.BitmapFromVector.getBitmapFromVector;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.domain.gps.entity.LocationEntity;
import com.emplk.go4lunch.domain.gps.entity.LocationStateEntity;
import com.emplk.go4lunch.ui.restaurant_detail.RestaurantDetailActivity;
import com.emplk.go4lunch.ui.restaurant_map.map__marker.OnMarkerClickedListener;
import com.emplk.go4lunch.ui.restaurant_map.map__marker.RestaurantMarkerViewStateItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MapFragment extends SupportMapFragment implements OnMapReadyCallback, OnMarkerClickedListener {
    private MapViewModel viewModel;

    private final List<Marker> markers = new ArrayList<>();

    private Marker userMarker = null;

    @NonNull
    public static SupportMapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onViewCreated(
        @NonNull View view,
        @Nullable Bundle savedInstanceState
    ) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        getMapAsync(this);
    }

    @SuppressLint({"PotentialBehaviorOverride", "MissingPermission"})
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setMyLocationEnabled(true);

        viewModel.getMapViewState().observe(getViewLifecycleOwner(), mapViewState -> {
                clearMarkers();
                for (RestaurantMarkerViewStateItem item : mapViewState) {
                    MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(item.getLatLng().latitude, item.getLatLng().longitude))
                        .title(item.getName())
                        .icon(getBitmapFromVector(getContext(), R.drawable.twotone_location_on_24, item.getColorAttendance()));

                    Marker marker = googleMap.addMarker(markerOptions);
                    if (marker != null) {
                        marker.setTag(item.getId());
                    }

                    markers.add(marker);
                }

                googleMap.setOnInfoWindowClickListener(
                    marker -> {
                        String restaurantId = (String) marker.getTag();
                        if (restaurantId != null) {
                            onMarkerClicked(restaurantId);
                        }
                    }
                );
            }
        );

        viewModel.getNoRestaurantMatchSingleLiveEvent().observe(getViewLifecycleOwner(), aVoid -> {
                Toast.makeText(getContext(), R.string.list_no_restaurant_match, Toast.LENGTH_LONG).show();
            }
        );

        viewModel.getNoRestaurantFoundSingleLiveEvent().observe(getViewLifecycleOwner(), aVoid -> {
                Toast.makeText(getContext(), R.string.toast_no_restaurant_found_nearby, Toast.LENGTH_LONG).show();
            }
        );

        viewModel.getLocationState().observe(getViewLifecycleOwner(), locationState -> {
                if (locationState instanceof LocationStateEntity.Success) {

                    if (userMarker != null) {
                        userMarker.remove();
                    }

                    LocationEntity location = ((LocationStateEntity.Success) locationState).locationEntity;
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                    float zoomLevel = 15f;

                    MarkerOptions userMarkerOptions = new MarkerOptions()
                        .position(latLng)
                        .title(getString(R.string.map_user_maker_message));

                    userMarker = googleMap.addMarker(userMarkerOptions);

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(latLng)
                        .zoom(zoomLevel)
                        .build();

                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    googleMap.moveCamera(cameraUpdate);
                }
            }
        );
    }

    @Override
    public void onMarkerClicked(@NonNull String restaurantId) {
        startActivity(RestaurantDetailActivity.navigate(requireContext(), restaurantId));
    }

    private void clearMarkers() {
        for (Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }
}