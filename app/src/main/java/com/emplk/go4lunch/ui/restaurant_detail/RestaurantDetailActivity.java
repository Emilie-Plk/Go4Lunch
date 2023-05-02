package com.emplk.go4lunch.ui.restaurant_detail;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.emplk.go4lunch.databinding.RestaurantDetailActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RestaurantDetailActivity extends AppCompatActivity {

    private RestaurantDetailActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RestaurantDetailActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}