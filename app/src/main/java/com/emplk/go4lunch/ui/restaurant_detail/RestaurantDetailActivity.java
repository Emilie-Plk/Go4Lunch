package com.emplk.go4lunch.ui.restaurant_detail;

import static com.emplk.go4lunch.ui.utils.RestaurantFavoriteState.IS_FAVORITE;
import static com.emplk.go4lunch.ui.utils.RestaurantFavoriteState.IS_NOT_FAVORITE;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
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


    public static Intent navigate(
        @NonNull Context context,
        @NonNull String restaurantId
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

        viewModel = new ViewModelProvider(this).get(RestaurantDetailViewModel.class);

        setupObservers();
    }

    private void setupObservers() {

        viewModel.isRestoFav().observe(this, isFav -> {
                if (isFav) {
                    binding.detailRestaurantLikeButton.setIconTint(ColorStateList.valueOf(getResources().getColor(R.color.red_clay)));
                    // ENUM TO MANAGE STATE FAV STAR + ACTION
                }
            }
        );

        viewModel.getRestaurantFavoriteState().observe(this, restaurantFavoriteState -> {
                if (restaurantFavoriteState == IS_FAVORITE) {
                    binding.detailRestaurantLikeButton.setIcon(ContextCompat.getDrawable(
                            this, R.drawable.baseline_star_24
                        )
                    );
                    binding.detailRestaurantLikeButton.setOnClickListener(v -> viewModel.onRemoveFavoriteRestaurant());
                } else if (restaurantFavoriteState == IS_NOT_FAVORITE) {
                    binding.detailRestaurantLikeButton.setIcon(ContextCompat.getDrawable(
                            this, R.drawable.baseline_star_border_24
                        )
                    );
                    binding.detailRestaurantLikeButton.setOnClickListener(v -> viewModel.onAddFavoriteRestaurant());
                }
            }
        );

        viewModel.getRestaurantDetails().observe(this, restaurantDetail -> {
                if (restaurantDetail != null) {
                    binding.detailRestaurantName.setText(restaurantDetail.getName());
                    binding.detailRestaurantAddress.setText(restaurantDetail.getAddress());
                    binding.detailRestaurantRatingBar.setRating(restaurantDetail.getRating());
                    binding.detailRestaurantVeganFriendly.setVisibility(Boolean.TRUE.equals(restaurantDetail.isVeganFriendly()) ? View.VISIBLE : View.INVISIBLE);
                    binding.loadingStateLoadingBar.setVisibility(Boolean.TRUE.equals(restaurantDetail.isLoading()) ? View.VISIBLE : View.INVISIBLE);
                    binding.detailRestaurantWebsiteButton.setEnabled(restaurantDetail.isWebsiteAvailable());
                    binding.detailRestaurantCallButton.setEnabled(restaurantDetail.isPhoneNumberAvailable());

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