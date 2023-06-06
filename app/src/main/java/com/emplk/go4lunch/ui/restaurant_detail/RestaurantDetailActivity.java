package com.emplk.go4lunch.ui.restaurant_detail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.RestaurantDetailActivityBinding;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class RestaurantDetailActivity extends AppCompatActivity {

    private RestaurantDetailActivityBinding binding;

    private RestaurantDetailViewModel viewModel;

    public static final String KEY_RESTAURANT_ID = "KEY_RESTAURANT_ID";
    private String restaurantId;


    public static Intent navigate(
        Context context,
        String restaurantId
    ) {
        Intent intent = new Intent(context, RestaurantDetailActivity.class);
        intent.putExtra(KEY_RESTAURANT_ID, restaurantId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RestaurantDetailActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setSupportActionBar(binding.detailRestaurantToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(null);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        restaurantId = getIntent().getStringExtra(KEY_RESTAURANT_ID);

        viewModel = new ViewModelProvider(this).get(RestaurantDetailViewModel.class);

        viewModel.getRestaurantId(restaurantId);

        setupObservers();
    }

    private void setupObservers() {
        viewModel.getDetailsRestaurantWrapper().observe(this, restaurantDetail -> {
                if (restaurantDetail != null) {
                    binding.detailRestaurantName.setText(restaurantDetail.getName());
                    binding.detailRestaurantAddress.setText(restaurantDetail.getAddress());
                    binding.detailRestaurantRatingBar.setRating(restaurantDetail.getRating());
                    binding.detailRestaurantVeganFriendly.setVisibility(Boolean.TRUE.equals(restaurantDetail.isVeganFriendly()) ? View.VISIBLE : View.INVISIBLE);
                    binding.loadingStateLoadingBar.setVisibility(Boolean.TRUE.equals(restaurantDetail.isLoading()) ? View.VISIBLE : View.INVISIBLE);
                    binding.detailRestaurantWebsiteButton.setEnabled(restaurantDetail.isWebsiteAvailable());
                    binding.detailRestaurantCallButton.setEnabled(restaurantDetail.isPhoneNumberAvailable());
                    binding.detailRestaurantLikeButton.setEnabled(restaurantDetail.isLiked());
                    binding.detailRestaurantLikeButton.setOnClickListener(v -> {
                            if (restaurantDetail.isLiked()) {
                                viewModel.onRemoveFavoriteRestaurant(restaurantDetail.getId());
                            } else {
                                viewModel.onAddFavoriteRestaurant(restaurantDetail.getId());
                            }
                        }
                    );

                    Glide.with(this)
                        .load(restaurantDetail.getPictureUrl())
                        .centerCrop()
                        .error(R.drawable.restaurant_table)
                        .fallback(R.drawable.restaurant_table)
                        .into(binding.detailRestaurantPicture);

                    binding.detailRestaurantWebsiteButton.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(restaurantDetail.getWebsiteUrl()));
                            startActivity(intent);
                        }
                    );

                    binding.detailRestaurantCallButton.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", restaurantDetail.getPhoneNumber(), null));
                            startActivity(intent);
                        }
                    );
                }
            }
        );
        binding.detailRestaurantPicture.setOnClickListener(v -> finish());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}