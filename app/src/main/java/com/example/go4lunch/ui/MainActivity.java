package com.example.go4lunch.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.go4lunch.R;
import com.example.go4lunch.databinding.MainActivityBinding;
import com.example.go4lunch.ui.restaurant_list.RestaurantListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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


        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();

        MainPagerAdapter adapter = new MainPagerAdapter(MainActivity.this);
        binding.mainViewpagerContainer.setAdapter(adapter);

        BottomNavigationView bottomNavigationView = binding.mainBottomAppbar;
        bottomNavigationView.setSelectedItemId(R.id.map_bottom_bar);

        binding.mainBottomAppbar.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.map_bottom_bar:
                        binding.mainViewpagerContainer.setCurrentItem(0, true);
                        return true;
                    case R.id.restaurant_list_bottom_bar:
                        binding.mainViewpagerContainer.setCurrentItem(1, true);
                        return true;
                    case R.id.workmate_list_bottom_bar:
                        binding.mainViewpagerContainer.setCurrentItem(2, true);
                        return true;
                }
                return false;
            }
        });

    }

}