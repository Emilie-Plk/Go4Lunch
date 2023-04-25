package com.example.go4lunch.ui.restaurant_detail;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.databinding.RestaurantDetailActivityBinding;

public class RestaurantDetailActivity extends AppCompatActivity {

    private RestaurantDetailActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RestaurantDetailActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}