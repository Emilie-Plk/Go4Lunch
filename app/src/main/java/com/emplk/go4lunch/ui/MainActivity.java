package com.emplk.go4lunch.ui;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.MainActivityBinding;
import com.emplk.go4lunch.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MainActivityBinding binding;

    private FirebaseAuth firebaseAuth;

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();


        MainPagerAdapter adapter = new MainPagerAdapter(MainActivity.this);
        binding.mainViewpagerContainer.setAdapter(adapter);

        initBottomNavigationBar();

        initNavigationDrawer();
    }


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
                        firebaseAuth.signOut();
                        Log.i(TAG, "Clicked on 'Logout' nav item");
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
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