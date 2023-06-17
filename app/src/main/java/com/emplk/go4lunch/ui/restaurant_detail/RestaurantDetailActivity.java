package com.emplk.go4lunch.ui.restaurant_detail;


import static com.emplk.go4lunch.ui.restaurant_detail.WorkmateState.WORKMATE_GOING;
import static com.emplk.go4lunch.ui.restaurant_detail.WorkmateState.WORKMATE_NOT_GOING;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.RestaurantDetailActivityBinding;
import com.emplk.go4lunch.ui.utils.RestaurantFavoriteState;
import com.emplk.go4lunch.ui.workmate_list.OnWorkmateClickedListener;
import com.emplk.go4lunch.ui.workmate_list.WorkmateListAdapter;

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

        setupWorkmatesRecyclerView();

        setupObservers();

    }

    private void setupWorkmatesRecyclerView() {
        WorkmateListAdapter adapter = new WorkmateListAdapter(new OnWorkmateClickedListener() {
            @Override
            public void onWorkmateClicked(@NonNull String workmateId) {
            }
        }
        );

        binding.detailRestaurantWorkmatesList.setAdapter(adapter);
        binding.detailRestaurantWorkmatesList.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getWorkmatesGoingToRestaurant().observe(this, workmates -> {
                adapter.submitList(workmates);
            }
        );

        viewModel.getWorkerState().observe(this, workerState -> {
                if (workerState == WORKMATE_GOING) {
                    binding.detailNoWorkmatesTv.setVisibility(View.GONE);
                } else if (workerState == WORKMATE_NOT_GOING) {
                    binding.detailNoWorkmatesTv.setVisibility(View.VISIBLE);
                }
            }
        );
    }

    private void setupObservers() {
        viewModel.getRestaurantDetails().observe(this, restaurantDetail -> {

                if (restaurantDetail instanceof RestaurantDetailViewState.Loading) {
                    binding.detailRestaurantLayout.setVisibility(View.GONE);
                    binding.detailRestaurantLoadingStateLayout.setVisibility(View.VISIBLE);
                }

                if (restaurantDetail instanceof RestaurantDetailViewState.RestaurantDetail) {
                    binding.detailRestaurantLayout.setVisibility(View.VISIBLE);
                    binding.detailRestaurantLoadingStateLayout.setVisibility(View.GONE);

                    RestaurantDetailViewState.RestaurantDetail detail = (RestaurantDetailViewState.RestaurantDetail) restaurantDetail;
                    binding.detailRestaurantName.setText(detail.getName());
                    binding.detailRestaurantAddress.setText(detail.getVicinity());
                    binding.detailRestaurantRatingBar.setRating(detail.getRating());
                    binding.detailRestaurantVeganFriendly.setVisibility(Boolean.TRUE.equals(detail.isVeganFriendly()) ? View.VISIBLE : View.INVISIBLE);
                    binding.detailRestaurantWebsiteButton.setEnabled(detail.isWebsiteAvailable());
                    binding.detailRestaurantCallButton.setEnabled(detail.isPhoneNumberAvailable());

                    if (detail.getAttendanceState() == AttendanceState.IS_ATTENDING) {
                        binding.detailRestaurantChoseFab.setText(detail.getAttendanceState().getText());
                        binding.detailRestaurantChoseFab.setOnClickListener(v -> {
                                Log.d("EMILIE", "clicked on remove user restaurant choice");
                                viewModel.onRemoveUserRestaurantChoice();
                            }
                        );
                    } else if (detail.getAttendanceState() == AttendanceState.IS_NOT_ATTENDING) {
                        binding.detailRestaurantChoseFab.setText(detail.getAttendanceState().getText());
                        binding.detailRestaurantChoseFab.setOnClickListener(v -> {
                                Log.d("EMILIE", "clicked on add user restaurant choice");
                                viewModel.onAddUserRestaurantChoice(
                                    detail.getName(),
                                    detail.getVicinity(),
                                    detail.getPictureUrl()
                                );
                            }
                        );
                    }

                    if (detail.getRestaurantFavoriteState() == RestaurantFavoriteState.IS_FAVORITE) {
                        binding.detailRestaurantLikeButton.setIcon(
                            ContextCompat.getDrawable(this,
                                detail.getRestaurantFavoriteState().getDrawableRes()
                            )
                        );
                        binding.detailRestaurantLikeButton.setOnClickListener(v -> {
                                Log.d("EMILIE", "clicked on remove favorite restaurant");
                                viewModel.onRemoveFavoriteRestaurant();
                            }
                        );
                    } else if (detail.getRestaurantFavoriteState() == RestaurantFavoriteState.IS_NOT_FAVORITE) {
                        binding.detailRestaurantLikeButton.setIcon(
                            ContextCompat.getDrawable(this,
                                detail.getRestaurantFavoriteState().getDrawableRes()
                            )
                        );
                        binding.detailRestaurantLikeButton.setOnClickListener(v -> {
                                Log.d("EMILIE", "clicked on add favorite restaurant");
                                viewModel.onAddFavoriteRestaurant();
                            }
                        );
                    }

                    Glide.with(this)
                        .load(detail.getPictureUrl())
                        .centerCrop()
                        .error(R.drawable.restaurant_table)
                        .fallback(R.drawable.restaurant_table)
                        .into(binding.detailRestaurantPicture);

                    binding.detailRestaurantWebsiteButton.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(detail.getWebsiteUrl()));
                            startActivity(intent);
                        }
                    );

                    binding.detailRestaurantCallButton.setOnClickListener(v -> {
                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", detail.getPhoneNumber(), null));
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