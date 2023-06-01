package com.emplk.go4lunch.ui.restaurant_list;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.emplk.go4lunch.R;
import com.emplk.go4lunch.databinding.LoadingStateBinding;
import com.emplk.go4lunch.databinding.RestaurantItemBinding;
import com.emplk.go4lunch.databinding.RestaurantListErrorStateBinding;

public class RestaurantListAdapter extends ListAdapter<RestaurantListViewState, RecyclerView.ViewHolder> {

    @NonNull
    private final OnRestaurantClickedListener listener;

    public RestaurantListAdapter(@NonNull OnRestaurantClickedListener listener) {
        super(new RestaurantListViewHolder.ListRestaurantItemCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (RestaurantListViewState.Type.values()[viewType]) {
            case LOADING_STATE:
                return new RecyclerView.ViewHolder(
                    LoadingStateBinding.inflate(
                            LayoutInflater.from(parent.getContext()), parent, false)
                        .getRoot()
                ) {
                };
            case DISPLAY_RESTAURANT_LIST:
                return new RestaurantListViewHolder(
                    RestaurantItemBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false
                    )
                );
            case ERROR_STATE:
                return new RestaurantListViewHolder.ErrorViewHolder(
                    RestaurantListErrorStateBinding.inflate(
                        LayoutInflater.from(parent.getContext()), parent, false
                    )
                );
            default:
                throw new IllegalStateException("Unknown viewType : " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RestaurantListViewHolder) {
            ((RestaurantListViewHolder) holder).bind((RestaurantListViewState.RestaurantItem) getItem(position), listener);
        } else if (holder instanceof RestaurantListViewHolder.ErrorViewHolder) {
            ((RestaurantListViewHolder.ErrorViewHolder) holder).bind((RestaurantListViewState.RestaurantListError) getItem(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        return getItem(position).getType().ordinal();
    }

    public static class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantItemBinding binding;

        public RestaurantListViewHolder(@NonNull RestaurantItemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }

        public void bind(
            @NonNull RestaurantListViewState.RestaurantItem itemViewState,
            @NonNull OnRestaurantClickedListener listener
        ) {
            itemView.setOnClickListener(v -> listener.onRestaurantClicked(itemViewState.getId()));

            binding.listRestaurantName.setText(itemViewState.getName());
            binding.listRestaurantAddress.setText(itemViewState.getAddress());

            Glide.with(itemView.getContext())
                .load(itemViewState.getPictureUrl())
                .error(R.drawable.restaurant_table)
                .transform(new CenterCrop(), new RoundedCorners(25))
                .into(binding.listRestaurantPicture);

            binding.listRestaurantDistance.setText(itemViewState.getDistance());
            binding.listRestaurantAttendants.setText(itemViewState.getAttendants());
            binding.listRestaurantRating.setVisibility(itemViewState.getIsRatingBarVisible() ? View.VISIBLE : View.INVISIBLE);
            binding.listRestaurantRating.setRating(itemViewState.getRating());
            if (itemViewState.getRestaurantOpeningState() != RestaurantOpeningState.IS_NOT_DEFINED) {
                binding.listRestaurantOpeningHours.setText(itemViewState.getRestaurantOpeningState().getText());
                binding.listRestaurantOpeningHours.setTextColor(Color.parseColor(itemViewState.getRestaurantOpeningState().getColorString()));
            }
        }

        public static class ErrorViewHolder extends RecyclerView.ViewHolder {

            private final RestaurantListErrorStateBinding binding;

            public ErrorViewHolder(RestaurantListErrorStateBinding binding) {
                super(binding.getRoot());
                this.binding = binding;
            }

            public void bind(@NonNull RestaurantListViewState.RestaurantListError item) {
                binding.restaurantListErrorTitle.setText(item.getErrorMessage());

                int drawableError;
                switch (item.getErrorDrawable()) {
                    case NO_GPS_FOUND:
                        drawableError = R.drawable.baseline_not_listed_location_24;
                        break;
                    case NO_RESULT_FOUND:
                        drawableError = R.drawable.baseline_mood_bad_24;
                        break;
                    case REQUEST_FAILURE:
                        drawableError = R.drawable.baseline_network_off_24;
                        break;
                    default:
                        drawableError = R.drawable.baseline_error_outline_24;
                }
                binding.restaurantListErrorImage.setImageResource(drawableError);
            }
        }


        private static class ListRestaurantItemCallback extends DiffUtil.ItemCallback<RestaurantListViewState> {
            @Override
            public boolean areItemsTheSame(@NonNull RestaurantListViewState oldItem, @NonNull RestaurantListViewState newItem) {

                boolean bothAreLoading = oldItem instanceof RestaurantListViewState.Loading && newItem instanceof RestaurantListViewState.Loading;
                boolean bothAreRestaurantLists = oldItem instanceof RestaurantListViewState.RestaurantItem && newItem instanceof RestaurantListViewState.RestaurantItem;

                return bothAreLoading ||
                    (bothAreRestaurantLists &&
                        ((RestaurantListViewState.RestaurantItem) oldItem).getId().equals(((RestaurantListViewState.RestaurantItem) newItem).getId())
                    ) || (
                    oldItem instanceof RestaurantListViewState.RestaurantListError && newItem instanceof RestaurantListViewState.RestaurantListError);

            }

            @Override
            public boolean areContentsTheSame(@NonNull RestaurantListViewState oldItem, @NonNull RestaurantListViewState newItem) {
                return oldItem.equals(newItem);
            }
        }
    }
}
