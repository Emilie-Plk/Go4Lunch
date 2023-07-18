package com.emplk.go4lunch.ui.main;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.MainActivityBinding;
import com.emplk.go4lunch.databinding.MainNavigationHeaderBinding;
import com.emplk.go4lunch.ui.chat.last_messages.ChatLastMessageListFragment;
import com.emplk.go4lunch.ui.dispatcher.DispatcherActivity;
import com.emplk.go4lunch.ui.main.settings.SettingsActivity;
import com.emplk.go4lunch.ui.restaurant_detail.RestaurantDetailActivity;
import com.emplk.go4lunch.ui.restaurant_list.RestaurantListFragment;
import com.emplk.go4lunch.ui.restaurant_map.MapFragment;
import com.emplk.go4lunch.ui.workmate_list.WorkmateListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {


    private MainActivityBinding binding;

    private MainViewModel viewModel;

    private Snackbar snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = MainActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mainToolbar);


        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setObservers();

        initBottomNavigationBar();

        initNavigationDrawer();
    }

    @SuppressLint("NonConstantResourceId")
    private void setObservers() {

        MainNavigationHeaderBinding navigationHeaderBinding = MainNavigationHeaderBinding.bind(
            binding.mainNavigationView.getHeaderView(0)
        );

        viewModel.getUserInfoLiveData().observe(this, currentLoggedUser -> {
                Glide.with(this)
                    .load(currentLoggedUser.getPictureUrl())
                    .fallback(R.drawable.baseline_person_24)
                    .error(R.drawable.baseline_person_24)
                    .transform(new CenterCrop(), new RoundedCorners(25))
                    .into(navigationHeaderBinding.navigationHeaderUserProfilePicture);

                navigationHeaderBinding.navigationHeaderUserEmail.setText(currentLoggedUser.getEmail());
                navigationHeaderBinding.navigationHeaderUserName.setText(currentLoggedUser.getName());
            }
        );

        viewModel.isGpsEnabledLiveData().observe(this, isGpsEnabled -> {
                if (!isGpsEnabled) {
                    snackbar = Snackbar
                        .make(
                            binding.getRoot(),
                            "Enable GPS ?",
                            Snackbar.LENGTH_INDEFINITE)
                        .setAnchorView(R.id.bottom_bar_map)
                        .setAction(
                            "settings",
                            view -> {
                                Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(intent);
                            }
                        );
                    snackbar.show();
                } else {
                    if (snackbar != null && snackbar.isShown()) {
                        snackbar.dismiss();
                    }
                }
            }
        );

        viewModel.getFragmentStateSingleLiveEvent().observe(this, fragmentState -> {
                switch (fragmentState) {
                    case MAP_FRAGMENT:
                        replaceFragment(MapFragment.newInstance());
                        break;
                    case LIST_FRAGMENT:
                        replaceFragment(RestaurantListFragment.newInstance());
                        break;
                    case WORKMATES_FRAGMENT:
                        replaceFragment(WorkmateListFragment.newInstance());
                        break;
                    case CHAT_FRAGMENT:
                        replaceFragment(ChatLastMessageListFragment.newInstance());
                        break;
                }
            }
        );

        viewModel.onUserLogged().observe(this, loggingState -> {
                if (!loggingState) {
                    startActivity(DispatcherActivity.navigate(this));
                    finish();
                }
            }
        );

        viewModel.getUserWithRestaurantChoice().observe(this, userWithRestaurantChoice -> {
                binding.mainNavigationView.setNavigationItemSelectedListener(
                    item -> {
                        switch (item.getItemId()) {
                            case R.id.nav_your_lunch:
                                if (userWithRestaurantChoice != null) {
                                    startActivity(
                                        RestaurantDetailActivity.navigate(
                                            this, userWithRestaurantChoice.getAttendingRestaurantId()
                                        )
                                    );
                                } else {
                                    Toast.makeText(
                                            this, R.string.toast_message_user_no_restaurant_chosen,
                                            Toast.LENGTH_SHORT)
                                        .show();
                                }
                                break;
                            case R.id.nav_settings:
                                startActivity(
                                    SettingsActivity.navigate(this)
                                );
                                break;
                            case R.id.nav_logout:
                                viewModel.signOut();
                                Log.i(TAG, "Clicked on 'Logout' nav item");
                                startActivity(new Intent(
                                    this, DispatcherActivity.class)
                                );
                                finish();
                                break;
                        }
                        return true;
                    }
                );
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
                        viewModel.onChangeFragmentView(FragmentState.MAP_FRAGMENT);
                        return true;
                    case R.id.bottom_bar_restaurant_list:
                        viewModel.onChangeFragmentView(FragmentState.LIST_FRAGMENT);
                        return true;
                    case R.id.bottom_bar_workmate_list:
                        viewModel.onChangeFragmentView(FragmentState.WORKMATES_FRAGMENT);
                        return true;
                    case R.id.bottom_bar_chat_list:
                        viewModel.onChangeFragmentView(FragmentState.CHAT_FRAGMENT);
                        return true;
                }
                return false;
            }
        );
    }

    @SuppressLint("NonConstantResourceId")
    private void initNavigationDrawer() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
            this,
            binding.mainDrawerLayout,
            binding.mainToolbar,
            R.string.open_nav,
            R.string.close_nav
        );

        binding.mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        binding.mainNavigationView.bringToFront();
    }

    private void replaceFragment(@NonNull Fragment fragment) {
        getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.main_frame_layout, fragment)
            .commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.onResume();
    }
}