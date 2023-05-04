package com.emplk.go4lunch.ui;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.MainActivityBinding;
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}