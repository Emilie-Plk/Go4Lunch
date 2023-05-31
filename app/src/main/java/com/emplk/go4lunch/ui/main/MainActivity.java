package com.emplk.go4lunch.ui.main;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.MainActivityBinding;
import com.emplk.go4lunch.databinding.MainNavigationHeaderBinding;
import com.emplk.go4lunch.ui.dispatcher.DispatcherActivity;
import com.emplk.go4lunch.ui.main.searchview.OnPredictionClickedListener;
import com.emplk.go4lunch.ui.main.searchview.SearchViewAdapter;
import com.emplk.go4lunch.ui.restaurant_detail.RestaurantDetailActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    private MainActivityBinding binding;

    private MainViewModel viewModel;

    private SearchViewAdapter searchViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainToolbar);


        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        MainPagerAdapter adapter = new MainPagerAdapter(MainActivity.this);
        binding.mainViewpagerContainer.setAdapter(adapter);

        setObservers();

        initBottomNavigationBar();

        initNavigationDrawer();

        initSearchView();
    }

    private void setObservers() {

        // MainNavigation Header
        MainNavigationHeaderBinding navigationHeaderBinding;
        View headerView = binding.mainNavigationView.getHeaderView(0);
        navigationHeaderBinding = MainNavigationHeaderBinding.bind(headerView);
        viewModel.getCurrentUserLiveData().observe(this, firebaseUser -> {
                Glide.with(this)
                    .load(firebaseUser.getPhotoUrl())
                    .transform(new CenterCrop(), new RoundedCorners(25))
                    .into(navigationHeaderBinding.navigationHeaderUserProfilePicture);

                navigationHeaderBinding.navigationHeaderUserEmail.setText(firebaseUser.getEmail());
                navigationHeaderBinding.navigationHeaderUserName.setText(firebaseUser.getDisplayName());
            }
        );
/*
        viewModel.getPredictionViewStateLiveData().observe(this, predictionViewState -> {
                if (predictionViewState != null) {
                    searchViewAdapter.submitList(predictionViewState);
                }
            }
        );*/
    }

    private void initSearchView() {

        SearchView searchView = binding.mainToolbarSearchView;
        searchView.clearFocus();

        RecyclerView recyclerView = binding.mainSearchviewRecyclerview;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setVisibility(View.GONE); // TODO: MVVMize this pls
        searchViewAdapter = new SearchViewAdapter(new OnPredictionClickedListener() {
            @Override
            public void onPredictionClicked(@NonNull String placeId) {
                startActivity(RestaurantDetailActivity.navigate(MainActivity.this, placeId));
            }
        }
        );

        recyclerView.setAdapter(searchViewAdapter);
/*
        searchView.setOnQueryTextListener(   //TODO: get this right later
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    recyclerView.setVisibility(View.VISIBLE);
                    viewModel.onUserSearchQuery(newText).observe(MainActivity.this, predictionViewState -> {
                            if (predictionViewState != null) {
                                searchViewAdapter.submitList(predictionViewState);
                            }
                        }
                    );
                    return false;
                }
            }
        );
*/

        searchView.setOnCloseListener(() -> {
                recyclerView.setVisibility(View.GONE);
                return false;
            }
        );
    }

    @SuppressLint("NonConstantResourceId")
    private void initBottomNavigationBar() {
        BottomNavigationView bottomNavigationView = binding.mainBottomAppbar;
        bottomNavigationView.setSelectedItemId(R.id.bottom_bar_map);

        binding.mainBottomAppbar.setOnItemSelectedListener(item -> {
                switch (item.getItemId()) {
                    case R.id.bottom_bar_map:
                        binding.mainViewpagerContainer.setCurrentItem(0, true);
                        return true;
                    case R.id.bottom_bar_restaurant_list:
                        binding.mainViewpagerContainer.setCurrentItem(1, true);
                        return true;
                    case R.id.bottom_bar_workmate_list:
                        binding.mainViewpagerContainer.setCurrentItem(2, true);
                        return true;
                }
                return false;
            }
        );
    }

    @SuppressLint("NonConstantResourceId")
    private void initNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.mainDrawerLayout, binding.mainToolbar, R.string.open_nav, R.string.close_nav);
        binding.mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.mainNavigationView.bringToFront();

        binding.mainNavigationView.setNavigationItemSelectedListener(
            item -> {
                switch (item.getItemId()) {
                    case R.id.nav_your_lunch:
                        Log.i(TAG, "Clicked on 'Your lunch' nav item");
                        break;
                    case R.id.nav_settings:
                        Log.i(TAG, "Clicked on 'Settings' nav item");
                        break;
                    case R.id.nav_gps_permission:
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        break;
                    case R.id.nav_logout:
                        viewModel.signOut();
                        Log.i(TAG, "Clicked on 'Logout' nav item");
                        startActivity(new Intent(MainActivity.this, DispatcherActivity.class));
                        finish();
                        break;
                }
                return true;
            }
        );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}